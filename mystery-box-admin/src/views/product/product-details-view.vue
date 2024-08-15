<script setup lang="ts">
import { onActivated, reactive, ref } from 'vue'
import { ElMessage, type FormInstance, type FormRules } from 'element-plus'
import type { ProductInput } from '@/apis/__generated/model/static'
import RemoteSelect from '@/components/base/form/remote-select.vue'
import ImageUpload from '@/components/image/image-upload.vue'
import KeyValueInput from '@/components/key-value/key-value-input.vue'
import ValueInput from '@/components/key-value/value-input.vue'
import { assertFormValidate } from '@/utils/common'
import { api } from '@/utils/api-instance'
import { useFormHelper } from '@/components/base/form/form-helper'
import { productCategoryQueryOptions } from '@/views/product-category/product-category'

const props = defineProps<{ id?: string }>()
const formRef = ref<FormInstance>()
const initForm: ProductInput = {
  attributes: [],
  brand: '',
  categoryId: '',
  cover: '',
  description: '',
  name: '',
  price: 0,
  specifications: [],
  tags: []
}
const { formData: form, restForm } = useFormHelper<ProductInput>(initForm)
const rules = reactive<FormRules<ProductInput>>({
  name: [{ required: true, message: '请输入名称', trigger: 'blur' }],
  price: [{ required: true, message: '请输入价格', trigger: 'blur' }],
  cover: [{ required: true, message: '请输入封面', trigger: 'blur' }],
  brand: [{ required: true, message: '请输入品牌', trigger: 'blur' }],
  categoryId: [{ required: true, message: '请输入类别', trigger: 'blur' }],
  description: [{ required: true, message: '请输入描述', trigger: 'blur' }],
  tags: [{ required: true, message: '请输入标签', trigger: 'blur' }],
  specifications: [{ required: true, message: '请输入规格', trigger: 'blur' }]
  // attributes: [{ required: true, message: '请输入属性', trigger: 'blur' }]
})
const handleConfirm = () => {
  formRef.value?.validate(
    assertFormValidate(() =>
      api.productForAdminController.save({ body: form.value }).then(async (res) => {
        form.value.id = res
        ElMessage.success('操作成功')
      })
    )
  )
}
onActivated(() => {
  if (props.id) {
    api.productForAdminController.findById({ id: props.id }).then((res) => {
      form.value = res
    })
  } else {
    restForm()
  }
})
</script>

<template>
  <div class="form">
    <el-form labelWidth="120" class="form" ref="formRef" :model="form" :rules="rules">
      <el-form-item label="名称" prop="name">
        <el-input v-model.trim="form.name"></el-input>
      </el-form-item>
      <el-form-item label="价格" prop="price">
        <el-input-number v-model="form.price"></el-input-number>
      </el-form-item>
      <el-form-item label="封面" prop="cover">
        <image-upload v-model="form.cover"></image-upload>
      </el-form-item>
      <el-form-item label="品牌" prop="brand">
        <el-input v-model.trim="form.brand"></el-input>
      </el-form-item>
      <el-form-item label="类别" prop="categoryId">
        <remote-select
          label-prop="name"
          :query-options="productCategoryQueryOptions"
          v-model="form.categoryId"
        ></remote-select>
      </el-form-item>
      <el-form-item label="描述" prop="description">
        <el-input v-model="form.description" type="textarea"></el-input>
      </el-form-item>
      <el-form-item label="标签" prop="tags">
        <value-input v-model="form.tags"></value-input>
      </el-form-item>
      <el-form-item label="规格" prop="specifications">
        <key-value-input v-model="form.specifications"></key-value-input>
      </el-form-item>
      <el-form-item label="属性" prop="attributes">
        <key-value-input v-model="form.attributes"></key-value-input>
      </el-form-item>
    </el-form>
    <el-row justify="center">
      <el-button type="primary" @click="handleConfirm">提交</el-button>
    </el-row>
  </div>
</template>

<style scoped lang="scss">
.form {
  background: white;
  padding: 20px;
  border-radius: 5px;
}
</style>
