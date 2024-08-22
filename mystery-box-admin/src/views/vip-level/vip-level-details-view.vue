<script setup lang="ts">
import { onActivated, reactive, ref } from 'vue'
import { ElMessage, type FormInstance, type FormRules } from 'element-plus'
import type { VipLevelInput } from '@/apis/__generated/model/static'
import { assertFormValidate } from '@/utils/common'
import { api } from '@/utils/api-instance'
import { useFormHelper } from '@/components/base/form/form-helper'

const props = defineProps<{ id?: string }>()
const formRef = ref<FormInstance>()
const initForm: VipLevelInput = { days: 0, name: '', price: 0 }
const { formData: form, restForm } = useFormHelper<VipLevelInput>(initForm)
const rules = reactive<FormRules<VipLevelInput>>({
  name: [{ required: true, message: '请输入等级名称', trigger: 'blur' }],
  price: [{ required: true, message: '请输入价格', trigger: 'blur' }],
  days: [{ required: true, message: '请输入天数', trigger: 'blur' }]
})
const handleConfirm = () => {
  formRef.value?.validate(
    assertFormValidate(() =>
      api.vipLevelForAdminController.save({ body: form.value }).then(async (res) => {
        form.value.id = res
        ElMessage.success('操作成功')
      })
    )
  )
}
onActivated(() => {
  if (props.id) {
    api.vipLevelForAdminController.findById({ id: props.id }).then((res) => {
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
      <el-form-item label="等级名称" prop="name">
        <el-input v-model.trim="form.name"></el-input>
      </el-form-item>
      <el-form-item label="价格" prop="price">
        <el-input-number v-model="form.price"></el-input-number>
      </el-form-item>
      <el-form-item label="天数" prop="days">
        <el-input-number v-model="form.days"></el-input-number>
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
