<template>
  <div class="coupon-page">
    <coupon-row
      v-for="couponUser in pageData.content"
      :key="couponUser.id"
      :coupon-user="couponUser"
      @use="chooseCoupon"
      :active="activeId == couponUser.id"
    ></coupon-row>
  </div>
</template>

<script setup lang="ts">
import { api } from "@/utils/api-instance";
import { usePageHelper } from "@/utils/page";
import CouponRow from "@/components/coupon/coupon-row.vue";
import Taro from "@tarojs/taro";
import { ref } from "vue";
import { CouponUserRelDto } from "@/apis/__generated/model/dto";
type CouponUser =
  CouponUserRelDto["CouponUserRelRepository/COMPLEX_FETCHER_FOR_FRONT"];
const activeId = ref<string>();
const fromUser = ref(false);
const { pageData, reloadPageData } = usePageHelper(
  api.couponUserRelForFrontController.query,
  api.couponUserRelForFrontController,
  { query: {} },
  { enableLoad: false },
);
Taro.useLoad(({ amount, id, from }) => {
  if (from) {
    fromUser.value = true;
  }
  activeId.value = id;
  reloadPageData({
    query: { maxThresholdAmount: amount || 999999999 },
  });
});
const chooseCoupon = (couponUser?: CouponUser) => {
  if (fromUser.value) {
    return Taro.switchTab({ url: "/pages/box/index" });
  }
  Taro.navigateBack({
    success() {
      Taro.eventCenter.trigger("coupon", couponUser);
    },
  });
};
</script>

<style lang="scss"></style>
