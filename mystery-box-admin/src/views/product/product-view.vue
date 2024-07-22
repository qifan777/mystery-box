<script lang="ts" setup>
import ProductTable from './components/product-table.vue'
import ProductQuery from './components/product-query.vue'
import { useTableHelper } from '@/components/base/table/table-helper'
import { api } from '@/utils/api-instance'
import { provide } from 'vue'
import type { ProductSpec } from '@/apis/__generated/model/static'
import { useQueryHelper } from '@/components/base/query/query-helper'

const initQuery: ProductSpec = {}
const tableHelper = useTableHelper(
  api.productForAdminController.query,
  api.productForAdminController,
  initQuery
)
const { query, restQuery } = useQueryHelper<ProductSpec>(initQuery)
provide('productTableHelper', tableHelper)
</script>
<template>
  <div class="product-view">
    <product-query
      v-model:query="query"
      @reset="restQuery"
      @search="tableHelper.reloadTableData({ query })"
    ></product-query>
    <product-table></product-table>
  </div>
</template>

<style lang="scss" scoped>
.product-view {
  background: white;
  padding: 20px;
  border-radius: 5px;
}
</style>
