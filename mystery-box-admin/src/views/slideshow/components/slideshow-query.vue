<script lang="ts" setup>
import type { SlideshowSpec } from '@/apis/__generated/model/static'
import { DictConstants } from '@/apis/__generated/model/enums/DictConstants'
import DictSelect from '@/components/dict/dict-select.vue'

const emit = defineEmits<{ search: [value: SlideshowSpec]; rest: [] }>()
const query = defineModel<SlideshowSpec>('query', { required: true })
</script>
<template>
  <div class="search">
    <el-form inline label-width="80" size="small">
      <el-form-item label="公告内容">
        <el-input v-model.trim="query.content"></el-input>
      </el-form-item>
      <el-form-item label="跳转目标id">
        <el-input v-model.trim="query.navigatorId"></el-input>
      </el-form-item>
      <el-form-item label="跳转类型">
        <dict-select
          :dict-id="DictConstants.NAVIGATOR_TYPE"
          v-model="query.navigatorType"
        ></dict-select>
      </el-form-item>
      <el-form-item label="排序号">
        <el-input-number v-model="query.sort" controls-position="right"></el-input-number>
      </el-form-item>
      <el-form-item label="是否显示">
        <el-switch v-model="query.valid"></el-switch>
      </el-form-item>
      <el-form-item label=" ">
        <div class="btn-wrapper">
          <el-button type="primary" size="small" @click="() => emit('search', query)">
            查询
          </el-button>
          <el-button type="warning" size="small" @click="() => emit('rest')"> 重置</el-button>
        </div>
      </el-form-item>
    </el-form>
  </div>
</template>

<style lang="scss" scoped>
:deep(.el-form-item) {
  margin-bottom: 5px;
}

.search {
  display: flex;
  flex-flow: column nowrap;
  width: 100%;

  .btn-wrapper {
    margin-left: 20px;
  }
}
</style>
