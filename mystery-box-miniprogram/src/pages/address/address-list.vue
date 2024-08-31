<template>
  <div class="address-list-page">
    <address-row
      class="address"
      :key="address.id"
      :address="address"
      v-for="address in pageData.content"
      @longpress="showActionSheet(address)"
      @click="handleAddressChoose(address)"
    >
      <template #operation>
        <div class="operations">
          <del class="delete" @click="handleDelete(address.id)"></del>
          <edit
            class="edit"
            @click.stop="switchPage(`./address-save?id=${address.id}`)"
          ></edit>
        </div>
      </template>
    </address-row>
    <div class="add-address" @click="switchPage('./address-save')">
      <div class="btn">
        <plus></plus>
        添加收货地址
      </div>
    </div>
    <nut-action-sheet
      v-model:visible="show"
      :menu-items="actions"
      @choose="handleActionChoose"
    />
  </div>
</template>

<script lang="ts" setup>
import { api } from "@/utils/api-instance";
import { switchPage } from "@/utils/common";
import AddressRow from "@/components/address/address-row.vue";
import Taro from "@tarojs/taro";
import { Edit, Del, Plus } from "@nutui/icons-vue-taro";
import { ref } from "vue";
import { AddressDto } from "@/apis/__generated/model/dto";
import { menuItems } from "@nutui/nutui-taro/dist/types/__VUE/actionsheet/index.taro.vue";
import { usePageHelper } from "@/utils/page";
// -----地址列表加载-----
type Address = AddressDto["AddressRepository/COMPLEX_FETCHER_FOR_FRONT"];
const { pageData, reloadPageData } = usePageHelper(
  api.addressForFrontController.query,
  api.addressForFrontController,
  { pageSize: 10000, pageNum: 1, query: {} },
  // 每次进入页面刷新数据（Taro.useDidShow），禁用首次进入页面刷新（Taro.useLoad），避免重复请求
  { enableShowLoad: true, enableLoad: false },
);
// -----地址列表加载-----

// -----地址删除操作-----
const handleDelete = (id: string) => {
  Taro.showModal({
    title: "是否确认删除",
    showCancel: true,
    success: async ({ confirm }) => {
      if (confirm) {
        await api.addressForFrontController.delete({ body: [id] });
        await reloadPageData();
      }
    },
  });
};
// -----地址删除操作-----

// -----地址点击操作-----
const isFromOrder = ref(false);
Taro.useLoad(({ from }) => {
  if (from === "order") {
    isFromOrder.value = true;
  }
});
const handleAddressChoose = (value: Address) => {
  if (isFromOrder.value) {
    Taro.navigateBack({
      success() {
        Taro.eventCenter.trigger("address", value);
      },
    });
  }
};
// -----地址点击操作-----

// -----长按地址显示菜单操作-----
const show = ref(false);
const activeAddress = ref<Address>();
const showActionSheet = (address: Address) => {
  activeAddress.value = address;
  show.value = true;
};

const actions = [{ name: "复制地址" }, { name: "设为默认" }] as menuItems[];
// 菜单项对应的操作
const actionMap = {
  ["复制地址"]: (address: Address) => {
    const value = `收件人：${address.realName}\n手机号码：${address.phoneNumber}\n详细地址：${address.details} ${address.houseNumber}`;
    Taro.setClipboardData({ data: value });
  },
  ["设为默认"]: async (address: Address) => {
    await api.addressForFrontController.top({ id: address.id });
    await reloadPageData();
  },
};
const handleActionChoose = (action: menuItems) => {
  actionMap[action.name](activeAddress.value);
};
// -----长按地址显示菜单操作-----
</script>

<style lang="scss">
@import "../../app.scss";
page {
  background-color: #f5f5f5;
}
.address-list-page {
  .operations {
    display: flex;
    justify-content: center;

    .edit {
      margin-left: 20px;
    }
  }
  .add-address {
    border-top: 1px solid rgba(black, 0.1);
    box-shadow: 10rpx -10rpx 10rpx rgba(black, 0.05);
    position: fixed;
    bottom: 0;
    width: 100%;
    padding: 30rpx 0 60rpx 0;

    .btn {
      color: white;
      margin: auto;
      justify-content: center;
      display: flex;
      align-items: center;
      background-image: linear-gradient($primary-color, $primary-color-end);
      width: 600rpx;
      padding: 20rpx 0;
      border-radius: 10rpx;
    }
  }
}
</style>
