<script setup lang="ts">
import { onActivated, reactive, ref } from 'vue'
import { ElMessage, type FormInstance, type FormRules } from 'element-plus'
import type { MysteryBoxInput } from '@/apis/__generated/model/static'
import ImageUpload from '@/components/image/image-upload.vue'
import { assertFormValidate } from '@/utils/common'
import { api } from '@/utils/api-instance'
import { useFormHelper } from '@/components/base/form/form-helper'

const props = defineProps<{ id?: string }>()
const formRef = ref<FormInstance>()
const initForm: MysteryBoxInput = { cover: '', details: '', name: '', price: 0, tips: '' }
const { formData: form, restForm } = useFormHelper<MysteryBoxInput>(initForm)
const rules = reactive<FormRules<MysteryBoxInput>>({
  name: [{ required: true, message: '请输入盲盒名字', trigger: 'blur' }],
  details: [{ required: true, message: '请输入盲盒详情', trigger: 'blur' }],
  tips: [{ required: true, message: '请输入购买提示', trigger: 'blur' }],
  price: [{ required: true, message: '请输入价格', trigger: 'blur' }],
  cover: [{ required: true, message: '请输入封面', trigger: 'blur' }]
})
const handleConfirm = () => {
  formRef.value?.validate(
    assertFormValidate(() =>
      api.mysteryBoxForAdminController.save({ body: form.value }).then(async (res) => {
        form.value.id = res
        ElMessage.success('操作成功')
      })
    )
  )
}
onActivated(() => {
  if (props.id) {
    api.mysteryBoxForAdminController.findById({ id: props.id }).then((res) => {
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
      <el-form-item label="盲盒名字" prop="name">
        <el-input v-model.trim="form.name"></el-input>
      </el-form-item>
      <el-form-item label="盲盒详情" prop="details">
        <el-input v-model="form.details" type="textarea"></el-input>
      </el-form-item>
      <el-form-item label="购买提示" prop="tips">
        <el-input v-model.trim="form.tips"></el-input>
      </el-form-item>
      <el-form-item label="价格" prop="price">
        <el-input-number v-model="form.price"></el-input-number>
      </el-form-item>
      <el-form-item label="封面" prop="cover">
        <image-upload v-model="form.cover"></image-upload>
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
