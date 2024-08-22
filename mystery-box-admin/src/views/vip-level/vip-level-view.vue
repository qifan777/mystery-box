<script lang="ts" setup>
import VipLevelTable from './components/vip-level-table.vue'
import VipLevelQuery from './components/vip-level-query.vue'
import { useTableHelper } from '@/components/base/table/table-helper'
import { api } from '@/utils/api-instance'
import { provide } from 'vue'
import type { VipLevelSpec } from '@/apis/__generated/model/static'
import { useQueryHelper } from '@/components/base/query/query-helper'

const initQuery: VipLevelSpec = {}
const tableHelper = useTableHelper(
  api.vipLevelForAdminController.query,
  api.vipLevelForAdminController,
  initQuery
)
const { query, restQuery } = useQueryHelper<VipLevelSpec>(initQuery)
provide('vipLevelTableHelper', tableHelper)
</script>
<template>
  <div class="vip-level-view">
    <vip-level-query
      v-model:query="query"
      @reset="restQuery"
      @search="tableHelper.reloadTableData({ query })"
    ></vip-level-query>
    <vip-level-table></vip-level-table>
  </div>
</template>

<style lang="scss" scoped>
.vip-level-view {
  background: white;
  padding: 20px;
  border-radius: 5px;
}
</style>
