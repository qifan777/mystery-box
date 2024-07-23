<script lang="ts" setup>
import { onMounted } from 'vue'
import type { Scope } from '@/typings'
import type { ProductDto } from '@/apis/__generated/model/dto'
import { Close } from '@element-plus/icons-vue'

const products = defineModel<ProductDto['ProductRepository/COMPLEX_FETCHER_FOR_ADMIN'][]>(
  'products',
  { required: true }
)
type Product = ProductDto['ProductRepository/COMPLEX_FETCHER_FOR_ADMIN']
type ProductScope = Scope<Product>
onMounted(() => {})
const handleRemove = (row: Product) => {
  products.value = products.value.filter((product) => product.id !== row.id)
}
</script>
<template>
  <div>
    <el-table ref="table" :data="products" :border="true">
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
      <el-table-column label="操作" fixed="right">
        <template v-slot:default="{ row }: ProductScope">
          <el-button type="danger" link :icon="Close" @click="handleRemove(row)"></el-button>
        </template>
      </el-table-column>
    </el-table>
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
