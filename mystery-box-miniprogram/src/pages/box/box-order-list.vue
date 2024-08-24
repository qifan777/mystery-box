<template>
  <div class="box-order-list-page">
    <nut-tabs class="tabs" v-model="activeStatus" @change="handleTabChange">
      <nut-tab-pane
        v-for="tab in tabList"
        :key="tab.keyId"
        :title="tab.keyName"
        :pane-key="tab.keyEnName"
      >
        <div class="order" v-for="order in pageData.content" :key="order.id">
          <box-order-row
            :order="order"
            @click="switchPage('./box-order-details?id=' + order.id)"
          >
            <nut-button
              size="small"
              plain
              v-if="order.status === 'TO_BE_PAID'"
              @click.stop="handleCancel(order.id)"
              >取消订单
            </nut-button>
            <nut-button
              size="small"
              type="primary"
              plain
              v-if="order.status === 'TO_BE_PAID'"
              @click.stop="handlePay(order.id)"
              >立即支付
            </nut-button>

            <nut-button
              size="small"
              type="success"
              plain
              v-if="order.status == 'TO_BE_RECEIVED'"
              @click="handleTrackingDetails(order.baseOrder.trackingNumber)"
              >查看物流</nut-button
            >
          </box-order-row>
        </div>
      </nut-tab-pane>
    </nut-tabs>
  </div>
</template>
<script setup lang="ts">
import { usePageHelper } from "@/utils/page";
import { api } from "@/utils/api-instance";
import { ProductOrderStatus } from "@/apis/__generated/model/enums";
import { switchPage } from "@/utils/common";
import { Dictionaries } from "@/apis/__generated/model/enums/DictConstants";
import { ref } from "vue";
import BoxOrderRow from "./components/box-order-row.vue";
import {
  boxOrderCancel,
  boxWeChatPay,
  handleTrackingDetails,
} from "@/pages/box/box-order";
import Taro from "@tarojs/taro";
const { TO_BE_PAID, TO_BE_DELIVERED, TO_BE_RECEIVED, CLOSED } =
  Dictionaries.ProductOrderStatus;
const tabList = [TO_BE_PAID, TO_BE_DELIVERED, TO_BE_RECEIVED, CLOSED];
const activeStatus = ref<ProductOrderStatus>("TO_BE_PAID");
const handleTabChange = () => {
  reloadPageData({ query: { status: activeStatus.value } });
};
const { pageData, reloadPageData } = usePageHelper(
  api.mysteryBoxOrderForFrontController.query,
  api.mysteryBoxOrderForFrontController,
  { pageNum: 1, pageSize: 10, query: { status: activeStatus.value } },
  { enableLoad: false },
);
Taro.useDidShow(() => {
  reloadPageData();
});
const handleCancel = async (id: string) => {
  await boxOrderCancel(id);
  await reloadPageData();
};
const handlePay = async (id: string) => {
  await boxWeChatPay(id);
  await reloadPageData();
};
</script>
<style lang="scss">
page {
  background-color: #f5f5f5;
}
.box-order-list-page {
  .nut-tabs__list {
    background-color: white;
  }
  .nut-tab-pane {
    padding: 0;
    background-color: #f5f5f5;
  }
}
</style>
