<script lang="ts" setup>
import { inject, onMounted } from 'vue'
import { api } from '@/utils/api-instance'
import type { Scope } from '@/typings'
import type { FeedbackDto } from '@/apis/__generated/model/dto'
import { useTableHelper } from '@/components/base/table/table-helper'

type FeedbackScope = Scope<FeedbackDto['FeedbackRepository/COMPLEX_FETCHER_FOR_ADMIN']>
const feedbackTableHelper = inject(
  'feedbackTableHelper',
  useTableHelper(api.feedbackForAdminController.query, api.feedbackForAdminController, {})
)
const { loadTableData, handleSortChange, reloadTableData, pageData, loading, queryRequest, table } =
  feedbackTableHelper
onMounted(() => {
  reloadTableData()
})
</script>
<template>
  <div>
    <el-table
      ref="table"
      :data="pageData.content"
      :border="true"
      @sort-change="handleSortChange"
      v-loading="loading"
    >
      <el-table-column
        label="文本内容"
        prop="content"
        sortable="custom"
        show-overflow-tooltip
        width="120"
      >
        <template v-slot:default="{ row }: FeedbackScope">
          {{ row.content }}
        </template>
      </el-table-column>
      <el-table-column>
        <template v-slot:default="{ row }: FeedbackScope">
          <el-image
            :preview-src-list="row.pictures"
            v-for="src in row.pictures"
            :src="src"
            :key="src"
            fit="cover"
          ></el-image>
        </template>
      </el-table-column>
      <el-table-column
        label="创建时间"
        prop="createdTime"
        sortable="custom"
        show-overflow-tooltip
        width="150"
      >
        <template v-slot:default="{ row }: FeedbackScope">
          {{ row.createdTime }}
        </template>
      </el-table-column>
      <el-table-column
        label="更新时间"
        prop="editedTime"
        sortable="custom"
        show-overflow-tooltip
        width="150"
      >
        <template v-slot:default="{ row }: FeedbackScope">
          {{ row.editedTime }}
        </template>
      </el-table-column>
      <el-table-column
        label="创建人"
        prop="creator.phone"
        sortable="custom"
        show-overflow-tooltip
        width="150"
      >
        <template v-slot:default="{ row }: FeedbackScope">
          {{ row.creator.nickname }}({{ row.creator.phone }})
        </template>
      </el-table-column>
      <el-table-column
        label="更新人"
        prop="editor.phone"
        sortable="custom"
        show-overflow-tooltip
        width="150"
      >
        <template v-slot:default="{ row }: FeedbackScope">
          {{ row.editor.nickname }}({{ row.editor.phone }})
        </template>
      </el-table-column>
    </el-table>
    <div class="page">
      <el-pagination
        style="margin-top: 30px"
        :current-page="queryRequest.pageNum"
        :page-size="queryRequest.pageSize"
        :page-sizes="[10, 20, 30, 40, 50]"
        :total="pageData.totalElements"
        background
        small
        layout="prev, pager, next, jumper, total, sizes"
        @current-change="(pageNum) => loadTableData({ pageNum })"
        @size-change="(pageSize) => loadTableData({ pageSize })"
      />
    </div>
  </div>
</template>

<style lang="scss" scoped>
.button-section {
  margin: 20px 0;
}

.page {
  display: flex;
  justify-content: flex-end;
}
</style>
