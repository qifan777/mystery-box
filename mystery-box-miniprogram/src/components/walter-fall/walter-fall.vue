<template>
  <div class="water-fall">
    <!-- 左侧瀑布流   -->
    <div class="left-water-fall">
      <div v-for="(item, index) in leftList" :key="index" class="item-wrapper">
        <slot :item="item" name="itemLeft"></slot>
      </div>
    </div>
    <!--  右侧瀑布流  -->
    <div class="right-water-fall">
      <div v-for="(item, index) in rightList" :key="index" class="item-wrapper">
        <slot :item="item" name="itemRight"></slot>
      </div>
    </div>
  </div>
</template>

<script lang="ts" setup generic="T">
import { computed } from "vue";
// 接收输入的列表数据
const props = defineProps<{ dataList: T[] }>();
defineSlots<{
  itemRight: (props: { item: T }) => void;
  itemLeft: (props: { item: T }) => void;
}>();
// 划分左右两边的列表
const leftList = computed(() =>
  props.dataList.filter((_value, index) => index % 2 === 0),
);
const rightList = computed(() =>
  props.dataList.filter((_value, index) => index % 2 !== 0),
);
</script>

<style lang="scss">
.water-fall {
  display: grid;
  grid-template-columns: 1fr 1fr;
  grid-gap: 15px;
  margin: 15px;
  .item-wrapper {
    margin-top: 15px;
  }
  .left-water-fall {
    justify-content: center;
  }

  .right-water-fall {
    justify-content: center;
  }
}
</style>
