<script setup lang="ts">
import { onActivated, reactive, ref } from 'vue'
import { ElMessage, type FormInstance, type FormRules } from 'element-plus'
import type { CouponUserRelInput } from '@/apis/__generated/model/static'
import RemoteSelect from '@/components/base/form/remote-select.vue'
import { DictConstants } from '@/apis/__generated/model/enums/DictConstants'
import { assertFormValidate } from '@/utils/common'
import { api } from '@/utils/api-instance'
import { useFormHelper } from '@/components/base/form/form-helper'
import { couponQueryOptions } from '@/views/coupon/coupon'
import { userLabelProp, userQueryOptions } from '@/views/user/store/user-store'
import DictSelect from '@/components/dict/dict-select.vue'

const props = defineProps<{ id?: string }>()
const formRef = ref<FormInstance>()
const initForm: CouponUserRelInput = {
  couponId: '',
  receiveType: 'GIFT',
  status: 'UNUSED',
  userId: ''
}
const { formData: form, restForm } = useFormHelper<CouponUserRelInput>(initForm)
const rules = reactive<FormRules<CouponUserRelInput>>({
  receiveType: [{ required: true, message: '请输入获取途径类型', trigger: 'change' }],
  couponId: [{ required: true, message: '请输入优惠券', trigger: 'blur' }],
  status: [{ required: true, message: '请输入优惠券状态', trigger: 'change' }],
  userId: [{ required: true, message: '请输入用户信息', trigger: 'blur' }]
})
const handleConfirm = () => {
  formRef.value?.validate(
    assertFormValidate(() =>
      api.couponUserRelForAdminController.save({ body: form.value }).then(async (res) => {
        form.value.id = res
        ElMessage.success('操作成功')
      })
    )
  )
}
onActivated(() => {
  if (props.id) {
    api.couponUserRelForAdminController.findById({ id: props.id }).then((res) => {
      form.value = { ...res, userId: res.user.id, couponId: res.coupon.id }
    })
  } else {
    restForm()
  }
})
</script>

<template>
  <div class="form">
    <el-form labelWidth="120" class="form" ref="formRef" :model="form" :rules="rules">
      <el-form-item label="获取途径类型" prop="receiveType">
        <dict-select
          :dict-id="DictConstants.COUPON_RECEIVE_TYPE"
          v-model="form.receiveType"
        ></dict-select>
      </el-form-item>
      <el-form-item label="优惠券" prop="couponId">
        <remote-select
          label-prop="name"
          :query-options="couponQueryOptions"
          v-model="form.couponId"
        ></remote-select>
      </el-form-item>
      <el-form-item label="优惠券状态" prop="status">
        <dict-select :dict-id="DictConstants.COUPON_USE_STATUS" v-model="form.status"></dict-select>
      </el-form-item>
      <el-form-item label="用户信息" prop="userId">
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
