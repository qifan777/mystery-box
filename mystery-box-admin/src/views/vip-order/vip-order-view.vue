<script lang="ts" setup>
import VipOrderTable from './components/vip-order-table.vue'
import VipOrderQuery from './components/vip-order-query.vue'
import { useTableHelper } from '@/components/base/table/table-helper'
import { api } from '@/utils/api-instance'
import { provide } from 'vue'
import type { VipOrderSpec } from '@/apis/__generated/model/static'
import { useQueryHelper } from '@/components/base/query/query-helper'

const initQuery: VipOrderSpec = {}
const tableHelper = useTableHelper(
  api.vipOrderForAdminController.query,
  api.vipOrderForAdminController,
  initQuery
)
const { query, restQuery } = useQueryHelper<VipOrderSpec>(initQuery)
provide('vipOrderTableHelper', tableHelper)
</script>
<template>
  <div class="vip-order-view">
    <vip-order-query
      v-model:query="query"
      @reset="restQuery"
      @search="tableHelper.reloadTableData({ query })"
    ></vip-order-query>
    <vip-order-table></vip-order-table>
  </div>
</template>

<style lang="scss" scoped>
.vip-order-view {
  background: white;
  padding: 20px;
  border-radius: 5px;
}
</style>
