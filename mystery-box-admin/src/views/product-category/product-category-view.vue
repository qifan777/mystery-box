<script lang="ts" setup>
import ProductCategoryTable from './components/product-category-table.vue'
import ProductCategoryQuery from './components/product-category-query.vue'
import { useTableHelper } from '@/components/base/table/table-helper'
import { api } from '@/utils/api-instance'
import { provide } from 'vue'
import type { ProductCategorySpec } from '@/apis/__generated/model/static'
import { useQueryHelper } from '@/components/base/query/query-helper'

const initQuery: ProductCategorySpec = {}
const tableHelper = useTableHelper(
  api.productCategoryForAdminController.query,
  api.productCategoryForAdminController,
  initQuery
)
const { query, restQuery } = useQueryHelper<ProductCategorySpec>(initQuery)
provide('productCategoryTableHelper', tableHelper)
</script>
<template>
  <div class="product-category-view">
    <product-category-query
      v-model:query="query"
      @reset="restQuery"
      @search="tableHelper.reloadTableData({ query })"
    ></product-category-query>
    <product-category-table></product-category-table>
  </div>
</template>

<style lang="scss" scoped>
.product-category-view {
  background: white;
  padding: 20px;
  border-radius: 5px;
}
</style>
