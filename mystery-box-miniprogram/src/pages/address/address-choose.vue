<template>
  <div class="address-choose">
    <nut-popup
      :visible="visible"
      @update:visible="(value) => emit('update:visible', value)"
      position="bottom"
      closeable
      round
    >
      <div class="address-wrapper">
        <div
          class="address"
          :key="address.id"
          v-for="address in addressList"
          @click="handleChoose(address)"
        >
          <check color="red" v-if="address.id == chosenAddress.id"></check>
          <location2 color="red" v-else></location2>
          <address-row :address="address"> </address-row>
        </div>
      </div>
    </nut-popup>
  </div>
</template>
<script setup lang="ts">
import { ref } from "vue";
import { AddressDto } from "@/apis/__generated/model/dto";
import { api } from "@/utils/api-instance";
import Taro from "@tarojs/taro";
import { Check, Location2 } from "@nutui/icons-vue-taro";
type Address = AddressDto["AddressRepository/COMPLEX_FETCHER_FOR_FRONT"];
defineProps<{ visible: boolean }>();
const emit = defineEmits<{
  "update:visible": [value: boolean];
  choose: [address: Address];
}>();
const addressList = ref<Address[]>([]);
const chosenAddress = ref({} as Address);
Taro.useLoad(() => {
  api.addressForFrontController
    .query({ body: { pageSize: 10000, pageNum: 1, query: {} } })
    .then((res) => {
      addressList.value = res.content;
      if (res.content.length > 0) {
        // 默认地址会在第一个
        chosenAddress.value = res[0];
        emit("choose", res[0]);
      }
    });
});
const handleChoose = (address: Address) => {
  chosenAddress.value = address;
  emit("update:visible", false);
  emit("choose", address);
};
</script>
<style lang="scss">
.address-choose {
  .address-wrapper {
    min-height: 300px;
    padding: 20px;
    margin-top: 60px;
    .address {
      margin: 0 20px;
      display: flex;
      align-items: center;
    }
  }
}
</style>
