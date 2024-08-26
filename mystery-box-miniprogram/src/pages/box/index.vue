<script setup lang="ts">
import { usePageHelper } from "@/utils/page";
import { api } from "@/utils/api-instance";
import { switchPage } from "@/utils/common";
import { computed, ref } from "vue";
import { SlideshowDto } from "@/apis/__generated/model/dto";
import Taro from "@tarojs/taro";
import { useHomeStore } from "@/stores/home-store";
type Category = { id: string; name: string };
type SlideShow = SlideshowDto["SlideshowRepository/COMPLEX_FETCHER_FOR_FRONT"];
const homeStore = useHomeStore();
homeStore.getUserInfo();
const { pageData, reloadPageData } = usePageHelper(
  api.mysteryBoxForFrontController.query,
  api.mysteryBoxForFrontController,
  { query: {} },
);
const slides = ref<
  SlideshowDto["SlideshowRepository/COMPLEX_FETCHER_FOR_FRONT"][]
>([]);
const handleSlideClick = (slide: SlideShow) => {
  if (slide.navigatorType == "BLIND_BOX") {
    switchPage(`./box-details?id=${slide.navigatorId}`);
  }
};
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
  api.mysteryBoxCategoryForFrontController
    .query({
      body: {
        pageNum: 1,
        pageSize: 1000,
        query: {},
        sorts: [{ property: "sortOrder", direction: "ASC" }],
      },
    })
    .then((res) => {
      categoryList.value = res.content;
    });
});
const categoryList = ref<Category[]>([]);
const activeCategory = ref<{ id: string; name: string }>({
  id: "",
  name: "全部",
});
const categories = computed(() => {
  const rows: Category[] = [{ name: "全部", id: "" }];
  rows.push(
    ...categoryList.value.map((row) => {
      return { ...row } satisfies Category;
    }),
  );
  return rows;
});
const handleChangeCategory = (category: Category) => {
  activeCategory.value = category;
  reloadPageData({
    pageNum: 1,
    pageSize: 10,
    query: { categoryId: category.id },
  });
};
Taro.showShareMenu({
  withShareTicket: true,
  showShareItems: ["wechatFriends", "wechatMoment"],
});
</script>

<template>
  <div class="box-list">
    <nut-swiper class="slideshow-swiper" height="400">
      <nut-swiper-item
        v-for="(slide, index) in slides"
        :key="index"
        style="height: 150px"
        @click="handleSlideClick(slide)"
      >
        <image
          :src="slide.picture"
          alt=""
          class="slideshow-image"
          mode="aspectFill"
          draggable="false"
        />
        <nut-noticebar :text="slide.content" :scrollable="false" />
      </nut-swiper-item>
    </nut-swiper>
    <scroll-view class="category-scroll" :scroll-x="true" v-if="activeCategory">
      <div class="category-list">
        <div
          :class="[
            'category',
            activeCategory.id == category.id ? 'active' : '',
          ]"
          v-for="category in categories"
          :key="category.id"
          @click="handleChangeCategory(category)"
        >
          {{ category.name }}
        </div>
      </div>
    </scroll-view>

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
@import "../../app.scss";

page {
  background-color: #f5f5f5;
}
.box-list {
  .slideshow-swiper {
    .nut-swiper-item {
      display: flex;
      flex-direction: column;
      height: 100% !important;
      width: 100% !important;
    }
    .slideshow-image {
      height: 100%;
      width: 100%;
      //height: 400px;
      //width: 100%;
    }
  }
  .category-scroll {
    padding: 30px 0;
    background-color: white;
    border-top: 1px solid rgba(black, 0.05);
    .category-list {
      white-space: nowrap;

      .category {
        font-size: 28px;
        display: inline-block;
        margin-left: 20px;
        border-radius: 999px;
        padding: 15px 30px;
        border: 1px solid rgba($primary-color, 0.3);
        color: $primary-color;

        &.active {
          background-color: $primary-color;
          color: white;
          font-weight: bold;
        }
      }
    }
  }
}
</style>
