<script lang="ts" setup>
import RemoteSelect from '@/components/base/form/remote-select.vue'
import type { ProductSpec } from '@/apis/__generated/model/static'
import { productCategoryQueryOptions } from '@/views/product-category/product-category'

const emit = defineEmits<{ search: [value: ProductSpec]; rest: [] }>()
const query = defineModel<ProductSpec>('query', { required: true })
</script>
<template>
  <div class="search">
    <el-form inline label-width="80" size="small">
      <el-form-item label="名称">
        <el-input v-model.trim="query.name"></el-input>
      </el-form-item>
      <el-form-item label="价格">
        <el-input-number v-model="query.price" controls-position="right"></el-input-number>
      </el-form-item>
      <el-form-item label="品牌">
        <el-input v-model.trim="query.brand"></el-input>
      </el-form-item>
      <el-form-item label="类别">
        <remote-select
          label-prop="name"
          :query-options="productCategoryQueryOptions"
          v-model="query.categoryId"
        ></remote-select>
      </el-form-item>
      <el-form-item label="描述">
        <el-input v-model.trim="query.description"></el-input>
      </el-form-item>
      <el-form-item label="标签"> </el-form-item>
      <el-form-item label="规格"> </el-form-item>
      <el-form-item label="属性"> </el-form-item>
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
