<script setup lang="ts">
import { ProductCategoryDto } from "@/apis/__generated/model/dto";
import { computed } from "vue";
import { useCartStore } from "@/pages/cart/cart-store";

type ProductCategory =
  ProductCategoryDto["ProductCategoryRepository/COMPLEX_FETCHER_FOR_FRONT"];
type Product = ProductCategory["products"][0];
const cartStore = useCartStore();
const props = defineProps<{ product: Product }>();
const index = computed(() => {
  return cartStore.cartList.findIndex(
    (row) => row.product.id == props.product.id,
  );
});
const handleAddProduct = (product: Product) => {
  cartStore.pushItem({ checked: true, count: 1, product });
};
const handleAddCount = () => {
  cartStore.plusItem(index.value);
};
const handleMinusCount = () => {
  cartStore.minusItem(index.value);
};
</script>

<template>
  <nut-button
    v-if="index < 0"
    size="small"
    type="primary"
    @click.stop="handleAddProduct(product)"
    >立即选购</nut-button
  >
  <nut-input-number
    v-else
    :model-value="cartStore.cartList[index].count"
    :min="0"
    @reduce="handleMinusCount"
    @add="handleAddCount"
  ></nut-input-number>
</template>

<style scoped lang="scss"></style>
