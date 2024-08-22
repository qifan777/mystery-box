<script lang="ts" setup>
import RemoteSelect from '@/components/base/form/remote-select.vue'
import type { VipOrderSpec } from '@/apis/__generated/model/static'
import { userLabelProp, userQueryOptions } from '@/views/user/store/user-store'
import { vipLevelQueryOptions } from '@/views/vip-level/vip-level'

const emit = defineEmits<{ search: [value: VipOrderSpec]; reset: [] }>()
const query = defineModel<VipOrderSpec>('query', { required: true })
</script>
<template>
  <div class="search">
    <el-form inline label-width="80" size="small">
      <el-form-item label="用户">
        <remote-select
          :label-prop="userLabelProp"
          :query-options="userQueryOptions"
          v-model="query.userId"
        ></remote-select>
      </el-form-item>
      <el-form-item label="vip套餐">
        <remote-select
          label-prop="name"
          :query-options="vipLevelQueryOptions"
          v-model="query.vipLevelId"
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
