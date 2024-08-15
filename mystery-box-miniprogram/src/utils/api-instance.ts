import { Api } from "@/apis/__generated";
import requestWithToken from "@/utils/request";

// 导出全局变量`api`
export const api = new Api(async ({ uri, method, body }) => {
  return await requestWithToken(uri, method, body);
});
