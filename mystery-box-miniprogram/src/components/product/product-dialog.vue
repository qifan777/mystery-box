<script setup lang="ts">
import { ProductDto } from "@/apis/__generated/model/dto";

defineProps<{
  product: Pick<
    ProductDto["ProductRepository/COMPLEX_FETCHER_FOR_FRONT"],
    | "name"
    | "specifications"
    | "tags"
    | "attributes"
    | "description"
    | "brand"
    | "cover"
    | "price"
  >;
  visible: boolean;
}>();
const emit = defineEmits<{
  "update:visible": [visible: boolean];
}>();
</script>

<template>
  <div class="product-dialog">
    <nut-popup
      lock-scroll
      style="background-color: black"
      :visible="visible"
      round
      @update:visible="(value) => emit('update:visible', value)"
    >
      <div class="product">
        <div>
          <div class="title">{{ product.name }}</div>
          <image
            class="product-cover"
            :src="product.cover"
            mode="widthFix"
          ></image>
          <div class="info">
            <div class="row">
              <div class="label">价格:</div>
              <div class="value">{{ product.price }}元</div>
            </div>
            <div class="row">
              <div class="label">参数:</div>
              <div class="value">
                <specification-list
                  :specs="product.specifications"
                ></specification-list>
              </div>
            </div>
            <div class="row">
              <div class="label">品牌:</div>
              <div class="value">{{ product.brand }}</div>
            </div>
          </div>
        </div>
      </div>
    </nut-popup>
  </div>
</template>

<style lang="scss">
@import "../../app.scss";
.product-dialog {
  .product {
    width: 650px;
    .title {
      margin: 30px;
      text-align: center;
      color: white;
      @include text-max-line(2);
    }
    .product-cover {
      width: 500px;
      margin: auto;
      display: block;
    }
    .info {
      background-color: #1a1a1a;
      margin: 20px;
      border-radius: 15px;
      font-size: 27px;
      padding: 40px;
      .row {
        display: flex;
        align-items: center;
        .label {
          color: #999;
          width: 100px;
        }
        .value {
          color: #ccc;
        }
      }
    }
  }
}
</style>
