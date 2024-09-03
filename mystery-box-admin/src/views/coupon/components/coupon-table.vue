<script lang="ts" setup>
import { inject, onMounted } from 'vue'
import { assertSuccess } from '@/utils/common'
import { api } from '@/utils/api-instance'
import { ElMessageBox } from 'element-plus'
import type { Scope } from '@/typings'
import type { CouponDto } from '@/apis/__generated/model/dto'
import { Delete, Edit, Plus } from '@element-plus/icons-vue'
import { useTableHelper } from '@/components/base/table/table-helper'
import { useTagStore } from '@/layout/store/tag-store'
import DictColumn from '@/components/dict/dict-column.vue'
import { DictConstants } from '@/apis/__generated/model/enums/DictConstants'
import CouponGiftDialog from '@/views/coupon/components/coupon-gift-dialog.vue'

const tagStore = useTagStore()
type CouponScope = Scope<CouponDto['CouponRepository/COMPLEX_FETCHER_FOR_ADMIN']>
const couponTableHelper = inject(
  'couponTableHelper',
  useTableHelper(api.couponForAdminController.query, api.couponForAdminController, {})
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
} = couponTableHelper
onMounted(() => {
  reloadTableData()
})
const handleEdit = (row: { id: string }) => {
  tagStore.openTag({
    path: '/coupon-details',
    query: {
      id: row.id
    }
  })
}
const handleCreate = () => {
  tagStore.openTag({
    path: '/coupon-details'
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
    api.couponForAdminController.delete({ body: ids }).then((res) => {
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
        label="优惠券名称"
        prop="name"
        sortable="custom"
        show-overflow-tooltip
        width="120"
      >
        <template v-slot:default="{ row }: CouponScope">
          {{ row.name }}
        </template>
      </el-table-column>
      <el-table-column
        label="门槛金额"
        prop="thresholdAmount"
        sortable="custom"
        show-overflow-tooltip
        width="120"
      >
        <template v-slot:default="{ row }: CouponScope">
          {{ row.thresholdAmount }}
        </template>
      </el-table-column>
      <el-table-column
        label="发放数量"
        prop="releasedQuantity"
        sortable="custom"
        show-overflow-tooltip
        width="120"
      >
        <template v-slot:default="{ row }: CouponScope">
          {{ row.releasedQuantity }}
        </template>
      </el-table-column>
      <el-table-column
        label="生效时间"
        prop="effectiveDate"
        sortable="custom"
        show-overflow-tooltip
        width="120"
      >
        <template v-slot:default="{ row }: CouponScope">
          {{ row.effectiveDate }}
        </template>
      </el-table-column>
      <el-table-column
        label="过期时间"
        prop="expirationDate"
        sortable="custom"
        show-overflow-tooltip
        width="120"
      >
        <template v-slot:default="{ row }: CouponScope">
          {{ row.expirationDate }}
        </template>
      </el-table-column>
      <el-table-column
        label="优惠类型"
        prop="couponType"
        sortable="custom"
        show-overflow-tooltip
        width="120"
      >
        <template v-slot:default="{ row }: CouponScope">
          <dict-column :dict-id="DictConstants.COUPON_TYPE" :value="row.couponType"></dict-column>
        </template>
      </el-table-column>
      <el-table-column
        label="使用范围类型"
        prop="scopeType"
        sortable="custom"
        show-overflow-tooltip
        width="120"
      >
        <template v-slot:default="{ row }: CouponScope">
          <dict-column
            :dict-id="DictConstants.COUPON_SCOPE_TYPE"
            :value="row.scopeType"
          ></dict-column>
        </template>
      </el-table-column>
      <el-table-column
        label="优惠金额"
        prop="amount"
        sortable="custom"
        show-overflow-tooltip
        width="120"
      >
        <template v-slot:default="{ row }: CouponScope">
          {{ row.amount }}
        </template>
      </el-table-column>
      <el-table-column
        label="折扣"
        prop="discount"
        sortable="custom"
        show-overflow-tooltip
        width="120"
      >
        <template v-slot:default="{ row }: CouponScope">
          {{ row.discount }}
        </template>
      </el-table-column>
      <el-table-column
        label="状态"
        prop="status"
        sortable="custom"
        show-overflow-tooltip
        width="120"
      >
        <template v-slot:default="{ row }: CouponScope">
          <el-switch v-model="row.status" disabled></el-switch>
        </template>
      </el-table-column>
      <el-table-column
        label="创建时间"
        prop="createdTime"
        sortable="custom"
        show-overflow-tooltip
        width="150"
      >
        <template v-slot:default="{ row }: CouponScope">
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
        <template v-slot:default="{ row }: CouponScope">
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
        <template v-slot:default="{ row }: CouponScope">
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
        <template v-slot:default="{ row }: CouponScope">
          {{ row.editor.nickname }}({{ row.editor.phone }})
        </template>
      </el-table-column>
      <el-table-column label="操作" fixed="right" min-width="120">
        <template v-slot:default="{ row }: CouponScope">
          <div class="buttons">
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
            <coupon-gift-dialog :coupon-id="row.id"></coupon-gift-dialog>
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
.buttons {
  display: flex;
}
.page {
  display: flex;
  justify-content: flex-end;
}
</style>
