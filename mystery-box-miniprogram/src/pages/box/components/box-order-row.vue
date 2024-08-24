<script setup lang="ts">
import DictColumn from "@/components/dict/dict-column.vue";
import { DictConstants } from "@/apis/__generated/model/enums/DictConstants";
import dayjs from "dayjs";
import { MysteryBoxOrderDto } from "@/apis/__generated/model/dto";
defineProps<{
  order: MysteryBoxOrderDto["MysteryBoxOrderRepository/COMPLEX_FETCHER_FOR_FRONT"];
}>();
</script>

<template>
  <div class="box-order-row">
    <div class="time-status">
      <div class="time">
        {{ dayjs(order.createdTime).format("YYYY-MM-DD HH:mm") }}
      </div>
      <dict-column
        :dict-id="DictConstants.PRODUCT_ORDER_STATUS"
        :value="order.status"
      ></dict-column>
    </div>
    <div class="list">
      <div class="row" v-for="item in order.items" :key="item.mysteryBoxId">
        <product-row :key="item.mysteryBoxId" :product="item.mysteryBox">
          <template #operation>
            <div class="count">x{{ item.mysteryBoxCount }}</div>
          </template>
        </product-row>
        <scroll-view :scroll-x="true">
          <div class="images">
            <image
              :src="product.cover"
              mode="aspectFill"
              v-for="product in item.products"
              :key="product.id"
            ></image>
          </div>
        </scroll-view>
      </div>
    </div>
    <div class="total">合计：￥{{ order.baseOrder.payment.payAmount }}</div>
    <div class="buttons">
      <slot></slot>
    </div>
  </div>
</template>

<style lang="scss">
.box-order-row {
  padding: 30px;
  background-color: white;
  border-radius: 20px;
  margin-top: 20px;
  .time-status {
    display: flex;
    font-size: 26px;
    justify-content: space-between;
    color: rgba(black, 0.5);
    padding: 10px 0;
  }
  .list {
    .row {
      .count {
        font-size: 26px;
      }
      .images {
        display: flex;
        align-items: center;
        justify-content: flex-end;
        image {
          background-color: rgba(black, 0.1);
          border-radius: 10px;
          margin-right: 20px;
          flex-shrink: 0;
          width: 100px;
          height: 100px;
        }
      }
    }
  }
  .total {
    font-size: 30px;
    display: flex;
    justify-content: flex-end;
    padding: 15px 10px;
  }
  .buttons {
    display: flex;
    justify-content: flex-end;
    .nut-button {
      margin-left: 20px;
    }
  }
}
</style>
