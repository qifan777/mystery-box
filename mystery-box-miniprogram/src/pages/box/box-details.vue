<script setup lang="ts">
import { ref } from "vue";
import { MysteryBoxDto } from "@/apis/__generated/model/dto";
import Taro from "@tarojs/taro";
import { api } from "@/utils/api-instance";
import { Dictionaries } from "@/apis/__generated/model/enums/DictConstants";
type Product =
  MysteryBoxDto["MysteryBoxRepository/COMPLEX_FETCHER_FOR_FRONT"]["products"][0];
const box =
  ref<MysteryBoxDto["MysteryBoxRepository/COMPLEX_FETCHER_FOR_FRONT"]>();
Taro.useLoad((ops) => {
  console.log(ops);
  api.mysteryBoxForFrontController.findById({ id: ops.id }).then((res) => {
    res.products = res.products.sort((a, b) => a.price - b.price);
    box.value = res;
  });
  marginTop.value = Taro.getMenuButtonBoundingClientRect().top + "px";
});
const dialogVisible = ref(false);
const activeProduct = ref<Product>();
const handleProductClick = (product: Product) => {
  activeProduct.value = product;
  dialogVisible.value = true;
};
const handleCreateOrder = (count: number) => {
  Taro.navigateTo({
    url: "./box-order-create",
    success() {
      Taro.eventCenter.trigger("items", [{ box: box.value, count }]);
    },
  });
};
Taro.showShareMenu({
  withShareTicket: true,
  showShareItems: ["wechatFriends", "wechatMoment"],
});
const marginTop = ref("0");
</script>

<template>
  <div>
    <product-dialog
      v-if="activeProduct"
      v-model:visible="dialogVisible"
      :product="activeProduct"
    ></product-dialog>
    <scroll-view class="box-details" :scroll-y="true" v-if="box">
      <div class="box-info">
        <div class="name">{{ box.name }}</div>
        <div class="probability">
          <image src="@/assets/icons/baodi.png" mode="heightFix"></image>
          <div class="value">
            价值 {{ box.products[0].price }}-{{
              box.products[box.products.length - 1].price
            }}元
          </div>
        </div>
      </div>
      <div class="product-list">
        <div
          :class="[
            'product',
            product.qualityType == 'LEGENDARY' ? 'legendary' : '',
            product.qualityType == 'HIDDEN' ? 'hidden' : '',
            product.qualityType == 'GENERAL' ? 'general' : '',
          ]"
          v-for="product in box.products"
          :key="product.id"
          @click="handleProductClick(product)"
        >
          <image class="cover" :src="product.cover" mode="heightFix"></image>
          <div class="background-text"></div>
        </div>
      </div>
      <nut-divider class="divider">商品介绍</nut-divider>
      <div class="details">
        <div class="product" v-for="product in box.products" :key="product.id">
          <div class="row">
            {{ Dictionaries["QualityType"][product.qualityType].keyName }} :
            {{ product.name }}
          </div>
          <div
            class="row"
            v-for="spec in product.specifications"
            :key="spec.name"
          >
            {{ spec.name }} : {{ spec.values.join(",") }}
          </div>
          <div class="row">品牌: {{ product.brand }}</div>
          <image class="cover" :src="product.cover" mode="widthFix"></image>
        </div>
      </div>
      <nut-divider class="divider">购买说明</nut-divider>
      <div class="description">
        <div>【超神款】：概率为【0.13%】</div>
        <div>【隐藏款】：概率为【7.46%】</div>
        <div>【普通款】：概率为【92.41%】</div>
      </div>
    </scroll-view>
    <div class="box-bar" v-if="box">
      <div class="buttons">
        <div class="single button" @click="handleCreateOrder(1)">
          <span>￥{{ box.price }}</span>
          <span class="desc">一发入魂</span>
        </div>
        <div class="multiple button" @click="handleCreateOrder(5)">
          <span>￥{{ box.price * 5 }}</span> <span class="desc">五发不重</span>
        </div>
      </div>
    </div>
  </div>
</template>

<style lang="scss">
page {
  background-color: #f5f5f5;
}

.box-details {
  box-sizing: border-box;
  height: 100vh;
  background-size: cover;
  background: url("../../assets/images/background.jpg") top no-repeat;
  padding-top: v-bind(marginTop);
  .divider {
    color: rgba(black, 0.5);
  }
  .box-info {
    padding: 30px;
    .name {
      color: white;
      font-size: 32px;
      margin-bottom: 20px;
    }

    .probability {
      display: flex;
      align-items: center;

      image {
        height: 40px;
      }

      .value {
        margin-left: 20px;
        font-size: 28px;
        background-color: rgba(white, 0.1);
        padding: 7px 20px;
        border-radius: 9999px;
        color: rgba(red, 0.6);
      }
    }
  }

  .product-list {
    padding: 30px;
    display: grid;
    grid-template-columns: repeat(3, 1fr);
    grid-column-gap: 42px;
    grid-row-gap: 40px;
    justify-content: center;
    .product {
      height: 300px;
      display: flex;
      flex-direction: column;
      align-items: center;
      justify-content: center;

      .cover {
        height: 200px;
      }
    }

    .legendary {
      background-image: url("../../assets/icons/legendary-style.png");
      background-repeat: no-repeat;
      background-size: contain;

      .background-text {
        background-image: url("../../assets/icons/legendary-star.png");
        background-repeat: no-repeat;
        background-size: contain;
        width: 200px;
        height: 66px;
        background-position: 50%;
        margin-top: 10px;
      }
    }

    .hidden {
      background-image: url("../../assets/icons/hidden-style.png");
      background-repeat: no-repeat;
      background-size: contain;

      .background-text {
        background-image: url("../../assets/icons/hidden-star.png");
        background-repeat: no-repeat;
        background-size: contain;
        width: 200px;
        height: 66px;
        background-position: 50%;
      }
    }

    .general {
      background-image: url("../../assets/icons/general-style.png");
      background-repeat: no-repeat;
      background-size: contain;
    }
  }
  .details {
    .product {
      padding: 30px;
      margin: 50px 20px 20px;
      border-radius: 20px;
      background-color: rgba(white, 0.4);
      .cover {
        width: 100%;
      }
      .row {
        display: flex;
        padding: 10px 0;
        color: rgba(black, 0.6);
      }
    }
  }
  .description {
    padding-bottom: 150px;
    div {
      padding: 20px;
      background-color: white;
    }
  }
}
.box-bar {
  display: flex;
  justify-content: flex-end;
  background-color: black;
  position: fixed;
  bottom: 0;
  width: 100%;
  height: 150px;
  .buttons {
    display: flex;
    align-items: center;

    .button {
      display: flex;
      align-items: center;
      justify-content: center;
      font-size: 34px;
      padding: 21px 15px;
      width: 250px;
      margin-right: 20px;
      border-radius: 10px;
      .desc {
        font-weight: bold;
        margin-left: 10px;
      }
    }
    .single {
      border: 1px solid #a455ff;
      color: #a455ff;
    }
    .multiple {
      background: linear-gradient(
        130.89deg,
        #ce97ff 6.22%,
        #d7e8ff 36.18%,
        #b3ebff 69.59%,
        #a1a1ff
      );
      border: 1px solid hsla(0, 0%, 100%, 0.4);
      color: black;
    }
  }
}
</style>
