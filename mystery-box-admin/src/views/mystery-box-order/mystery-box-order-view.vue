<script lang="ts" setup>
import MysteryBoxOrderTable from './components/mystery-box-order-table.vue'
import MysteryBoxOrderQuery from './components/mystery-box-order-query.vue'
import { useTableHelper } from '@/components/base/table/table-helper'
import { api } from '@/utils/api-instance'
import { provide } from 'vue'
import type { MysteryBoxOrderSpec } from '@/apis/__generated/model/static'
import { useQueryHelper } from '@/components/base/query/query-helper'

const initQuery: MysteryBoxOrderSpec = {}
const tableHelper = useTableHelper(
  api.mysteryBoxOrderForAdminController.query,
  api.mysteryBoxOrderForAdminController,
  initQuery
)
const { query, restQuery } = useQueryHelper<MysteryBoxOrderSpec>(initQuery)
provide('mysteryBoxOrderTableHelper', tableHelper)
</script>
<template>
  <div class="mystery-box-order-view">
    <mystery-box-order-query
      v-model:query="query"
      @reset="restQuery"
      @search="tableHelper.reloadTableData({ query })"
    ></mystery-box-order-query>
    <mystery-box-order-table></mystery-box-order-table>
  </div>
</template>

<style lang="scss" scoped>
.mystery-box-order-view {
  background: white;
  padding: 20px;
  border-radius: 5px;
}
</style>
