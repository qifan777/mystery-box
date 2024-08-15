<script setup lang="ts">
import { onActivated, reactive, ref } from 'vue'
import { ElMessage, type FormInstance, type FormRules } from 'element-plus'
import type { SlideshowInput } from '@/apis/__generated/model/static'
import ImageUpload from '@/components/image/image-upload.vue'
import { assertFormValidate } from '@/utils/common'
import { api } from '@/utils/api-instance'
import { useFormHelper } from '@/components/base/form/form-helper'
import DictSelect from '@/components/dict/dict-select.vue'
import { DictConstants } from '@/apis/__generated/model/enums/DictConstants'

const props = defineProps<{ id?: string }>()
const formRef = ref<FormInstance>()
const initForm: SlideshowInput = { content: '', picture: '', sort: 0, valid: true }
const { formData: form, restForm } = useFormHelper<SlideshowInput>(initForm)
const rules = reactive<FormRules<SlideshowInput>>({
  picture: [{ required: true, message: '请输入公告图片', trigger: 'blur' }],
  content: [{ required: true, message: '请输入公告内容', trigger: 'blur' }],
  sort: [{ required: true, message: '请输入排序号', trigger: 'blur' }],
  valid: [{ required: true, message: '请输入是否显示', trigger: 'blur' }]
})
const handleConfirm = () => {
  formRef.value?.validate(
    assertFormValidate(() =>
      api.slideshowForAdminController.save({ body: form.value }).then(async (res) => {
        form.value.id = res
        ElMessage.success('操作成功')
      })
    )
  )
}
onActivated(() => {
  if (props.id) {
    api.slideshowForAdminController.findById({ id: props.id }).then((res) => {
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
      <el-form-item label="公告图片" prop="picture">
        <image-upload v-model="form.picture"></image-upload>
      </el-form-item>
      <el-form-item label="公告内容" prop="content">
        <el-input v-model.trim="form.content"></el-input>
      </el-form-item>
      <el-form-item label="跳转目标id" prop="navigatorId">
        <el-input v-model.trim="form.navigatorId"></el-input>
      </el-form-item>
      <el-form-item label="跳转类型" prop="navigatorType">
        <dict-select
          :dict-id="DictConstants.NAVIGATOR_TYPE"
          v-model="form.navigatorType"
        ></dict-select>
      </el-form-item>
      <el-form-item label="排序号" prop="sort">
        <el-input-number v-model="form.sort"></el-input-number>
      </el-form-item>
      <el-form-item label="是否显示" prop="valid">
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
