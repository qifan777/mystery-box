<script lang="ts" setup>
import { inject, onMounted } from 'vue'
import { api } from '@/utils/api-instance'
import type { Scope } from '@/typings'
import type { ProductDto } from '@/apis/__generated/model/dto'
import { useTableHelper } from '@/components/base/table/table-helper'

type Product = ProductDto['ProductRepository/COMPLEX_FETCHER_FOR_ADMIN']
type ProductScope = Scope<Product>
const productTableHelper = inject(
  'productTableHelper',
  useTableHelper(api.productForAdminController.query, api.productForAdminController, {})
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
} = productTableHelper
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
      <el-table-column label="名称" prop="name" sortable="custom" show-overflow-tooltip width="120">
        <template v-slot:default="{ row }: ProductScope">
          {{ row.name }}
        </template>
      </el-table-column>
      <el-table-column
        label="价格"
        prop="price"
        sortable="custom"
        show-overflow-tooltip
        width="120"
      >
        <template v-slot:default="{ row }: ProductScope">
          {{ row.price }}
        </template>
      </el-table-column>
      <el-table-column
        label="封面"
        prop="cover"
        sortable="custom"
        show-overflow-tooltip
        width="120"
      >
        <template v-slot:default="{ row }: ProductScope">
          <el-avatar :src="row.cover" alt=""></el-avatar>
        </template>
      </el-table-column>
      <el-table-column
        label="品牌"
        prop="brand"
        sortable="custom"
        show-overflow-tooltip
        width="120"
      >
        <template v-slot:default="{ row }: ProductScope">
          {{ row.brand }}
        </template>
      </el-table-column>
      <el-table-column
        label="类别"
        prop="categoryId"
        sortable="custom"
        show-overflow-tooltip
        width="120"
      >
        <template v-slot:default="{ row }: ProductScope">
          {{ row.category.name }}
        </template>
      </el-table-column>
      <el-table-column
        label="描述"
        prop="description"
        sortable="custom"
        show-overflow-tooltip
        width="120"
      >
        <template v-slot:default="{ row }: ProductScope">
          {{ row.description }}
        </template>
      </el-table-column>
      <el-table-column label="标签" prop="tags" sortable="custom" show-overflow-tooltip width="120">
        <template v-slot:default="{ row }: ProductScope">
          {{ row.tags }}
        </template>
      </el-table-column>
      <el-table-column
        label="规格"
        prop="specifications"
        sortable="custom"
        show-overflow-tooltip
        width="120"
      >
        <template v-slot:default="{ row }: ProductScope">
          {{ row.specifications }}
        </template>
      </el-table-column>
      <el-table-column
        label="属性"
        prop="attributes"
        sortable="custom"
        show-overflow-tooltip
        width="120"
      >
        <template v-slot:default="{ row }: ProductScope">
          {{ row.attributes }}
        </template>
      </el-table-column>
      <el-table-column
        label="创建时间"
        prop="createdTime"
        sortable="custom"
        show-overflow-tooltip
        width="150"
      >
        <template v-slot:default="{ row }: ProductScope">
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
        <template v-slot:default="{ row }: ProductScope">
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
        <template v-slot:default="{ row }: ProductScope">
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
        <template v-slot:default="{ row }: ProductScope">
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
