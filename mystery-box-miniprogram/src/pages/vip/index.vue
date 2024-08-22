<script setup lang="ts">
import { ref } from "vue";
import Taro from "@tarojs/taro";
import { api } from "@/utils/api-instance";
import dayjs from "dayjs";
import { RectRight } from "@nutui/icons-vue-taro";
import { switchPage } from "@/utils/common";
import { VipDto } from "@/apis/__generated/model/dto";
import { VipConfig } from "@/apis/__generated/model/static";

const vip = ref<VipDto["VipRepository/COMPLEX_FETCHER_FOR_FRONT"]>({
  createdTime: "",
  creator: { id: "" },
  editedTime: "",
  endTime: "",
  id: "",
});
const vipConfig = ref<VipConfig>({ discount: 0, description: "" });
Taro.useLoad(() => {
  api.vipForFrontController.find().then((res) => {
    if (res.id) {
      vip.value = res;
    }
  });
  api.vipConfigController.getConfig().then((res) => {
    vipConfig.value = res;
  });
});
</script>

<template>
  <div class="vip-page" v-if="vip">
    <div class="vip-info card">
      <div class="valid-time">
        会员有效期至：{{
          vip.endTime ? dayjs(vip.endTime).format("YYYY-MM-DD") : ""
        }}
      </div>
      <div class="buttons">
        <div class="button" @click="switchPage('./vip-level-list')">
          <div class="title-wrapper">
            <image src="@/assets/icons/vip-one-active.png"></image>
            <div class="title">续费VIP会员</div>
            <RectRight size="14"></RectRight>
          </div>
        </div>
        <!--        <div-->
        <!--          class="button"-->
        <!--          @click="switchPage('/pages/customer-service/index')"-->
        <!--        >-->
        <!--          <div class="title-wrapper">-->
        <!--            <image src="@/assets/icons/like.png"></image>-->
        <!--            <div class="title">VIP特权服务</div>-->
        <!--            <RectRight size="14"></RectRight>-->
        <!--          </div>-->
        <!--        </div>-->
      </div>
    </div>
    <div class="description card">
      <div class="title-bar">
        <div class="front">
          <div class="prefix"></div>
          会员服务说明
        </div>
      </div>
      <div class="content">{{ vipConfig.description }}</div>
    </div>
  </div>
</template>

<style lang="scss">
@import "../../app.scss";
page {
  background: linear-gradient(
    to bottom,
    $primary-color 0%,
    #f6f6f6 30%,
    #f6f6f6
  );
}
.vip-page {
  padding: 20px;
  .card {
    background: white;
    padding: 25px;
    border-radius: 15px;
    box-shadow: 0 0 10px 10px rgba(black, 0.01);
    margin-top: 40px;
  }
  .vip-info {
    .top {
      display: flex;
      justify-content: space-between;
      font-size: 30px;
    }
    .valid-time {
      margin-top: 20px;
      font-size: 28px;
      color: rgba(black, 0.7);
    }
    .buttons {
      display: flex;
      justify-content: space-between;
      margin-top: 20px;
      .button {
        padding: 20px;
        background-color: rgba(black, 0.06);
        border-radius: 10px;
        display: flex;
        flex-direction: column;
        align-items: center;
        .title-wrapper {
          font-size: 28px;
          display: flex;
          align-items: center;
          image {
            width: 40px;
            height: 40px;
          }
          .title {
            margin: 0 10px;
            font-weight: 500;
          }
        }
        .desc {
          margin-top: 10px;
          font-size: 26px;
          color: rgba(black, 0.7);
        }
      }
    }
  }
  .description {
    .content {
      margin-top: 20px;
      font-size: 26px;
      color: rgba(black, 0.7);
    }
  }
  .title-bar {
    display: flex;
    justify-content: space-between;
    align-items: center;
    .front {
      display: flex;
      align-items: center;
      .prefix {
        background-color: $primary-color;
        border-radius: 5px;
        width: 10px;
        height: 30px;
        margin-right: 10px;
      }
    }
  }
}
</style>
