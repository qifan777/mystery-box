<script lang="ts" setup>
import VipTable from './components/vip-table.vue'
import VipQuery from './components/vip-query.vue'
import { useTableHelper } from '@/components/base/table/table-helper'
import { api } from '@/utils/api-instance'
import { provide } from 'vue'
import type { VipSpec } from '@/apis/__generated/model/static'
import { useQueryHelper } from '@/components/base/query/query-helper'

const initQuery: VipSpec = {}
const tableHelper = useTableHelper(
  api.vipForAdminController.query,
  api.vipForAdminController,
  initQuery
)
const { query, restQuery } = useQueryHelper<VipSpec>(initQuery)
provide('vipTableHelper', tableHelper)
</script>
<template>
  <div class="vip-view">
    <vip-query
      v-model:query="query"
      @reset="restQuery"
      @search="tableHelper.reloadTableData({ query })"
    ></vip-query>
    <vip-table></vip-table>
  </div>
</template>

<style lang="scss" scoped>
.vip-view {
  background: white;
  padding: 20px;
  border-radius: 5px;
}
</style>
