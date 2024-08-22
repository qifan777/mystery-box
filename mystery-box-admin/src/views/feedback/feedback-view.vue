<script lang="ts" setup>
import FeedbackTable from './components/feedback-table.vue'
import FeedbackQuery from './components/feedback-query.vue'
import { useTableHelper } from '@/components/base/table/table-helper'
import { api } from '@/utils/api-instance'
import { provide } from 'vue'
import type { FeedbackSpec } from '@/apis/__generated/model/static'
import { useQueryHelper } from '@/components/base/query/query-helper'

const initQuery: FeedbackSpec = {}
const tableHelper = useTableHelper(
  api.feedbackForAdminController.query,
  api.feedbackForAdminController,
  initQuery
)
const { query, restQuery } = useQueryHelper<FeedbackSpec>(initQuery)
provide('feedbackTableHelper', tableHelper)
</script>
<template>
  <div class="feedback-view">
    <feedback-query
      v-model:query="query"
      @reset="restQuery"
      @search="tableHelper.reloadTableData({ query })"
    ></feedback-query>
    <feedback-table></feedback-table>
  </div>
</template>

<style lang="scss" scoped>
.feedback-view {
  background: white;
  padding: 20px;
  border-radius: 5px;
}
</style>
