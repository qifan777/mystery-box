<template>
  <div>
    <image
      v-if="src"
      :style="{ width, height, borderRadius: radius }"
      v-bind="{ ...$attrs, src, mode }"
    ></image>
    <image
      v-else
      :style="{ width, height, borderRadius: radius }"
      v-bind="{ ...$attrs, mode }"
      src="@/assets/icons/perople.png"
    ></image>
  </div>
</template>

<script lang="ts" setup>
import { ImageProps } from "@tarojs/components";

withDefaults(
  defineProps<{
    src?: string;
    radius?: string;
    width?: string;
    height?: string;
    mode?: ImageProps["mode"];
  }>(),
  {
    width: "60rpx",
    height: "60rpx",
    radius: "50rpx",
    mode: "widthFix",
  },
);
defineOptions({
  inheritAttrs: false,
});
</script>

<style></style>
