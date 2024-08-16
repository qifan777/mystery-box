<script setup lang="ts">
import { usePageHelper } from "@/utils/page";
import { api } from "@/utils/api-instance";
import { switchPage } from "@/utils/common";
import { ref } from "vue";
import { SlideshowDto } from "@/apis/__generated/model/dto";
import Taro from "@tarojs/taro";
import { useHomeStore } from "@/stores/home-store";
const homeStore = useHomeStore();
homeStore.getUserInfo();
const { pageData } = usePageHelper(
  api.mysteryBoxForFrontController.query,
  api.mysteryBoxForFrontController,
  { query: {} },
);
const slides = ref<
  SlideshowDto["SlideshowRepository/COMPLEX_FETCHER_FOR_FRONT"][]
>([]);
Taro.useLoad(() => {
  api.slideshowForFrontController
    .query({
      body: {
        pageNum: 1,
        pageSize: 1000,
        query: { valid: true },
        sorts: [{ property: "sort", direction: "ASC" }],
      },
    })
    .then((res) => {
      slides.value = res.content;
    });
});
Taro.showShareMenu({
  withShareTicket: true,
  showShareItems: ["wechatFriends", "wechatMoment"],
});
</script>

<template>
  <div class="box-list">
    <nut-swiper height="200">
      <nut-swiper-item
        v-for="(slide, index) in slides"
        :key="index"
        style="height: 150px"
      >
        <image
          :src="slide.picture"
          alt=""
          style="height: 100%; width: 100%"
          mode="aspectFill"
          draggable="false"
        />
        <nut-noticebar :text="slide.content" :scrollable="false" />
      </nut-swiper-item>
    </nut-swiper>

    <walter-fall :data-list="pageData.content" class="product-walter-fall">
      <template #itemLeft="{ item }">
        <product-cover
          :product="item"
          @click="switchPage('/pages/box/box-details?id=' + item.id)"
        ></product-cover>
      </template>
      <template #itemRight="{ item }">
        <product-cover
          :product="item"
          @click="switchPage('/pages/box/box-details?id=' + item.id)"
        ></product-cover>
      </template>
    </walter-fall>
  </div>
</template>

<style lang="scss">
page {
  background-color: #f5f5f5;
}
</style>
