<template>
  <div class="vip-package-page">
    <div class="vip-package-list">
      <div
        :class="['vip-package', level.id == active ? 'active' : '']"
        v-for="level in pageData.content"
        :key="level.id"
        @click="active = level.id"
      >
        <div class="name">{{ level.name }}</div>
        <div class="price">{{ level.price }} ￥</div>
        <div class="row">
          <div class="label">天数：</div>
          <div class="value">{{ level.days }}天</div>
        </div>
      </div>
    </div>
    <nut-button class="submit" block type="primary" @click="handleSubmit"
      >立即购买
    </nut-button>
  </div>
</template>

<script setup lang="ts">
import { usePageHelper } from "@/utils/page";
import { api } from "@/utils/api-instance";
import { ref } from "vue";
import Taro from "@tarojs/taro";

const { pageData, reloadPageData } = usePageHelper(
  api.vipPackageForFrontController.query,
  api.vipPackageForFrontController,
  {
    pageNum: 1,
    pageSize: 1000,
    query: {},
  },
  { enableLoad: false },
);
const active = ref();
Taro.useDidShow(async () => {
  await reloadPageData();
  active.value = pageData.value.content[0].id;
});
const handleSubmit = () => {
  Taro.showLoading();
  api.vipOrderForFrontController
    .save({
      body: {
        vipPackageId: active.value,
      },
    })
    .then((res) => {
      Taro.requestPayment({
        nonceStr: res.nonceStr,
        package: res.packageValue,
        paySign: res.paySign,
        timeStamp: res.timeStamp,
        signType: res.signType as "RSA",
        success: () => {
          Taro.showLoading();
          Taro.showToast({
            title: "支付成功",
            icon: "success",
          });
          Taro.navigateBack();
        },
        fail: (res) => {
          Taro.showLoading();
          Taro.showToast({ title: res.errMsg, icon: "none" });
        },
      });
    });
};
</script>

<style lang="scss">
@import "../../app.scss";

page {
  background-color: rgba(black, 0.05);
}

.vip-package-page {
  .vip-package-list {
    padding: 20px;
    display: grid;
    grid-template-columns: repeat(3, 1fr);
    grid-column-gap: 20px;
    grid-row-gap: 40px;
    justify-content: space-around;

    .vip-package {
      background-color: white;
      padding: 20px 10px;
      display: flex;
      flex-direction: column;
      align-items: center;
      font-size: 28px;
      border-radius: 8px;
      margin-right: 20px;

      &.active {
        color: white;
        background: linear-gradient(
          315deg,
          $primary-color 0%,
          $primary-color-end 100%
        );
      }

      .name {
        font-size: 30px;
        font-weight: bold;
      }

      .price {
        margin-top: 10px;
        font-size: 30px;
        font-weight: bold;
      }

      .row {
        margin-top: 10px;
        font-size: 26px;
        display: flex;
        align-items: center;
      }
    }
  }

  .submit {
    margin: 30px auto;
    width: 600px;
  }
}
</style>
