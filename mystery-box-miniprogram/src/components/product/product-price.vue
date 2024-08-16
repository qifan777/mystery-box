<script setup lang="ts">
import { computed } from "vue";

const props = defineProps<{ price: number; fontSize?: number }>();
const size = computed(() => {
  return (props.fontSize || 28) + "rpx";
});
</script>

<template>
  <div class="product-price-123">
    <span class="prefix">￥</span>
    <span>{{ price }}</span>
  </div>
</template>

<style lang="scss">
.product-price-123 {
  display: flex;
  // ￥ 和 数字底部对齐
  align-items: flex-end;
  .prefix {
    font-size: 28px;
    color: red;
  }

  color: red;
  font-weight: bold;
  font-size: v-bind(size);
}
</style>
