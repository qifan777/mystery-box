<script lang="ts" setup>
import { inject, onMounted } from 'vue'
import { api } from '@/utils/api-instance'
import type { Scope } from '@/typings'
import type { VipOrderDto } from '@/apis/__generated/model/dto'
import { useTableHelper } from '@/components/base/table/table-helper'

type VipOrderScope = Scope<VipOrderDto['VipOrderRepository/COMPLEX_FETCHER_FOR_ADMIN']>
const vipOrderTableHelper = inject(
  'vipOrderTableHelper',
  useTableHelper(api.vipOrderForAdminController.query, api.vipOrderForAdminController, {})
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
} = vipOrderTableHelper
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
      @selection-change="handleSelectChange"
      @sort-change="handleSortChange"
      v-loading="loading"
    >
      <el-table-column type="selection" width="55"></el-table-column>
      <el-table-column label="用户" prop="user" sortable="custom" show-overflow-tooltip width="120">
        <template v-slot:default="{ row }: VipOrderScope">
          {{ row.creator.nickname }}({{ row.creator.phone }})
        </template>
      </el-table-column>
      <el-table-column
        label="vip套餐"
        prop="vipLevel"
        sortable="custom"
        show-overflow-tooltip
        width="120"
      >
        <template v-slot:default="{ row }: VipOrderScope">
          {{ row.vipLevel.name }}
        </template>
      </el-table-column>
      <el-table-column label="实付金额" prop="payAmount" sortable="custom">
        <template
          v-slot:default="{
            row: {
              baseOrder: { payment }
            }
          }: VipOrderScope"
        >
          {{ payment.payAmount }}
        </template>
      </el-table-column>
      <el-table-column
        label="支付时间"
        prop="payTime"
        sortable="custom"
        min-width="130"
        show-overflow-tooltip
      >
        <template
          v-slot:default="{
            row: {
              baseOrder: { payment }
            }
          }: VipOrderScope"
        >
          {{ payment.payTime }}
        </template>
      </el-table-column>
      <el-table-column
        label="创建时间"
        prop="createdTime"
        sortable="custom"
        show-overflow-tooltip
        width="150"
      >
        <template v-slot:default="{ row }: VipOrderScope">
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
        <template v-slot:default="{ row }: VipOrderScope">
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
        <template v-slot:default="{ row }: VipOrderScope">
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
        <template v-slot:default="{ row }: VipOrderScope">
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
