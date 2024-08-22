<template>
  <div class="coupon-row">
    <image
      src="@/assets/icons/coupon-row.png"
      class="coupon-background"
      mode="heightFix"
    >
    </image>
    <div class="coupon-info">
      <div class="coupon-price">
        <div class="price" v-if="couponUser.coupon.couponType == 'REDUCE'">
          <span class="prefix">￥</span
          ><span>
            {{ couponUser.coupon.amount }}
          </span>
        </div>
        <div class="price" v-else>
          <span>
            {{ couponUser.coupon.discount }}
          </span>
          <span class="suffix">折</span>
        </div>
        <div class="threshold">
          满 {{ couponUser.coupon.thresholdAmount }}可用
        </div>
      </div>

      <div class="divider"></div>
      <div class="coupon-details">
        <div class="description">
          <dict-column
            :dict-id="DictConstants.COUPON_SCOPE_TYPE"
            :value="couponUser.coupon.scopeType"
          ></dict-column>
        </div>
        <div class="valid-time">
          {{ dayjs(couponUser.coupon.effectiveDate).format("YYYY-MM-DD") }}-
          {{ dayjs(couponUser.coupon.expirationDate).format("YYYY-MM-DD") }}
        </div>
      </div>
      <div class="use">
        <nut-button
          type="warning"
          size="mini"
          @click="chooseCoupon()"
          v-if="active"
          >取消使用</nut-button
        >
        <nut-button
          type="primary"
          size="mini"
          @click="chooseCoupon(couponUser)"
          v-else
          >立即使用</nut-button
        >
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { CouponUserRelDto } from "@/apis/__generated/model/dto";
import dayjs from "dayjs";
import { DictConstants } from "@/apis/__generated/model/enums/DictConstants";
type CouponUserRel =
  CouponUserRelDto["CouponUserRelRepository/COMPLEX_FETCHER_FOR_FRONT"];
defineProps<{
  couponUser: CouponUserRel;
  active?: boolean;
}>();
const emit = defineEmits<{
  use: [couponUser?: CouponUserRel];
}>();
const chooseCoupon = (couponUser?: CouponUserRel) => {
  emit("use", couponUser);
};
</script>

<style lang="scss">
.coupon-row {
  display: flex;
  justify-content: center;
  width: 100%;
  position: relative;
  .coupon-background {
    height: 230px;
  }
  .coupon-info {
    position: absolute;
    display: flex;
    align-items: center;
    left: 60px;
    top: 0;
    height: 230px;
    .coupon-price {
      color: red;
      display: flex;
      flex-direction: column;
      align-items: center;
      .price {
        font-weight: bold;
        display: flex;
        align-items: flex-end;
        font-size: 50px;
        .prefix {
          line-height: 50px;
          font-size: 30px;
        }
        .suffix {
          line-height: 50px;
          font-size: 30px;
        }
      }
      .threshold {
        margin-top: 10px;
        font-size: 26px;
      }
    }
    .divider {
      border-right: 2px dashed rgba($color: #000000, $alpha: 0.1);
      height: 150px;
      margin-left: 20px;
      margin-right: 20px;
    }
    .coupon-details {
      .description {
        color: rgba($color: #000000, $alpha: 0.9);
        font-size: 30px;
      }
      .valid-time {
        font-size: 24px;
        color: rgba($color: #000000, $alpha: 0.5);
        margin-top: 10px;
      }
    }
    .use {
      margin-left: 20px;
    }
  }
}
</style>
