<script setup lang="ts">
import { type CartItem, useCartStore } from "@/pages/cart/cart-store";
import { storeToRefs } from "pinia";
import Taro from "@tarojs/taro";

const cartStore = useCartStore();
const { checkedItems } = storeToRefs(cartStore);
const emit = defineEmits<{ submit: [value: CartItem[]] }>();
const handleClick = () => {
  Taro.switchTab({ url: "/pages/cart/cart-list" });
};
</script>

<template>
  <nut-badge
    :value="checkedItems.length"
    class="cart-icon-wrapper"
    @click="handleClick"
  >
    <image class="cart-icon" src="@/assets/icons/shopping-cart-one.png"></image>
  </nut-badge>
</template>

<style lang="scss">
.cart-icon-wrapper {
  .cart-icon {
    height: 60px;
    width: 60px;
  }
}
</style>
