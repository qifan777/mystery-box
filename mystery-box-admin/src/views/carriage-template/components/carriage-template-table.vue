<script lang="ts" setup>
import { inject, onMounted } from 'vue'
import { assertSuccess } from '@/utils/common'
import { api } from '@/utils/api-instance'
import { ElMessageBox } from 'element-plus'
import type { Scope } from '@/typings'
import type { CarriageTemplateDto } from '@/apis/__generated/model/dto'
import { Delete, Edit, Plus } from '@element-plus/icons-vue'
import { useTableHelper } from '@/components/base/table/table-helper'
import { useTagStore } from '@/layout/store/tag-store'

const tagStore = useTagStore()
type CarriageTemplateScope = Scope<
  CarriageTemplateDto['CarriageTemplateRepository/COMPLEX_FETCHER_FOR_ADMIN']
>
const carriageTemplateTableHelper = inject(
  'carriageTemplateTableHelper',
  useTableHelper(
    api.carriageTemplateForAdminController.query,
    api.carriageTemplateForAdminController,
    {}
  )
)
const {
  loadTableData,
  handleSortChange,
  handleSelectChange,
  getTableSelectedRows,
  reloadTableData,
  pageData,
  loading,
  queryRequest,
  table
} = carriageTemplateTableHelper
onMounted(() => {
  reloadTableData()
})
const handleEdit = (row: { id: string }) => {
  tagStore.openTag({
    path: '/carriage-template-details',
    query: {
      id: row.id
    }
  })
}
const handleCreate = () => {
  tagStore.openTag({
    path: '/carriage-template-details'
  })
}
const handleSingleDelete = (row: { id: string }) => {
  handleDelete([row.id])
}
const handleBatchDelete = () => {
  handleDelete(
    getTableSelectedRows().map((row) => {
      return row.id || ''
    })
  )
}
const handleDelete = (ids: string[]) => {
  ElMessageBox.confirm('此操作将删除数据且无法恢复, 是否继续?', '警告', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(() => {
    api.carriageTemplateForAdminController.delete({ body: ids }).then((res) => {
      assertSuccess(res).then(() => reloadTableData())
    })
  })
}
</script>
<template>
  <div>
    <div class="button-section">
      <el-button type="success" size="small" @click="handleCreate">
        <el-icon>
          <plus />
        </el-icon>
        新增
      </el-button>
      <el-button type="danger" size="small" @click="handleBatchDelete">
        <el-icon>
          <delete />
        </el-icon>
        删除
      </el-button>
    </div>
    <el-table
      ref="table"
      :data="pageData.content"
      :border="true"
      @selection-change="handleSelectChange"
      @sort-change="handleSortChange"
      v-loading="loading"
    >
      <el-table-column type="selection" width="55"></el-table-column>
      <el-table-column
        label="模板名称"
        prop="name"
        sortable="custom"
        show-overflow-tooltip
        width="120"
      >
        <template v-slot:default="{ row }: CarriageTemplateScope">
          {{ row.name }}
        </template>
      </el-table-column>
      <el-table-column
        label="模板描述"
        prop="description"
        sortable="custom"
        show-overflow-tooltip
        min-width="120"
      >
        <template v-slot:default="{ row }: CarriageTemplateScope">
          {{ row.description }}
        </template>
      </el-table-column>
      <el-table-column label="" prop="valid" sortable="custom" show-overflow-tooltip width="120">
        <template v-slot:default="{ row }: CarriageTemplateScope">
          <el-switch v-model="row.valid" disabled></el-switch>
        </template>
      </el-table-column>
      <el-table-column
        label="创建时间"
        prop="createdTime"
        sortable="custom"
        show-overflow-tooltip
        width="150"
      >
        <template v-slot:default="{ row }: CarriageTemplateScope">
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
        <template v-slot:default="{ row }: CarriageTemplateScope">
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
        <template v-slot:default="{ row }: CarriageTemplateScope">
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
        <template v-slot:default="{ row }: CarriageTemplateScope">
          {{ row.editor.nickname }}({{ row.editor.phone }})
        </template>
      </el-table-column>
      <el-table-column label="操作" fixed="right">
        <template v-slot:default="{ row }">
          <div>
            <el-button class="edit-btn" link size="small" type="primary" @click="handleEdit(row)">
              <el-icon>
                <edit />
              </el-icon>
            </el-button>
            <el-button
              class="delete-btn"
              link
              size="small"
              type="primary"
              @click="handleSingleDelete(row)"
            >
              <el-icon>
                <delete />
              </el-icon>
            </el-button>
          </div>
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
