export default defineAppConfig({
  pages: [
    "pages/box/index",
    "pages/box/box-details",
    "pages/user/index",
    "pages/user/user-info",
    "pages/feedback/index",
  ],
  window: {
    backgroundTextStyle: "light",
    navigationBarBackgroundColor: "#fff",
    navigationBarTextStyle: "black",
  },
  tabBar: {
    borderStyle: "black",
    backgroundColor: "#ffffff",
    color: "#8F8F94",
    selectedColor: "#4C7459",
    list: [
      {
        pagePath: "pages/box/index",
        iconPath: "assets/icons/gift.png",
        selectedIconPath: "assets/icons/gift-active.png",
        text: "首页",
      },
      {
        pagePath: "pages/user/index",
        iconPath: "assets/icons/user.png",
        selectedIconPath: "assets/icons/user-active.png",
        text: "我的",
      },
    ],
  },
  permission: {
    "scope.userLocation": {
      desc: "你的位置将用于确认收货地址",
    },
  },
  requiredPrivateInfos: ["chooseLocation"],
});
