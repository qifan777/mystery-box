import { createApp } from "vue";
import { createPinia } from "pinia";
import dayjs from "dayjs";
import "dayjs/locale/zh-cn.js";
dayjs.locale("zh-cn");
const App = createApp({
  onShow() {},
  // 入口组件不需要实现 render 方法，即使实现了也会被 taro 所覆盖
});
App.use(createPinia());

export default App;
