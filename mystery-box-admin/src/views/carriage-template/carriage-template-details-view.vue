<script setup lang="ts">
import { onActivated, reactive, ref } from 'vue'
import { ElMessage, type FormInstance, type FormRules } from 'element-plus'
import type { CarriageTemplateInput } from '@/apis/__generated/model/static'
import { assertFormValidate } from '@/utils/common'
import { api } from '@/utils/api-instance'
import { useFormHelper } from '@/components/base/form/form-helper'
import CarriageConfig from './components/carriage-config.vue'

const props = defineProps<{ id?: string }>()
const formRef = ref<FormInstance>()
const initForm: CarriageTemplateInput = {
  description: '',
  configs: [],
  name: '',
  valid: false
}
const { formData: form, restForm } = useFormHelper<CarriageTemplateInput>(initForm)
const rules = reactive<FormRules<CarriageTemplateInput>>({
  name: [{ required: true, message: '请输入模板名称', trigger: 'blur' }],
  description: [{ required: true, message: '请输入模板描述', trigger: 'blur' }],
  valid: [{ required: true, message: '请输入', trigger: 'blur' }]
})
const handleConfirm = () => {
  formRef.value?.validate(
    assertFormValidate(() =>
      api.carriageTemplateForAdminController.save({ body: form.value }).then(async (res) => {
        form.value.id = res
        ElMessage.success('操作成功')
      })
    )
  )
}
onActivated(() => {
  if (props.id) {
    api.carriageTemplateForAdminController.findById({ id: props.id }).then((res) => {
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
      <el-form-item label="模板名称" prop="name">
        <el-input v-model.trim="form.name"></el-input>
      </el-form-item>
      <el-form-item label="模板描述" prop="description">
        <el-input type="textarea" v-model.trim="form.description"></el-input>
      </el-form-item>
      <el-form-item label="运费配置">
        <carriage-config v-model="form.configs"></carriage-config>
      </el-form-item>
      <el-form-item label="是否生效" prop="valid">
        <el-switch v-model="form.valid"></el-switch>
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
