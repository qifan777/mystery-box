<script setup lang="ts">
import { onActivated, reactive, ref } from 'vue'
import { ElMessage, type FormInstance, type FormRules } from 'element-plus'
import type { VipInput } from '@/apis/__generated/model/static'
import RemoteSelect from '@/components/base/form/remote-select.vue'
import { assertFormValidate } from '@/utils/common'
import { api } from '@/utils/api-instance'
import { useFormHelper } from '@/components/base/form/form-helper'
import { userLabelProp, userQueryOptions } from '@/views/user/store/user-store'

const props = defineProps<{ id?: string }>()
const formRef = ref<FormInstance>()
const initForm: VipInput = { endTime: '', status: false, userId: '' }
const { formData: form, restForm } = useFormHelper<VipInput>(initForm)
const rules = reactive<FormRules<VipInput>>({
  endTime: [{ required: true, message: '请输入过期时间', trigger: 'blur' }],
  userId: [{ required: true, message: '请输入用户信息', trigger: 'blur' }]
})
const handleConfirm = () => {
  formRef.value?.validate(
    assertFormValidate(() =>
      api.vipForAdminController.save({ body: form.value }).then(async (res) => {
        form.value.id = res
        ElMessage.success('操作成功')
      })
    )
  )
}
onActivated(() => {
  if (props.id) {
    api.vipForAdminController.findById({ id: props.id }).then((res) => {
      form.value = { ...res, userId: res.user.id }
    })
  } else {
    restForm()
  }
})
</script>

<template>
  <div class="form">
    <el-form labelWidth="120" class="form" ref="formRef" :model="form" :rules="rules">
      <el-form-item label="过期时间" prop="endTime">
        <el-date-picker
          v-model="form.endTime"
          type="datetime"
          placeholder="请选择日期"
          value-format="YYYY-MM-DD HH:mm:ss"
        ></el-date-picker>
      </el-form-item>
      <el-form-item label="用户信息" prop="user">
        <remote-select
          :label-prop="userLabelProp"
          :query-options="userQueryOptions"
          v-model="form.userId"
        ></remote-select>
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
