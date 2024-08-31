<script setup lang="ts">
import { KeyValue } from "@/apis/__generated/model/static";
defineProps<{ specs: KeyValue[] }>();
</script>

<template>
  <scroll-view class="spec-scroll" :scroll-x="true">
    <div class="cell-list">
      <div class="cell" v-for="spec in specs" :key="spec.name">
        <div class="top">{{ spec.name }}</div>
        <div class="bottom">{{ spec.values.join(",") }}</div>
      </div>
    </div>
  </scroll-view>
</template>

<style lang="scss">
.spec-scroll {
  width: 450px;
  .cell-list {
    display: flex;
    align-items: center;
    .cell {
      flex-shrink: 0;
      display: flex;
      flex-direction: column;
      align-items: center;
      padding: 10px 20px;
      border-right: 1px solid rgba(white, 0.05);
      .top {
        margin-bottom: 8px;
        color: #ccc;
      }
      .bottom {
        text-align: center;
        color: #999;
        @include text-max-line(1);
        max-width: 120px;
      }
    }
  }
}
</style>
