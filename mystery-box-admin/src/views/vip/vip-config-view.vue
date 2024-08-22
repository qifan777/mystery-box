<script setup lang="ts">
import { onMounted, ref } from 'vue'
import type { VipConfig } from '@/apis/__generated/model/static'
import { api } from '@/utils/api-instance'
import { ElMessage } from 'element-plus'

const form = ref<VipConfig>({ discount: 10, description: '' })
onMounted(() => {
  api.vipConfigController.getConfig().then((res) => {
    form.value = res
  })
})
const handleSubmit = () => {
  api.vipConfigController.saveConfig({ body: form.value }).then(() => {
    ElMessage.success('保存成功')
  })
}
</script>

<template>
  <div class="vip-config-view">
    <el-form>
      <el-form-item label="vip折扣">
        <el-input-number
          v-model="form.discount"
          controls-position="right"
          :step="0.1"
          :min="0"
          :max="10"
        ></el-input-number>
      </el-form-item>
      <el-form-item label="会员服务描述">
        <el-input type="textarea" v-model="form.description"></el-input>
      </el-form-item>
    </el-form>
    <el-button @click="handleSubmit">提交</el-button>
  </div>
</template>

<style scoped lang="scss">
.vip-config-view {
  background: white;
  padding: 20px;
  border-radius: 5px;
}
</style>
