<script setup lang="ts">
import {
  AddressDto,
  CouponUserRelDto,
  MysteryBoxDto,
} from "@/apis/__generated/model/dto";
import { ref } from "vue";
import { RectRight } from "@nutui/icons-vue-taro";
import { switchPage } from "@/utils/common";
import Taro from "@tarojs/taro";
import { api } from "@/utils/api-instance";
import {
  MysteryBoxOrderInput,
  PaymentPriceView,
} from "@/apis/__generated/model/static";

type CouponUserRel =
  CouponUserRelDto["CouponUserRelRepository/COMPLEX_FETCHER_FOR_FRONT"];
type Address = AddressDto["AddressRepository/COMPLEX_FETCHER_FOR_FRONT"];
type MysteryBoxItem = {
  box: MysteryBoxDto["MysteryBoxRepository/COMPLEX_FETCHER_FOR_FRONT"];
  count: number;
};
const order = ref<MysteryBoxOrderInput>({
  baseOrder: { addressId: "" },
  items: [],
});
const chosenAddress = ref<Address>();
Taro.eventCenter.on("address", (value: Address) => {
  chosenAddress.value = value;
  order.value.baseOrder.addressId = value.id;
  calculate();
});
const chosenCoupon = ref<CouponUserRel>();
Taro.eventCenter.on("coupon", (couponUserRel?: CouponUserRel) => {
  chosenCoupon.value = couponUserRel;
  order.value.baseOrder.couponUserId = couponUserRel?.id;
  calculate();
});
const boxItems = ref<MysteryBoxItem[]>();
Taro.eventCenter.on("items", (items: MysteryBoxItem[]) => {
  boxItems.value = items;
  order.value.items = items.map((item) => {
    return { mysteryBoxId: item.box.id, mysteryBoxCount: item.count };
  });
  calculate();
});

const payment = ref<PaymentPriceView>();
const calculate = async () => {
  payment.value = await api.mysteryBoxOrderForFrontController.calculate({
    body: order.value,
  });
};
const handleSubmit = () => {
  if (!chosenAddress.value) {
    Taro.showToast({ title: "请选择地址", icon: "error" });
    return;
  }
  api.mysteryBoxOrderForFrontController
    .create({
      body: order.value,
    })
    .then((res) => {
      Taro.navigateTo({ url: `./box-order-details?id=` + res });
    });
};
</script>

<template>
  <div class="box-order-create" v-if="payment">
    <div class="box-order">
      <div class="box" v-for="item in boxItems" :key="item.box.id">
        <image :src="item.box.cover" mode="aspectFill"></image>
        <div class="info">
          <div class="title">{{ item.box.name }}</div>
          <div class="desc">不支持7天无理由退换货</div>
          <div class="price">
            <div class="prefix">￥</div>
            <div class="value">
              {{ item.box.price * item.count }}
            </div>
          </div>
        </div>
      </div>

      <nut-cell-group class="cells">
        <nut-cell
          is-link
          center
          @click="switchPage('/pages/address/address-list?from=order')"
        >
          <template #icon>
            <image
              class="icon"
              src="@/assets/icons/local.png"
              mode="widthFix"
            ></image>
          </template>
          <template #title>
            <address-row
              :address="chosenAddress"
              v-if="chosenAddress"
            ></address-row>
          </template>
          <template #desc v-if="!chosenAddress"> 请选择地址</template>
          <template #link>
            <rect-right></rect-right>
          </template>
        </nut-cell>
        <nut-cell
          is-link
          center
          @click="
            switchPage(
              `/pages/coupon/coupon-available-list?amount=${payment.productAmount}&id=${chosenCoupon?.id}`,
            )
          "
        >
          <template #icon>
            <image class="icon" src="@/assets/icons/coupon.png"></image>
          </template>
          <template #title>
            {{ chosenCoupon?.coupon.name }}
          </template>
          <template #desc v-if="!chosenCoupon"> 请选择优惠券 </template>
          <template #link>
            <rect-right></rect-right>
          </template>
        </nut-cell>
        <nut-cell is-link center>
          <template #icon>
            <image class="icon" src="@/assets/icons/editor.png"></image>
          </template>
          <template #desc>
            <textarea
              v-model="order.baseOrder.remark"
              style="height: 100rpx"
              placeholder="请输入备注"
            ></textarea>
          </template>
          <template #link>
            <div></div>
          </template>
        </nut-cell>
      </nut-cell-group>
      <nut-cell-group class="summary">
        <nut-cell title="盲盒总价">
          <template #desc>
            <div class="value">￥{{ payment.productAmount }}</div>
          </template>
        </nut-cell>
        <nut-cell title="运费">
          <template #desc>
            <div class="value">￥{{ payment.deliveryFee }}</div>
          </template>
        </nut-cell>
        <nut-cell title="优惠券">
          <template #desc>
            <div class="value">-￥{{ payment.couponAmount }}</div>
          </template>
        </nut-cell>
        <nut-cell title="会员优惠">
          <template #desc>
            <div class="value">-￥{{ payment.vipAmount }}</div>
          </template>
        </nut-cell>
      </nut-cell-group>
      <div class="submit" @click="handleSubmit">
        ￥{{ payment.payAmount }} 提交订单
      </div>
    </div>
  </div>
</template>

<style lang="scss">
page {
  --nut-cell-box-shadow: 0;
}

.box-order-create {
  .box-order {
    padding: 30px;

    .box {
      display: flex;
      align-items: flex-start;
      margin-bottom: 30px;

      image {
        width: 180px;
        height: 180px;
        border-radius: 15px;
      }

      .info {
        margin-left: 20px;

        .title {
          font-size: 30px;
          font-weight: 600;
          margin-bottom: 15px;
        }

        .desc {
          font-size: 28px;
          margin-bottom: 15px;
        }

        .price {
          margin-top: 40px;
          display: flex;
          align-items: flex-end;
          color: rgb(238, 136, 255);

          .prefix {
            margin-right: 10px;
            font-size: 28px;
            line-height: 30px;
          }

          .value {
            line-height: 40px;
            font-size: 40px;
            font-weight: bold;
          }
        }
      }
    }
  }
  .cells {
    .icon {
      width: 30px;
      height: 30px;
    }
    .textarea-placeholder {
      color: #cccccc;
    }
  }

  .submit {
    background: linear-gradient(90deg, #e2c1ff, #d7e7ff 50.14%, #aac3ff);
    width: 600px;
    margin: auto;
    padding: 20px;
    text-align: center;
    border-radius: 15px;
    font-weight: bold;
    font-size: 32px;
  }
}
</style>
