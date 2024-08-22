<script lang="ts" setup>
import CouponUserRelTable from './components/coupon-user-rel-table.vue'
import CouponUserRelQuery from './components/coupon-user-rel-query.vue'
import { useTableHelper } from '@/components/base/table/table-helper'
import { api } from '@/utils/api-instance'
import { provide } from 'vue'
import type { CouponUserRelSpec } from '@/apis/__generated/model/static'
import { useQueryHelper } from '@/components/base/query/query-helper'

const initQuery: CouponUserRelSpec = {}
const tableHelper = useTableHelper(
  api.couponUserRelForAdminController.query,
  api.couponUserRelForAdminController,
  initQuery
)
const { query, restQuery } = useQueryHelper<CouponUserRelSpec>(initQuery)
provide('couponUserRelTableHelper', tableHelper)
</script>
<template>
  <div class="coupon-user-rel-view">
    <coupon-user-rel-query
      v-model:query="query"
      @reset="restQuery"
      @search="tableHelper.reloadTableData({ query })"
    ></coupon-user-rel-query>
    <coupon-user-rel-table></coupon-user-rel-table>
  </div>
</template>

<style lang="scss" scoped>
.coupon-user-rel-view {
  background: white;
  padding: 20px;
  border-radius: 5px;
}
</style>
