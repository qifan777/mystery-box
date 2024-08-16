<script setup lang="ts">
import { ref } from "vue";
import { FeedbackInput } from "@/apis/__generated/model/static";
import { beforeXhrUpload, onSuccess, UploadFile } from "@/utils/upload";
import { api } from "@/utils/api-instance";
import Taro from "@tarojs/taro";

const form = ref<FeedbackInput>({ content: "", pictures: [] });
const fileList = ref<UploadFile[]>([]);
const submit = () => {
  form.value.pictures = fileList.value.map((file) => file.url);
  api.feedbackForFrontController.save({ body: form.value }).then(() => {
    Taro.showToast({ title: "反馈成功" });
    Taro.navigateBack();
  });
};
</script>

<template>
  <div class="feedback-page">
    <div class="content">
      <textarea
        v-model="form.content"
        placeholder="请输入反馈内容"
        maxlength="2000"
        autoHeight
      ></textarea>
      <nut-uploader
        v-model:file-list="fileList"
        maximum="5"
        accept="image/*"
        :source-type="['album', 'camera']"
        :before-xhr-upload="beforeXhrUpload"
        @success="onSuccess"
      >
      </nut-uploader>
    </div>
    <view class="bottom">
      <nut-button class="submit-btn" block type="primary" @click="submit"
        >提交</nut-button
      >
    </view>
  </div>
</template>

<style lang="scss">
.feedback-page {
  .content {
    border-radius: 20rpx;
    margin: 30rpx;
    padding: 20rpx;
    background-color: white;
    height: 83vh;
    .text-wrapper {
      margin-bottom: 30rpx;
    }
  }

  .bottom {
    background-color: white;
    position: fixed;
    bottom: 200px;
    width: 100%;
    color: white;
    .submit-btn {
      width: 500rpx;
      margin: auto;
    }
  }
}
</style>
