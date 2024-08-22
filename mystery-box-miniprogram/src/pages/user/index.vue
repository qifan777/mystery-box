<template>
  <div class="user-page">
    <image
      class="background"
      mode="aspectFill"
      src="@/assets/images/BJ.jpg"
    ></image>
    <!-- user-wrapper的作用是让白色的框框在整个页面水平居中。如果不使用user-wrapper，白色框框会靠左，而不是水平居中。 -->
    <!-- 水平居中的方案有好几种，我用的是父亲元素flex让子元素居中，也可以直接让子元素margin: 0 auto;实现水平居中 -->
    <div class="user-wrapper">
      <!-- 白色的框框，让用户的信息在白色框框中垂直居中. -->
      <div class="user-section">
        <!-- 用户的信息包含了 头像+（用户名，箭头），让他们垂直居中 -->
        <div class="user-info" @click="switchPage('./user-info')">
          <user-avatar
            v-if="userInfo.avatar"
            :src="userInfo.avatar"
            radius="50%"
            width="100rpx"
            height="100rpx"
            mode="aspectFill"
          ></user-avatar>
          <!-- 让用户名和箭头垂直居中 -->
          <div class="username">
            <!-- 用户信息不存在时，点击此会加载用户信息。 -->
            <div
              v-if="!userInfo.nickname"
              class="name not-login"
              @click.stop="homeStore.getUserInfo()"
            >
              您还未登录,点击登录
            </div>
            <div v-else class="name">{{ userInfo.nickname }}</div>
            <rect-right class="rect-right" />
          </div>
        </div>
      </div>
    </div>
    <div class="divider">
      <div class="front">个人中心</div>
      <div class="rear">MY SERVICE</div>
    </div>
    <div class="functions">
      <div class="function" @tap="switchPage('/pages/order/order-list')">
        <image
          class="icon"
          mode="widthFix"
          src="@/assets/icons/order.png"
        ></image>
        <div class="name">商品订单</div>
      </div>
      <div class="function" @tap="switchPage('/pages/box/box-order-list')">
        <image
          class="icon"
          mode="widthFix"
          src="@/assets/icons/gift-active.png"
        ></image>
        <div class="name">盲盒订单</div>
      </div>
      <div class="function" @tap="switchPage('/pages/vip/index')">
        <image
          class="icon"
          mode="widthFix"
          src="@/assets/icons/vip.png"
        ></image>
        <div class="name">会员权益</div>
      </div>
    </div>
    <div class="divider">
      <div class="front">其他功能</div>
      <div class="rear">OTHER FUNCTIONS</div>
    </div>
    <div class="functions">
      <div class="function" @tap="switchPage('/pages/feedback/index')">
        <image
          class="icon"
          mode="widthFix"
          src="@/assets/icons/editor.png"
        ></image>
        <div class="name">留言反馈</div>
      </div>
      <div class="function" @tap="switchPage('/pages/address/address-list')">
        <image
          class="icon"
          mode="widthFix"
          src="@/assets/icons/local.png"
        ></image>
        <div class="name">地址簿</div>
      </div>
    </div>
  </div>
  <register-popup></register-popup>
</template>

<script lang="ts" setup>
import { RectRight } from "@nutui/icons-vue-taro";
import { useHomeStore } from "@/stores/home-store";
import UserAvatar from "@/components/user/user-avatar.vue";
import { switchPage } from "@/utils/common";
import Taro from "@tarojs/taro";
import { storeToRefs } from "pinia";

const homeStore = useHomeStore();
homeStore.getUserInfo();
const { userInfo } = storeToRefs(homeStore);
Taro.showShareMenu({
  showShareItems: ["shareAppMessage", "shareTimeline"],
});
</script>

<style lang="scss">
.user-page {
  // 背景图片绝对布局，垫在底部
  padding-bottom: 200px;

  .background {
    z-index: -1;
    position: absolute;
    max-width: 100%;
    max-height: 500.35px;
    width: 100%;
    top: 0;
  }

  .user-wrapper {
    width: 100%;
    // 白色框框在整个页面水平居中
    display: flex;
    justify-content: center;
    margin-top: 350px;

    .user-section {
      box-shadow: 0 8px 25px rgba(0, 0, 0, 0.2);
      width: 100%;
      height: 150px;
      background-color: white;
      margin: 20px 40px;
      padding: 20px;
      border-radius: 10px;
      // 用户信息在白色框框中垂直居中
      display: flex;
      align-items: center;

      .user-info {
        // 头像和（用户名+右箭头）垂直居中
        display: flex;
        align-items: center;

        .avatar {
          margin-right: 50px;
          box-shadow: 0 8px 25px rgba(0, 0, 0, 0.2);
        }

        .username {
          // 用户名和箭头垂直居中
          margin-left: 20px;
          display: flex;
          align-items: center;

          .name {
            font-size: 32px;
            margin-right: 20px;

            &.not-login {
              color: rgba(black, 0.5);
            }
          }

          .rect-right {
            color: rgb(black, 0.5);
          }
        }
      }
    }
  }

  .divider {
    margin: 25px 40px;
    display: flex;
    align-items: flex-end;

    .front {
      font-size: 35px;
      font-weight: bold;
      margin-right: 10px;
    }

    .rear {
      font-size: 25px;
      color: #c3c3c3;
    }
  }

  .functions {
    box-shadow: 0 8px 25px rgba(0, 0, 0, 0.2);
    background-color: white;
    margin: 0 40px;
    padding: 40px 20px;
    border-radius: 10px;
    display: grid;
    grid-template-columns: 1fr 1fr 1fr;
    grid-auto-flow: row;
    grid-row-gap: 15px;

    .function {
      display: flex;
      flex-direction: column;
      align-items: center;

      .icon {
        width: 55px;
      }

      .name {
        background-color: white;
        border: 0;
        padding: 10px 0;
        color: #9e9e9e;
        font-size: 25px;
      }
    }
  }
}
</style>
