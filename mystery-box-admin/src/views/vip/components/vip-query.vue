<script lang="ts" setup>
import RemoteSelect from '@/components/base/form/remote-select.vue'
import type { VipSpec } from '@/apis/__generated/model/static'
import DatetimePicker from '@/components/datetime/datetime-picker.vue'
import { userLabelProp, userQueryOptions } from '@/views/user/store/user-store'

const emit = defineEmits<{ search: [value: VipSpec]; reset: [] }>()
const query = defineModel<VipSpec>('query', { required: true })
</script>
<template>
  <div class="search">
    <el-form inline label-width="80" size="small">
      <el-form-item label="过期时间">
        <datetime-picker
          v-model:min-date-time="query.minEndTime"
          v-model:max-date-time="query.maxEndTime"
        >
        </datetime-picker>
      </el-form-item>
      <el-form-item label="用户信息">
        <remote-select
          :label-prop="userLabelProp"
          :query-options="userQueryOptions"
          v-model="query.userId"
        ></remote-select>
      </el-form-item>
      <el-form-item label="是否过期">
        <el-switch v-model="query.status"></el-switch>
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
