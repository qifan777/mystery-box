<script setup lang="ts">
import { onActivated, reactive, ref } from 'vue'
import { ElMessage, type FormInstance, type FormRules } from 'element-plus'
import type { CouponInput } from '@/apis/__generated/model/static'
import { DictConstants } from '@/apis/__generated/model/enums/DictConstants'
import { assertFormValidate } from '@/utils/common'
import { api } from '@/utils/api-instance'
import { useFormHelper } from '@/components/base/form/form-helper'
import DictSelect from '@/components/dict/dict-select.vue'

const props = defineProps<{ id?: string }>()
const formRef = ref<FormInstance>()
const initForm: CouponInput = {
  amount: 0,
  couponType: 'REDUCE',
  discount: 10,
  effectiveDate: '',
  expirationDate: '',
  name: '',
  scopeType: 'PRODUCT',
  thresholdAmount: 0
}
const { formData: form, restForm } = useFormHelper<CouponInput>(initForm)
const rules = reactive<FormRules<CouponInput>>({
  name: [{ required: true, message: '请输入优惠券名称', trigger: 'blur' }],
  thresholdAmount: [{ required: true, message: '请输入门槛金额', trigger: 'blur' }],
  effectiveDate: [{ required: true, message: '请输入生效时间', trigger: 'blur' }],
  expirationDate: [{ required: true, message: '请输入过期时间', trigger: 'blur' }],
  couponType: [{ required: true, message: '请输入优惠类型', trigger: 'change' }],
  scopeType: [{ required: true, message: '请输入使用范围类型', trigger: 'change' }],
  amount: [{ required: true, message: '请输入优惠金额', trigger: 'blur' }],
  discount: [{ required: true, message: '请输入折扣', trigger: 'blur' }]
})
const handleConfirm = () => {
  formRef.value?.validate(
    assertFormValidate(() =>
      api.couponForAdminController.save({ body: form.value }).then(async (res) => {
        form.value.id = res
        ElMessage.success('操作成功')
      })
    )
  )
}
onActivated(() => {
  if (props.id) {
    api.couponForAdminController.findById({ id: props.id }).then((res) => {
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
      <el-form-item label="优惠券名称" prop="name">
        <el-input v-model.trim="form.name"></el-input>
      </el-form-item>
      <el-form-item label="门槛金额" prop="thresholdAmount">
        <el-input-number v-model="form.thresholdAmount"></el-input-number>
      </el-form-item>
      <el-form-item label="发放数量" prop="releasedQuantity">
        <el-input-number v-model="form.releasedQuantity"></el-input-number>
      </el-form-item>
      <el-form-item label="生效时间" prop="effectiveDate">
        <el-date-picker
          v-model="form.effectiveDate"
          type="datetime"
          placeholder="请选择日期"
          value-format="YYYY-MM-DD HH:mm:ss"
        ></el-date-picker>
      </el-form-item>
      <el-form-item label="过期时间" prop="expirationDate">
        <el-date-picker
          v-model="form.expirationDate"
          type="datetime"
          placeholder="请选择日期"
          value-format="YYYY-MM-DD HH:mm:ss"
        ></el-date-picker>
      </el-form-item>
      <el-form-item label="优惠类型" prop="couponType">
        <dict-select :dict-id="DictConstants.COUPON_TYPE" v-model="form.couponType"></dict-select>
      </el-form-item>
      <el-form-item label="使用范围类型" prop="scopeType">
        <dict-select
          :dict-id="DictConstants.COUPON_SCOPE_TYPE"
          v-model="form.scopeType"
        ></dict-select>
      </el-form-item>
      <el-form-item label="优惠金额" prop="amount">
        <el-input-number v-model="form.amount"></el-input-number>
      </el-form-item>
      <el-form-item label="折扣" prop="discount">
        <el-input-number v-model="form.discount"></el-input-number>
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
