<script setup lang="ts">
import { AddressDto } from "@/apis/__generated/model/dto";
defineProps<{
  address: AddressDto["AddressRepository/COMPLEX_FETCHER_FOR_FRONT"];
}>();
</script>

<template>
  <div class="address-row">
    <div class="address-info">
      <div class="details">
        {{ address.details + " " + address.houseNumber }}
      </div>
      <div class="connector">
        <div class="real-name">{{ address.realName }}</div>
        <div class="phone-number">{{ address.phoneNumber }}</div>
        <div class="is-default" v-if="address.top">
          <nut-tag type="primary" plain>默认</nut-tag>
        </div>
      </div>
    </div>
    <slot name="operation"></slot>
  </div>
</template>

<style lang="scss">
.address-row {
  padding: 30rpx;
  background: white;
  display: flex;
  align-items: center;

  .address-info {
    width: 600rpx;
    .details {
      font-size: 30rpx;
      font-weight: bold;
      margin-bottom: 10rpx;
    }
    .connector {
      font-size: 28rpx;
      display: flex;
      align-items: center;
      .real-name {
        margin-right: 30rpx;
      }
      .is-default {
        margin-left: 20px;
      }
    }
  }
}
</style>
