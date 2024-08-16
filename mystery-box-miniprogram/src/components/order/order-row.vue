<script setup lang="ts">
import { ProductOrderDto } from "@/apis/__generated/model/dto";
import DictColumn from "@/components/dict/dict-column.vue";
import { DictConstants } from "@/apis/__generated/model/enums/DictConstants";
import dayjs from "dayjs";

defineProps<{
  order: ProductOrderDto["ProductOrderRepository/COMPLEX_FETCHER_FOR_FRONT"];
}>();
</script>

<template>
  <div class="order-row">
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
      <product-row
        class="row"
        :key="item.product.id"
        :product="item.product"
        v-for="item in order.items"
      >
        <template #operation>
          <div class="count">x{{ item.productCount }}</div>
        </template>
      </product-row>
    </div>
    <div class="total">合计：￥{{ order.baseOrder.payment.payAmount }}</div>
    <slot name="bottom"></slot>
    <div class="buttons">
      <slot name="buttons"></slot>
    </div>
  </div>
</template>

<style lang="scss">
.order-row {
  padding: 30px;
  background-color: white;
  border-radius: 20px;
  .time-status {
    display: flex;
    font-size: 26px;
    justify-content: space-between;
    color: rgba(black, 0.5);
    padding: 10px 0;
  }
  .product-list {
    box-sizing: border-box;
    background-color: white;
    padding: 32px;
    border-radius: 12px;
    margin-bottom: 30px;
    height: 450px;

    .date-bookings {
      .date {
        margin: 20px 0;
        font-size: 32px;
        color: rgba(black, 0.8);
      }

      .bookings {
        background-color: rgba(black, 0.05);
        padding: 20px;
        border-radius: 10px;

        .booking {
          font-size: 28px;
          display: flex;
          align-items: center;
          justify-content: space-between;
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
