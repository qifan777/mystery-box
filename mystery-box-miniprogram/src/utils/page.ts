import { type Ref, ref } from "vue";
import type { Page, QueryRequest } from "@/apis/__generated/model/static";
import _ from "lodash";
import Taro from "@tarojs/taro";

export type PageResult<T> = Pick<
  Page<T>,
  "content" | "number" | "size" | "totalElements" | "totalPages"
>;
export const recursiveOmit = (obj: any) => {
  const obj2 = {
    ..._.omitBy(obj, (row) => {
      if (_.isString(row)) {
        return _.isEmpty(row);
      } else {
        return _.isNil(row);
      }
    }),
  };
  const keys = Object.keys(
    _.pickBy(obj, (row) => {
      return _.isObject(row);
    }),
  );
  keys.forEach((key) => {
    obj2[key] = recursiveOmit(obj[key]);
  });
  return obj2;
};
export const usePageHelper = <T extends Object, E>(
  // 调用后端的查询接口
  queryApi: (options: {
    readonly body: QueryRequest<T>;
  }) => Promise<PageResult<E>>,
  object: unknown,
  // 查询条件
  initQuery: QueryRequest<T>,
  config?: {
    // 分页数据后置处理
    postProcessor?: (data: PageResult<E>) => void;
    enablePullDownRefresh?: boolean;
    enableReachBottom?: boolean;
    enableLoad?: boolean;
  },
) => {
  config = {
    enablePullDownRefresh: true,
    enableReachBottom: true,
    enableLoad: true,
    ...config,
  };
  const pageData = ref({
    content: [] as E,
    number: 1,
    size: initQuery.pageSize,
    totalElements: 0,
    totalPages: 0,
  }) as Ref<PageResult<E>>;
  const queryRequest = ref({
    query: {},
    pageNum: 1,
    pageSize: 10,
    ..._.omitBy(initQuery, _.isNil),
    likeMode: "ANYWHERE",
    sorts: [{ property: "createdTime", direction: "DESC" }],
  }) as Ref<QueryRequest<T>>;
  const finish = ref(false);

  // 请求分页数据
  const loadPageData = async (request: Partial<QueryRequest<T>> = {}) => {
    // 通用查询对象，防止传入空值
    queryRequest.value = {
      ...queryRequest.value,
      ..._.omitBy(request, _.isNil),
    };
    // 如果查询条件为null，undefined，空字符串则过滤不提交
    queryRequest.value.query = recursiveOmit(queryRequest.value.query) as T;

    if (finish.value) return;
    // 显示加载动画
    Taro.showLoading({
      title: "加载中",
    });
    try {
      // 调用查询接口
      const res: PageResult<E> = await queryApi.apply(object, [
        { body: queryRequest.value },
      ]);
      if (config?.postProcessor !== undefined) {
        config.postProcessor(res);
      }
      // 返回结果
      pageData.value.content.push(...res.content);
      pageData.value.totalElements = res.totalElements;
      pageData.value.number = res.number;
      pageData.value.totalPages = res.totalPages;
      finish.value = res.content.length < res.size;
      queryRequest.value.pageNum = (queryRequest.value.pageNum || 1) + 1;
      // 取消加载动画
      Taro.hideLoading();
      return pageData.value;
    } catch (e) {
      Taro.hideLoading();
    }
  };
  // 重新请求分页数据，pageNum=1, pageSize=10
  const reloadPageData = async (request?: {
    pageSize?: number;
    pageNum?: number;
    query?: T;
  }) => {
    finish.value = false;
    pageData.value.content = [];
    await loadPageData({
      pageNum: request?.pageNum ?? 1,
      pageSize: request?.pageSize ?? 10,
      query: request?.query,
    });
  };
  if (config?.enablePullDownRefresh) {
    // 下拉刷新
    Taro.usePullDownRefresh(async () => {
      await reloadPageData();
      setTimeout(() => {
        Taro.stopPullDownRefresh();
      }, 300);
    });
  }
  if (config?.enableReachBottom) {
    // 触底加载
    Taro.useReachBottom(async () => {
      await loadPageData();
    });
  }
  if (config?.enableLoad) {
    // 首次进入页面加载
    Taro.useLoad(async () => {
      await loadPageData();
    });
  }
  return {
    queryRequest,
    pageData,
    loadPageData,
    reloadPageData,
  };
};
