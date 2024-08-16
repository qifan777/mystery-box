<script lang="tsx">
import { PropType, SlotsType, defineComponent, reactive } from "vue";
import { ScrollView, ScrollViewProps } from "@tarojs/components";
import Taro from "@tarojs/taro";
import { ProductCategoryDto } from "@/apis/__generated/model/dto";
type ProductCategory =
  ProductCategoryDto["ProductCategoryRepository/COMPLEX_FETCHER_FOR_FRONT"];
export default defineComponent({
  name: "CategoryProducts",
  props: {
    categories: {
      type: Array as PropType<ProductCategory[]>,
      required: true,
    },
  },
  slots: Object as SlotsType<{
    default: {
      product: ProductCategory["products"][0];
    };
  }>,
  setup(props, { slots }) {
    const scrollState = reactive({
      // 存储右侧category-products的头部和底部与product-section的距离。
      rightToTopList: [] as Array<[number, number]>,
      // 存储左侧每个category-item的顶部距与category-section的距离
      leftToTopList: [] as number[],
      // 左侧滚动区域滚动条与category-section的距离
      leftScrollTop: 0,
      // 右侧滚动区域滚动条与product-section的距离
      rightScrollTop: 0,
      // right-scroll的高度
      rightHeight: 0,
      // 当前激活的类别
      activeCategory: 0,
    });
    Taro.useDidShow(() => {
      Taro.nextTick(() => {
        const query = Taro.createSelectorQuery();
        query
          .selectAll(".category-products")
          .boundingClientRect((data) => {
            // 第一个category-products与小程序顶部的距离（约等于product-section到小程序顶部的距离）
            const toTop = data[0].top;
            if (scrollState.rightToTopList.length === 0) {
              if (data instanceof Array) {
                for (let i = 0; i < data.length; i++) {
                  // 通过css选择器得到的top是与小程序顶部的距离，这步我们需要的是与product-section顶部的距离。
                  // 因此下面的top都是需要减去toTop
                  scrollState.rightToTopList.push([
                    // 计算得到顶部和 product-section 的距离。
                    data[i].top - toTop,
                    // 计算得到底部和 product-section 的距离。
                    data[i].top + data[i].height - toTop,
                  ]);
                }
              }
            }
          })
          .exec();
        query
          .selectAll(".category-item")
          .boundingClientRect((data) => {
            // 第一个category-item到小程序顶部的距离（约等于category-section到小程序顶部的距离）
            const toTop = data[0].top;
            if (scrollState.leftToTopList.length === 0) {
              if (data instanceof Array) {
                for (let i = 0; i < data.length; i++) {
                  // 计算得到 category-item顶部到category-section的距离
                  scrollState.leftToTopList.push(data[i].top - toTop);
                }
              }
            }
          })
          .exec();
        query
          .select(".right-scroll")
          .boundingClientRect(
            (data: Taro.NodesRef.BoundingClientRectCallbackResult) => {
              // 右侧的高度（不是滚动区域的总高度）
              scrollState.rightHeight = data.height;
            },
          );
      });
    });

    const leftClick = (index: number) => {
      scrollState.activeCategory = index;
      scrollState.rightScrollTop = scrollState.rightToTopList[index][0];
    };
    const rightScroll: ScrollViewProps["onScroll"] = (event) => {
      const scrollTop = event.detail.scrollTop;
      // 滚动到底部了
      if (scrollTop + scrollState.rightHeight >= event.detail.scrollHeight) {
        scrollState.leftScrollTop =
          scrollState.leftToTopList[scrollState.leftToTopList.length - 1];
        scrollState.activeCategory = scrollState.leftToTopList.length - 1;
        return;
      }
      // 判断当前滚动条在哪个商品区间
      for (let i = 0; i < scrollState.rightToTopList.length; i++) {
        if (
          scrollState.rightToTopList[i][0] < scrollTop &&
          scrollState.rightToTopList[i][1] > scrollTop
        ) {
          scrollState.leftScrollTop = scrollState.leftToTopList[i];
          scrollState.activeCategory = i;
          break;
        }
      }
    };
    return () => (
      <div class="side-menu">
        {/* 左右结构left-scroll和right-scroll */}
        {/* scrollY： 可以在垂直方向上滚动。必须指定hight的值。 */}
        <ScrollView
          class="left-scroll"
          scrollY={true}
          scrollWithAnimation={true}
          scrollTop={scrollState.leftScrollTop}
        >
          {/* 左侧存放类别，所有类别垂直排列，且在水平居中 */}
          <div class="category-section">
            {props.categories.map((category, index) => (
              // 每个类别左边是图标，右边是类别名字。且图标和名字垂直/水平居中。
              <div
                class={[
                  "category-item",
                  scrollState.activeCategory === index ? "active" : "",
                ]}
                key={category.id}
                onClick={() => {
                  leftClick(index);
                }}
              >
                <div class="name-icon">
                  {category.icon ? (
                    // mode={"aspectFill"} 优先保证图片的比例，无法显示的部分会截切掉。
                    <image
                      src={category.icon}
                      class="icon"
                      mode={"aspectFill"}
                    ></image>
                  ) : (
                    ""
                  )}
                  <div class="name">{category.name}</div>
                </div>
              </div>
            ))}
          </div>
        </ScrollView>
        <ScrollView
          class="right-scroll"
          scrollY={true}
          scrollWithAnimation={true}
          scrollTop={scrollState.rightScrollTop}
          onScroll={rightScroll}
        >
          <div class="product-section">
            {props.categories.map((category) => (
              // 每个类别对应多个商品
              <div class="category-products">
                <div class="category-name">{category.name}</div>
                <div class="products">
                  {/* 商品插槽，商品的样式比较动态，留给引用方实现。 */}
                  {category.products.map((product) => (
                    <div>{slots.default({ product })}</div>
                  ))}
                </div>
              </div>
            ))}
          </div>
        </ScrollView>
      </div>
    );
  },
});
</script>

<style lang="scss">
.side-menu {
  display: flex;
  .left-scroll {
    height: 100vh;
    width: 150px;
    background-color: rgba(#000, 0.03);
    .category-section {
      // 类别区域内的类别垂直排列且水平居中
      display: flex;
      flex-direction: column;
      align-items: center;

      .category-item {
        // 类别的图标和名字的相关样式。
        display: flex;
        padding: 50px 0;
        align-items: center;
        justify-content: center;
        width: 100%;
        .name-icon {
          display: flex;
          align-items: center;
          .icon {
            width: 40px;
            height: 40px;
            margin-right: 10px;
            border-radius: 50%;
          }
        }
        &.active {
          background-color: white;
        }
      }
    }
  }

  .right-scroll {
    height: 100vh;
    width: 600px;

    .product-section {
      background-color: white;
      padding: 15px 15px 120px;
      .category-products {
        .category-name {
          padding: 20px 0;
          font-size: 32px;
          color: rgba(#000, 0.8);
        }
        .products {
          display: grid;
          grid-template-columns: repeat(2, 1fr);
        }
      }
    }
  }
}
</style>
