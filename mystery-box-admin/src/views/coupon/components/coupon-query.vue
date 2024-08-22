<script lang="ts" setup>
import type { CouponSpec } from '@/apis/__generated/model/static'
import { DictConstants } from '@/apis/__generated/model/enums/DictConstants'
import DatetimePicker from '@/components/datetime/datetime-picker.vue'
import DictSelect from '@/components/dict/dict-select.vue'

const emit = defineEmits<{ search: [value: CouponSpec]; reset: [] }>()
const query = defineModel<CouponSpec>('query', { required: true })
</script>
<template>
  <div class="search">
    <el-form inline label-width="80" size="small">
      <el-form-item label="优惠券名称">
        <el-input v-model.trim="query.name"></el-input>
      </el-form-item>
      <el-form-item label="门槛金额">
        <el-input-number
          v-model="query.thresholdAmount"
          controls-position="right"
        ></el-input-number>
      </el-form-item>
      <el-form-item label="发放数量">
        <el-input-number
          v-model="query.releasedQuantity"
          controls-position="right"
        ></el-input-number>
      </el-form-item>
      <el-form-item label="生效时间">
        <datetime-picker
          v-model:min-date-time="query.minEffectiveDate"
          v-model:max-date-time="query.maxEffectiveDate"
        >
        </datetime-picker>
      </el-form-item>
      <el-form-item label="过期时间">
        <datetime-picker
          v-model:min-date-time="query.minExpirationDate"
          v-model:max-date-time="query.maxExpirationDate"
        >
        </datetime-picker>
      </el-form-item>
      <el-form-item label="优惠类型">
        <dict-select :dict-id="DictConstants.COUPON_TYPE" v-model="query.couponType"></dict-select>
      </el-form-item>
      <el-form-item label="使用范围类型">
        <dict-select
          :dict-id="DictConstants.COUPON_SCOPE_TYPE"
          v-model="query.scopeType"
        ></dict-select>
      </el-form-item>
      <el-form-item label="优惠金额">
        <el-input-number v-model="query.amount" controls-position="right"></el-input-number>
      </el-form-item>
      <el-form-item label="折扣">
        <el-input-number v-model="query.discount" controls-position="right"></el-input-number>
      </el-form-item>
      <el-form-item label=" ">
        <div class="btn-wrapper">
          <el-button type="primary" size="small" @click="() => emit('search', query)">
            查询
          </el-button>
          <el-button type="warning" size="small" @click="() => emit('reset')"> 重置</el-button>
        </div>
      </el-form-item>
    </el-form>
  </div>
</template>

<style lang="scss" scoped>
:deep(.el-form-item) {
  margin-bottom: 5px;
}

.search {
  display: flex;
  flex-flow: column nowrap;
  width: 100%;

  .btn-wrapper {
    margin-left: 20px;
  }
}
</style>
