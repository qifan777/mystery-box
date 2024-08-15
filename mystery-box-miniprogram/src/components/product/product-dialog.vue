<script setup lang="ts">
import { ProductDto } from "@/apis/__generated/model/dto";

defineProps<{
  product: Pick<
    ProductDto["ProductRepository/COMPLEX_FETCHER"],
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
      style="background-color: black"
      :visible="visible"
      round
      @update:visible="(value) => emit('update:visible', value)"
    >
      <scroll-view class="product-scroll-view" :scroll-y="true">
        <div>
          <div class="title">{{ product.name }}</div>
          <image
            class="product-cover"
            :src="product.cover"
            mode="widthFix"
          ></image>
          <div
            :class="[
              product.tags[0] === '超神款' ? 'super' : '',
              product.tags[0] === '隐藏款' ? 'middle' : '',
            ]"
          ></div>
          <div class="info">
            <div class="row">
              <div class="label">价格:</div>
              <div class="value">{{ product.price }}元</div>
            </div>
            <div class="row">
              <div class="label">参数:</div>
              <div class="value">
                <scroll-view class="spec-scroll" :scroll-x="true">
                  <div class="cell-list">
                    <div
                      class="cell"
                      v-for="spec in product.specifications"
                      :key="spec.name"
                    >
                      <div class="top">{{ spec.name }}</div>
                      <div class="bottom">{{ spec.values.join(",") }}</div>
                    </div>
                  </div>
                </scroll-view>
              </div>
            </div>
            <div class="row">
              <div class="label">品牌:</div>
              <div class="value">{{ product.brand }}</div>
            </div>
          </div>
        </div>
      </scroll-view>
    </nut-popup>
  </div>
</template>

<style lang="scss">
@import "../../app.scss";
.product-dialog {
  .product-scroll-view {
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
    .super {
      background-image: url("../../assets/icons/super-star.png");
      background-size: contain;
      background-repeat: no-repeat;
      width: 220px;
      height: 140px;
      margin: auto;
    }
    .middle {
      background-image: url("../../assets/icons/middle-star.png");
      background-size: contain;
      background-repeat: no-repeat;
      width: 220px;
      height: 140px;
      margin: auto;
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
          .spec-scroll {
            width: 450px;
            .cell-list {
              display: flex;
              align-items: center;
              .cell {
                flex-shrink: 0;
                display: flex;
                flex-direction: column;
                align-items: center;
                padding: 10px 20px;
                border-right: 1px solid rgba(white, 0.05);
                .top {
                  margin-bottom: 8px;
                  color: #ccc;
                }
                .bottom {
                  text-align: center;
                  color: #999;
                  @include text-max-line(1);
                  max-width: 120px;
                }
              }
            }
          }
        }
      }
    }
  }
}
</style>
