<template>
  <div class="tags">
    <!-- 当页签太多溢出时，点击左方向可以向左滚动 -->
    <el-button link @click="scroll('left')"
      ><el-icon><ArrowLeftBold /></el-icon>
    </el-button>
    <div class="tags-wrapper" ref="tagWrapperRef">
      <!-- 当页签新增或者删除时的过渡动画 -->
      <transition-group name="list">
        <router-tag
          ref="tagRefs"
          :tag="tag"
          :index="index"
          v-for="(tag, index) in tagStore.tags"
          :key="tag.id"
        ></router-tag>
      </transition-group>
    </div>
    <!-- 当页签太多溢出时，点击右方向可以向右滚动 -->
    <el-button link @click="scroll('right')">
      <el-icon><ArrowRightBold /></el-icon>
    </el-button>
  </div>
</template>

<script setup lang="ts">
import { useTagStore } from '@/layout/store/tag-store'
import RouterTag from './router-tag.vue'
import { nextTick, ref, watch } from 'vue'
import { ArrowLeftBold, ArrowRightBold } from '@element-plus/icons-vue'
const tagStore = useTagStore()
const tagWrapperRef = ref<HTMLDivElement>()
const tagRefs = ref<InstanceType<typeof RouterTag>[]>([])
const scroll = (direction: 'right' | 'left') => {
  if (tagWrapperRef.value) {
    tagWrapperRef.value.scrollTo({
      left: tagWrapperRef.value.scrollLeft + (direction == 'right' ? 100 : -100),
      behavior: 'smooth'
    })
  }
}
watch(
  () => tagStore.activeTag,
  (value) => {
    nextTick(() => {
      let width = 0
      for (let i = 0; i < tagStore.tags.findIndex((row) => row.id == value.id); i++) {
        width += tagRefs.value[i].$el.clientWidth
      }
      console.log(width)
      tagWrapperRef.value?.scrollTo({
        left: width,
        behavior: 'smooth'
      })
    })
  }
)
</script>

<style lang="scss" scoped>
.list-enter-active,
.list-leave-active {
  transition: all 0.5s ease;
}
.list-enter-from,
.list-leave-to {
  opacity: 0;
  transform: translateY(30px);
}
.tags {
  display: flex;
  justify-content: space-between;
  width: calc(100% - 40px);
  .tags-wrapper {
    box-sizing: border-box;
    display: flex;
    align-items: center;
    width: 100%;
    overflow-x: scroll;

    &::-webkit-scrollbar {
      // --bar-width: 2px;
      height: 0px;
    }

    &::-webkit-scrollbar-track {
      background-color: transparent;
    }

    &::-webkit-scrollbar-thumb {
      background-color: rgba(0, 0, 0, 0.1);
      border-radius: 20px;
      background-clip: content-box;
      border: 1px solid transparent;
    }
  }
}
</style>
