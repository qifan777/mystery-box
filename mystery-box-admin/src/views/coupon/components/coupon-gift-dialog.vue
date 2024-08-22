<script setup lang="ts">
import UserChooseTable from '@/views/user/components/user-choose-table.vue'
import { Present } from '@element-plus/icons-vue'
import { inject, ref } from 'vue'
import { api } from '@/utils/api-instance'
import { useTableHelper } from '@/components/base/table/table-helper'
import { ElMessage } from 'element-plus'
const userTableHelper = useTableHelper(
  api.userForAdminController.query,
  api.userForAdminController,
  {}
)
inject('userTableHelper', userTableHelper)
const props = defineProps<{ couponId: string }>()
const visible = ref(false)
const handleConfirm = async () => {
  await api.couponForAdminController.gift({
    body: {
      userIds: userTableHelper.getTableSelectedRows().map((row) => row.id),
      id: props.couponId
    }
  })
  ElMessage.success('发送成功')
  visible.value = false
}
</script>

<template>
  <el-button type="primary" link size="small">
    <el-icon>
      <present></present>
    </el-icon>
  </el-button>
  <el-dialog v-model="visible">
    <user-choose-table></user-choose-table>
    <template #footer>
      <el-button type="primary" @click="handleConfirm">确认</el-button>
    </template>
  </el-dialog>
</template>

<style scoped lang="scss"></style>
