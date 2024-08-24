<template>
  <div class="box-order-details" v-if="order">
    <nut-cell-group class="cells">
      <nut-cell is-link center>
        <template #icon>
          <image
            class="icon"
            src="@/assets/icons/local.png"
            mode="widthFix"
          ></image>
        </template>
        <template #title>
          <address-row :address="order.baseOrder.address"></address-row>
        </template>
      </nut-cell>
      <nut-cell is-link center v-if="order.baseOrder.couponUser">
        <template #icon>
          <image class="icon" src="@/assets/icons/coupon.png"></image>
        </template>
        <template #title>
          {{ order.baseOrder.couponUser.coupon.name }}
        </template>
      </nut-cell>
      <nut-cell is-link center v-if="order.baseOrder.remark">
        <template #icon>
          <image class="icon" src="@/assets/icons/editor.png"></image>
        </template>
        <template #desc>
          <textarea
            v-model="order.baseOrder.remark"
            style="height: 100rpx"
            readonly
          ></textarea>
        </template>
        <template #link>
          <div></div>
        </template>
      </nut-cell>
    </nut-cell-group>
    <div class="product-list">
      <div class="row" v-for="item in order.items" :key="item.id">
        <product-row :product="item.mysteryBox">
          <template #operation>
            <div class="count">x{{ item.mysteryBoxCount }}</div>
          </template>
        </product-row>
        <div class="products">
          <product-row
            v-for="product in item.products"
            :key="product.id"
            :product="product"
          />
        </div>
      </div>
    </div>
    <nut-cell-group class="summary">
      <nut-cell title="盲盒总价">
        <template #desc>
          <div class="value">￥{{ order.baseOrder.payment.productAmount }}</div>
        </template>
      </nut-cell>
      <nut-cell title="运费">
        <template #desc>
          <div class="value">￥{{ order.baseOrder.payment.deliveryFee }}</div>
        </template>
      </nut-cell>
      <nut-cell title="优惠券">
        <template #desc>
          <div class="value">-￥{{ order.baseOrder.payment.couponAmount }}</div>
        </template>
      </nut-cell>
      <nut-cell title="会员优惠">
        <template #desc>
          <div class="value">-￥{{ order.baseOrder.payment.vipAmount }}</div>
        </template>
      </nut-cell>
      <nut-cell title="实付金额">
        <template #desc>
          <div class="value">-￥{{ order.baseOrder.payment.payAmount }}</div>
        </template>
      </nut-cell>
    </nut-cell-group>
    <div class="buttons-wrapper" v-if="order.status === 'TO_BE_PAID'">
      <div class="buttons">
        <nut-button @click="handleCancel(order.id)"> 取消订单 </nut-button>
        <nut-button type="primary" @click="handlePay(order.id)"
          >立即支付 ￥{{ order.baseOrder.payment.payAmount }}
        </nut-button>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref } from "vue";
import { api } from "@/utils/api-instance";
import Taro from "@tarojs/taro";
import { MysteryBoxOrderDto } from "@/apis/__generated/model/dto";
import { boxOrderCancel, boxWeChatPay } from "@/pages/box/box-order";

const order =
  ref<
    MysteryBoxOrderDto["MysteryBoxOrderRepository/COMPLEX_FETCHER_FOR_FRONT"]
  >();
const init = async (id: string) => {
  order.value = await api.mysteryBoxOrderForFrontController.findById({ id });
};
Taro.useLoad(({ id }) => {
  init(id);
});
const handleCancel = async (id: string) => {
  await boxOrderCancel(id);
  Taro.navigateBack();
};
const handlePay = async (id: string) => {
  await boxWeChatPay(id);
  await init(id);
};
</script>

<style lang="scss">
@import "../../app.scss";

page {
  background-color: rgba(black, 0.05);
}

.box-order-details {
  .cells {
    .icon {
      width: 30px;
      height: 30px;
    }
    .textarea-placeholder {
      color: #cccccc;
    }
  }
  .count {
    font-size: 28px;
  }

  .product-list {
    background-color: white;
    padding: 32px;
    margin-bottom: 30px;
    .products {
      padding-left: 20px;
    }
  }

  .summary {
    padding-bottom: 130px;
    .value {
      color: rgba(black, 0.9);
      font-weight: bold;
    }
  }
  .buttons-wrapper {
    display: flex;
    justify-content: flex-end;
    position: fixed;
    bottom: 0;
    width: 100%;
    height: 150px;
    border-top: 1px solid rgba(black, 0.1);
    background-color: white;

    .buttons {
      display: flex;
      align-items: center;
      button {
        margin-right: 20px;
      }
    }
  }
}
</style>
