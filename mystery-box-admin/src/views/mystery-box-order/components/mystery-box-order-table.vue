<script lang="ts" setup>
import { inject, onMounted } from 'vue'
import { api } from '@/utils/api-instance'
import { ElMessage, ElMessageBox } from 'element-plus'
import type { Scope } from '@/typings'
import type { MysteryBoxOrderDto } from '@/apis/__generated/model/dto'
import { Close, Promotion } from '@element-plus/icons-vue'
import { useTableHelper } from '@/components/base/table/table-helper'
import DictColumn from '@/components/dict/dict-column.vue'
import { DictConstants } from '@/apis/__generated/model/enums/DictConstants'

type MysteryBoxOrderScope = Scope<
  MysteryBoxOrderDto['MysteryBoxOrderRepository/COMPLEX_FETCHER_FOR_ADMIN']
>
type MysteryBoxOrderItemScope = Scope<
  MysteryBoxOrderDto['MysteryBoxOrderRepository/COMPLEX_FETCHER_FOR_ADMIN']['items'][0]
>
type ProductScope =
  MysteryBoxOrderDto['MysteryBoxOrderRepository/COMPLEX_FETCHER_FOR_ADMIN']['items'][0]['products'][0]
const mysteryBoxOrderTableHelper = inject(
  'mysteryBoxOrderTableHelper',
  useTableHelper(
    api.mysteryBoxOrderForAdminController.query,
    api.mysteryBoxOrderForAdminController,
    {}
  )
)
const {
  loadTableData,
  handleSortChange,
  handleSelectChange,
  reloadTableData,
  pageData,
  loading,
  queryRequest,
  table
} = mysteryBoxOrderTableHelper
onMounted(() => {
  reloadTableData()
})
const handleClose = (row: { id: string }) => {
  ElMessageBox.confirm('是否确认退款?', '警告', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(() => {
    api.mysteryBoxOrderForAdminController.paidCancelForAdmin({ id: row.id }).then(() => {
      ElMessage.success('退款成功')
      reloadTableData()
    })
  })
}
const handleDeliver = (row: { id: string }) => {
  ElMessageBox.prompt('请输入物流单号', '发货', {
    confirmButtonText: '确定',
    cancelButtonText: '取消'
  }).then((res) => {
    api.mysteryBoxOrderForAdminController
      .deliver({ id: row.id, trackingNumber: res.value })
      .then(() => {
        reloadTableData()
        ElMessage.success('发货成功')
      })
  })
}
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
      <el-table-column type="expand">
        <template v-slot:default="{ row }: MysteryBoxOrderScope">
          <el-table :data="row.items">
            <el-table-column type="expand">
              <el-table>
                <el-table-column label="购买数量" prop="name" sortable="custom">
                  <template v-slot:default="{ row }: ProductScope">
                    {{ row.productCount }}
                  </template>
                </el-table-column>
                <el-table-column label="商品名称" prop="name" sortable="custom">
                  <template v-slot:default="{ row }: ProductScope">
                    {{ row.product.name }}
                  </template>
                </el-table-column>
                <el-table-column label="封面" prop="cover" sortable="custom">
                  <template v-slot:default="{ row }: ProductScope">
                    <el-avatar :src="row.product.cover" alt=""></el-avatar>
                  </template>
                </el-table-column>
                <el-table-column label="价格" prop="price" sortable="custom">
                  <template v-slot:default="{ row }: ProductScope">
                    {{ row.product.price }}
                  </template>
                </el-table-column>
                <el-table-column label="库存" prop="stock" sortable="custom">
                  <template v-slot:default="{ row }: ProductScope">
                    {{ row.product.stock }}
                  </template>
                </el-table-column>
              </el-table>
            </el-table-column>
            <el-table-column label="盲盒名字" prop="name" show-overflow-tooltip width="120">
              <template v-slot:default="{ row }: MysteryBoxOrderItemScope">
                {{ row.mysteryBox.name }}
              </template>
            </el-table-column>
            <el-table-column label="盲盒详情" prop="details" show-overflow-tooltip width="120">
              <template v-slot:default="{ row }: MysteryBoxOrderItemScope">
                {{ row.mysteryBox.details }}
              </template>
            </el-table-column>
            <el-table-column label="购买提示" prop="tips" show-overflow-tooltip width="120">
              <template v-slot:default="{ row }: MysteryBoxOrderItemScope">
                {{ row.mysteryBox.tips }}
              </template>
            </el-table-column>
            <el-table-column label="价格" prop="price" show-overflow-tooltip width="120">
              <template v-slot:default="{ row }: MysteryBoxOrderItemScope">
                {{ row.mysteryBox.price }}
              </template>
            </el-table-column>
            <el-table-column label="封面" prop="cover" show-overflow-tooltip width="120">
              <template v-slot:default="{ row }: MysteryBoxOrderItemScope">
                <el-avatar :src="row.mysteryBox.cover" alt=""></el-avatar>
              </template>
            </el-table-column>
          </el-table>
        </template>
      </el-table-column>
      <el-table-column
        label="订单状态"
        prop="status"
        sortable="custom"
        min-width="100"
        show-overflow-tooltip
      >
        <template v-slot:default="{ row }: MysteryBoxOrderScope">
          <dict-column
            :dict-id="DictConstants.PRODUCT_ORDER_STATUS"
            :value="row.status"
          ></dict-column>
        </template>
      </el-table-column>
      <el-table-column label="支付详情">
        <el-table-column label="VIP优惠" prop="payAmount" sortable="custom">
          <template
            v-slot:default="{
              row: {
                baseOrder: { payment }
              }
            }: MysteryBoxOrderScope"
          >
            {{ payment.vipAmount }}
          </template>
        </el-table-column>
        <el-table-column label="邮费" prop="payAmount" sortable="custom">
          <template
            v-slot:default="{
              row: {
                baseOrder: { payment }
              }
            }: MysteryBoxOrderScope"
          >
            {{ payment.deliveryFee }}
          </template>
        </el-table-column>
        <el-table-column label="商品金额" prop="payAmount" sortable="custom">
          <template
            v-slot:default="{
              row: {
                baseOrder: { payment }
              }
            }: MysteryBoxOrderScope"
          >
            {{ payment.productAmount }}
          </template>
        </el-table-column>
        <el-table-column label="实付金额" prop="payAmount" sortable="custom">
          <template
            v-slot:default="{
              row: {
                baseOrder: { payment }
              }
            }: MysteryBoxOrderScope"
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
            }: MysteryBoxOrderScope"
          >
            {{ payment.payTime }}
          </template>
        </el-table-column>
      </el-table-column>
      <el-table-column label="地址详情">
        <el-table-column label="姓名" prop="realName">
          <template v-slot:default="{ row: { baseOrder } }: MysteryBoxOrderScope">
            {{ baseOrder.address?.realName }}
          </template>
        </el-table-column>
        <el-table-column label="电话" prop="phoneNumber" width="130" show-overflow-tooltip>
          <template v-slot:default="{ row: { baseOrder } }: MysteryBoxOrderScope">
            {{ baseOrder.address?.phoneNumber }}
          </template>
        </el-table-column>
        <el-table-column label="地址信息" prop="address" width="100" show-overflow-tooltip>
          <template v-slot:default="{ row: { baseOrder } }: MysteryBoxOrderScope">
            <div>
              {{ baseOrder.address?.details + ' ' + baseOrder.address?.houseNumber }}
            </div>
          </template>
        </el-table-column>
      </el-table-column>
      <el-table-column
        label="备注"
        prop="remark"
        sortable="custom"
        min-width="100"
        show-overflow-tooltip
      >
        <template v-slot:default="{ row: { baseOrder } }: MysteryBoxOrderScope">
          {{ baseOrder.remark }}
        </template>
      </el-table-column>
      <el-table-column
        label="创建时间"
        prop="createdTime"
        sortable="custom"
        show-overflow-tooltip
        width="150"
      >
        <template v-slot:default="{ row }: MysteryBoxOrderScope">
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
        <template v-slot:default="{ row }: MysteryBoxOrderScope">
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
        <template v-slot:default="{ row }: MysteryBoxOrderScope">
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
        <template v-slot:default="{ row }: MysteryBoxOrderScope">
          {{ row.editor.nickname }}({{ row.editor.phone }})
        </template>
      </el-table-column>
      <el-table-column label="操作" fixed="right">
        <template v-slot:default="{ row }">
          <div>
            <el-button
              class="edit-btn"
              link
              size="small"
              type="primary"
              @click="handleClose(row)"
              v-if="row.status === 'TO_BE_RECEIVED' || row.status === 'TO_BE_DELIVERED'"
            >
              <el-icon>
                <close />
              </el-icon>
            </el-button>
            <el-button
              class="edit-btn"
              link
              size="small"
              type="success"
              v-if="row.status === 'TO_BE_DELIVERED' || row.status == 'TO_BE_RECEIVED'"
              @click="handleDeliver(row)"
            >
              <el-icon>
                <promotion></promotion>
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
        @current-change="(pageNum: number) => loadTableData({ pageNum })"
        @size-change="(pageSize: number) => loadTableData({ pageSize })"
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
