<template>
  <div class="available-coupon-list" v-if="couponList.length">
    <coupon-row
      class="coupon-row"
      v-for="couponUser in couponList"
      :coupon-user="couponUser"
      :key="couponUser.id"
      @use="chooseCoupon"
      :active="activeId == couponUser.id"
    ></coupon-row>
  </div>
  <nut-empty v-else></nut-empty>
</template>

<script setup lang="ts">
import { ref } from "vue";
import { api } from "@/utils/api-instance";
import { CouponUserRelDto } from "@/apis/__generated/model/dto";
import Taro from "@tarojs/taro";
type CouponUser =
  CouponUserRelDto["CouponUserRelRepository/COMPLEX_FETCHER_FOR_FRONT"];
const activeId = ref<string>();
const couponList = ref<CouponUser[]>([]);
Taro.useLoad(({ amount, id }) => {
  activeId.value = id;
  api.couponForFrontController.availableCoupons({ amount }).then((res) => {
    couponList.value = res;
  });
});
const chooseCoupon = (couponUser?: CouponUser) => {
  Taro.navigateBack({
    success() {
      Taro.eventCenter.trigger("coupon", couponUser);
    },
  });
};
</script>

<style lang="scss">
.available-coupon-list {
  .coupon-row {
    margin-top: 40px;
  }
}
</style>
