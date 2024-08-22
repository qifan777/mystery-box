<script lang="ts" setup>
import RemoteSelect from '@/components/base/form/remote-select.vue'
import type { CouponUserRelSpec } from '@/apis/__generated/model/static'
import { DictConstants } from '@/apis/__generated/model/enums/DictConstants'
import DictSelect from '@/components/dict/dict-select.vue'
import { couponQueryOptions } from '@/views/coupon/coupon'
import { userQueryOptions } from '@/views/user/store/user-store'

const emit = defineEmits<{ search: [value: CouponUserRelSpec]; reset: [] }>()
const query = defineModel<CouponUserRelSpec>('query', { required: true })
</script>
<template>
  <div class="search">
    <el-form inline label-width="80" size="small">
      <el-form-item label="获取途径类型">
        <dict-select
          :dict-id="DictConstants.COUPON_RECEIVE_TYPE"
          v-model="query.receiveType"
        ></dict-select>
      </el-form-item>
      <el-form-item label="优惠券">
        <remote-select
          label-prop="name"
          :query-options="couponQueryOptions"
          v-model="query.couponId"
        ></remote-select>
      </el-form-item>
      <el-form-item label="优惠券状态">
        <dict-select
          :dict-id="DictConstants.COUPON_USE_STATUS"
          v-model="query.status"
        ></dict-select>
      </el-form-item>
      <el-form-item label="用户信息">
        <remote-select
          label-prop="name"
          :query-options="userQueryOptions"
          v-model="query.userId"
        ></remote-select>
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
