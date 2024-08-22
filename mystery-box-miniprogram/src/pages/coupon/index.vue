<template>
  <div class="coupon-page">
    <div v-for="couponUser in pageData.content" :key="couponUser.id">
      <coupon-row :coupon-user="couponUser"></coupon-row>
    </div>
  </div>
</template>

<script setup lang="ts">
import { api } from "@/utils/api-instance";
import { usePageHelper } from "@/utils/page";
import CouponRow from "@/components/coupon/coupon-row.vue";

const { pageData } = usePageHelper(
  api.couponUserRelForFrontController.query,
  api.couponUserRelForFrontController,
  { query: {} },
);
</script>

<style lang="scss"></style>
