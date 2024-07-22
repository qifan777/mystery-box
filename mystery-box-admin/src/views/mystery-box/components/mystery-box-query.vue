<script lang="ts" setup>
import type { MysteryBoxSpec } from '@/apis/__generated/model/static'

const emit = defineEmits<{ search: [value: MysteryBoxSpec]; rest: [] }>()
const query = defineModel<MysteryBoxSpec>('query', { required: true })
</script>
<template>
  <div class="search">
    <el-form inline label-width="80" size="small">
      <el-form-item label="盲盒名字">
        <el-input v-model.trim="query.name"></el-input>
      </el-form-item>
      <el-form-item label="盲盒详情">
        <el-input v-model.trim="query.details"></el-input>
      </el-form-item>
      <el-form-item label="购买提示">
        <el-input v-model.trim="query.tips"></el-input>
      </el-form-item>
      <el-form-item label="价格">
        <el-input-number v-model="query.price" controls-position="right"></el-input-number>
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
