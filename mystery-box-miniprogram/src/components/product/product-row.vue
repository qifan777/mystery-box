<template>
  <div class="qi-product">
    <!-- 封面 aspectFill优先保证图片比例 -->
    <image class="cover" :src="product.cover" mode="aspectFill"></image>
    <!-- 商品详情 -->
    <div class="info">
      <div class="name">{{ product.name }}</div>
      <div class="description" v-if="product.brand">
        {{ product.description }}
      </div>
      <div class="brand" v-if="product.brand">
        <nut-tag type="success">{{ product.brand }}</nut-tag>
      </div>
      <div class="price-row">
        <div class="price">
          <span class="prefix">￥</span>
          <span>{{ product.price }}</span>
        </div>
        <slot name="operation"></slot>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
defineProps<{
  product: {
    id: string;
    name: string;
    description?: string;
    price: number;
    cover: string;
    brand?: string;
  };
}>();
</script>

<style lang="scss">
@import "../../app.scss";
.qi-product {
  padding: 15px 0;
  display: flex;
  align-items: flex-start;
  width: 100%;
  .cover {
    width: 140px;
    height: 140px;
    border-radius: 10px;
  }
  .info {
    // 撑开多余的空间
    flex: 1;
    margin-left: 20px;
    padding-right: 10px;
    .name {
      font-size: 28px;
      @include text-max-line(1);
    }
    .description {
      margin-top: 10px;
      font-size: 24px;
      color: rgba($color: #000000, $alpha: 0.7);
      @include text-max-line(1);
    }
    .brand {
      padding: 0;
      --nut-tag-font-size: 20px;
    }
    .price-row {
      display: flex;
      // 水平方向上均衡分布
      justify-content: space-between;
      // 垂直方向居中
      align-items: center;
      .price {
        display: flex;
        // ￥ 和 数字底部对齐
        align-items: flex-end;
        .prefix {
          font-size: 28px;
          color: red;
        }

        color: red;
        font-weight: bold;
        font-size: 28px;
      }
    }
  }
}
</style>
