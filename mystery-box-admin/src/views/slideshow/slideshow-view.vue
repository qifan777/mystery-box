<script lang="ts" setup>
import SlideshowTable from './components/slideshow-table.vue'
import SlideshowQuery from './components/slideshow-query.vue'
import { useTableHelper } from '@/components/base/table/table-helper'
import { api } from '@/utils/api-instance'
import { provide } from 'vue'
import type { SlideshowSpec } from '@/apis/__generated/model/static'
import { useQueryHelper } from '@/components/base/query/query-helper'

const initQuery: SlideshowSpec = {}
const tableHelper = useTableHelper(
  api.slideshowForAdminController.query,
  api.slideshowForAdminController,
  initQuery
)
const { query, restQuery } = useQueryHelper<SlideshowSpec>(initQuery)
provide('slideshowTableHelper', tableHelper)
</script>
<template>
  <div class="slideshow-view">
    <slideshow-query
      v-model:query="query"
      @reset="restQuery"
      @search="tableHelper.reloadTableData({ query })"
    ></slideshow-query>
    <slideshow-table></slideshow-table>
  </div>
</template>

<style lang="scss" scoped>
.slideshow-view {
  background: white;
  padding: 20px;
  border-radius: 5px;
}
</style>
