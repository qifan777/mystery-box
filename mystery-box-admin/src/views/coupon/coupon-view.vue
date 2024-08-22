<script lang="ts" setup>
import CouponTable from './components/coupon-table.vue'
import CouponQuery from './components/coupon-query.vue'
import { useTableHelper } from '@/components/base/table/table-helper'
import { api } from '@/utils/api-instance'
import { provide } from 'vue'
import type { CouponSpec } from '@/apis/__generated/model/static'
import { useQueryHelper } from '@/components/base/query/query-helper'

const initQuery: CouponSpec = {}
const tableHelper = useTableHelper(
  api.couponForAdminController.query,
  api.couponForAdminController,
  initQuery
)
const { query, restQuery } = useQueryHelper<CouponSpec>(initQuery)
provide('couponTableHelper', tableHelper)
</script>
<template>
  <div class="coupon-view">
    <coupon-query
      v-model:query="query"
      @reset="restQuery"
      @search="tableHelper.reloadTableData({ query })"
    ></coupon-query>
    <coupon-table></coupon-table>
  </div>
</template>

<style lang="scss" scoped>
.coupon-view {
  background: white;
  padding: 20px;
  border-radius: 5px;
}
</style>
