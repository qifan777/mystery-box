<template>
  <nut-popup
    v-model:visible="registerShow"
    :style="{ padding: '30px 50px' }"
    position="bottom"
    pop-class="register-popup"
  >
    <div class="register-section">
      <div class="climb-icon">
        <image
          class="climb"
          mode="heightFix"
          src="@/assets/images/climb.png"
        ></image>
      </div>
      <div class="title-section">
        <div class="title">欢迎注册~</div>
        <div class="info">注册后可享受更好的服务体验</div>
      </div>
      <div class="button-section">
        <button class="btn" open-type="getPhoneNumber" @getPhoneNumber="submit">
          手机号快捷登录
        </button>
      </div>
    </div>
  </nut-popup>
</template>

<script lang="ts" setup>
import Taro from "@tarojs/taro";
import { useHomeStore } from "@/stores/home-store";
import { api } from "@/utils/api-instance";
import { storeToRefs } from "pinia";

const homeStore = useHomeStore();
const { registerShow } = storeToRefs(homeStore);
const handleClose = () => {
  registerShow.value = false;
};

const submit = (e: { detail: { code: string } }) => {
  Taro.login({
    success: function (loginRes) {
      Taro.showLoading();
      // 调用微信登录接口
      api.userWeChatForFrontController
        .registerV2({
          body: {
            loginCode: loginRes.code,
            phoneCode: e.detail.code,
            inviteCode: "",
          },
        })
        .then((res) => {
          Taro.setStorageSync("token", res.tokenValue);
          homeStore.getUserInfo();
          handleClose();
          Taro.hideLoading();
        });
    },
  });
};
</script>

<style lang="scss">
page,
:root {
  .nut-popup {
    padding: 0 !important;
    overflow: unset;
  }
}

.register-section {
  height: 460px;
  background-color: white;
  width: 100%;

  .climb-icon {
    position: relative;
    width: 100%;

    .climb {
      position: absolute;
      height: 350px;
      right: 100px;
      top: -175px;
    }
  }

  .title-section {
    padding: 100px 0 20px 50px;

    .title {
      color: red;
      font-size: 55px;
      font-weight: bold;
      margin-bottom: 30px;
    }

    .info {
      color: #727272;
      font-size: 35px;
    }
  }
  .button-section {
    width: 100%;
    display: flex;
    justify-content: center;

    .icon {
      height: 40px;
      margin-right: 20px;
    }

    .btn {
      background-color: rgb(0, 200, 0);
      margin-top: 40px;
      color: white;
      width: 600px;
      display: flex;
      justify-content: center;
      align-items: center;
      border-radius: 999px;
      font-size: 40px;
    }
  }
}
</style>
