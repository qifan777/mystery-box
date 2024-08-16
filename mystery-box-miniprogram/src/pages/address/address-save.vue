<template>
  <div class="address-save">
    <div class="address-wrapper">
      <div class="address">
        <div class="btn" @click="chooseAddress">
          去选择收货地址
          <rect-right color="#999"></rect-right>
        </div>
        <div class="form">
          <div class="form-item">
            <div class="label">地址：</div>
            <div class="input">
              <input
                placeholder="请输入地址"
                v-model="address.details"
                readonly
              />
            </div>
          </div>
          <div class="form-item">
            <div class="label">门牌号</div>
            <div class="input">
              <input placeholder="请输入门牌号" v-model="address.houseNumber" />
            </div>
          </div>
          <div class="form-item">
            <div class="label">联系人</div>
            <div class="input">
              <input placeholder="请输入姓名" v-model="address.realName" />
            </div>
          </div>
          <div class="form-item">
            <div class="label">手机号</div>
            <div class="input">
              <input placeholder="请输入手机号" v-model="address.phoneNumber" />
            </div>
          </div>
        </div>
        <div class="submit" @click="submit">保存地址</div>
      </div>
    </div>
  </div>
</template>

<script lang="ts" setup>
import { computed, ref } from "vue";
import Taro from "@tarojs/taro";
import { api } from "@/utils/api-instance";
import { AddressInput } from "@/apis/__generated/model/static";
import { RectRight } from "@nutui/icons-vue-taro";
import location from "@/assets/icons/local.png";
// 当前位置标记
const marker = computed(() => {
  return {
    id: 1,
    iconPath: location,
    latitude: address.value.latitude,
    longitude: address.value.longitude,
    width: 40,
    height: 40,
  };
});
// 获取到经纬度
Taro.authorize({
  scope: "scope.userLocation",
  success() {
    Taro.getLocation({
      success: (res) => {
        // 编辑时不需要获取当前地址
        if (!address.value.id) {
          address.value.latitude = res.latitude;
          address.value.longitude = res.longitude;
        }
      },
    });
  },
});
const address = ref<AddressInput>({
  details: "",
  latitude: 23.099994,
  longitude: 113.32452,
  phoneNumber: "",
  realName: "",
  top: false,
  houseNumber: "",
});

const chooseAddress = () => {
  Taro.chooseLocation({
    success(result) {
      address.value.details = result.address;
      address.value.latitude = result.latitude;
      address.value.longitude = result.longitude;
      console.log(result);
    },
  });
};

// 提交表单
const submit = () => {
  api.addressForFrontController.save({ body: address.value }).then(() => {
    Taro.showToast({ title: "提交成功", icon: "success" });
    Taro.navigateBack();
  });
};
// 编辑回显
Taro.useLoad((ops: any) => {
  if (ops.id) {
    api.addressForFrontController.findById({ id: ops.id }).then((res) => {
      address.value = res;
    });
  }
});
</script>

<style lang="scss">
@import "../../app.scss";
page {
  background-color: rgba(black, 0.05);
}
.address-save {
  .map {
    top: 0;
    position: absolute;
    z-index: -1;
    width: 100%;
    height: 100vh;
  }
  .address-wrapper {
    width: 100%;
    position: absolute;
    top: 40px;
    .address {
      background-color: white;
      border-radius: 20rpx;
      padding: 40rpx 20rpx 70px 20px;
      margin: 0 30px;
      .btn {
        display: flex;
        align-items: center;
        justify-content: center;
        width: 400rpx;
        border: 1.5px solid $primary-color-end;
        text-align: center;
        padding: 15rpx 50rpx;
        color: $primary-color;
        font-weight: bold;
        margin: auto;
        border-radius: 15rpx;
      }
      .form {
        margin-top: 40rpx;
        .form-item {
          display: flex;
          align-items: center;
          padding: 20rpx 0;
          .label {
            width: 120rpx;
            font-weight: bold;
          }
          .input {
            flex: 1;
            padding: 20rpx 0;
            border-bottom: 1px solid rgba(black, 0.1);
          }
        }
      }
      .submit {
        margin-top: 30rpx;
        text-align: center;
        padding: 20rpx 0;
        font-weight: bold;
        background-image: linear-gradient($primary-color, $primary-color-end);
        border-radius: 15rpx;
        color: white;
      }
    }
  }
}
</style>
