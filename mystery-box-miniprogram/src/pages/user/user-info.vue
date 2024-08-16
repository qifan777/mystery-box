<template>
  <div class="user-edit">
    <nut-form @submit="submit">
      <nut-form-item label="头像">
        <nut-button
          class="choose-avatar"
          plain
          shape="square"
          open-type="chooseAvatar"
          @chooseavatar="chooseAvatar"
        >
          <template #icon>
            <user-avatar
              width="80rpx"
              height="80rpx"
              mode="aspectFill"
              class="avatar"
              :src="form.avatar"
            ></user-avatar>
          </template>
        </nut-button>
      </nut-form-item>
      <nut-form-item label="昵称">
        <nut-input
          type="nickname"
          name="nickname"
          placeholder="请输入昵称"
          v-model="form.nickname"
        ></nut-input>
      </nut-form-item>
      <nut-form-item label="性别">
        <popup-picker
          placeholder="请选择性别"
          v-model="form.gender"
          :columns="genderColumns"
        ></popup-picker>
      </nut-form-item>
      <nut-button class="submit" type="primary" block form-type="submit"
        >提交</nut-button
      >
    </nut-form>
  </div>
</template>

<script setup lang="ts">
import { uploadFile, UploadFile } from "@/utils/upload";
import { ref, watch } from "vue";
import { Dictionaries } from "@/apis/__generated/model/enums/DictConstants";
import PopupPicker from "@/components/popup-picker/popup-picker.vue";
import { useHomeStore } from "@/stores/home-store";
import { storeToRefs } from "pinia";
import Taro from "@tarojs/taro";
import { api } from "@/utils/api-instance";
import { UserInfoInput } from "@/apis/__generated/model/static";
const genderColumns = [
  {
    text: Dictionaries.Gender.MALE.keyName,
    value: Dictionaries.Gender.MALE.keyEnName,
  },
  {
    text: Dictionaries.Gender.FEMALE.keyName,
    value: Dictionaries.Gender.FEMALE.keyEnName,
  },
  {
    text: Dictionaries.Gender.PRIVATE.keyName,
    value: Dictionaries.Gender.PRIVATE.keyEnName,
  },
];

const homeStore = useHomeStore();
const { userInfo } = storeToRefs(homeStore);
const fileList = ref<UploadFile[]>([]);
const form = ref<UserInfoInput>({
  gender: "PRIVATE",
});
watch(
  () => userInfo.value,
  () => {
    if (userInfo.value.avatar) {
      fileList.value = [
        {
          name: userInfo.value.avatar,
          url: userInfo.value.avatar,
          status: "success",
          message: "上传成功",
          type: "image",
        },
      ];
    }
    form.value = { ...userInfo.value };
  },
  {
    immediate: true,
  },
);
const chooseAvatar = (e: any) => {
  uploadFile(e.detail.avatarUrl).then((res) => {
    form.value.avatar = res;
  });
};

const submit = (e: any) => {
  form.value.nickname = e.detail.value.nickname;
  api.userForFrontController.updateInfo({ body: form.value }).then(() => {
    Taro.showToast({ title: "保存成功" });
    Taro.navigateBack();
    homeStore.getUserInfo();
  });
};
</script>

<style lang="scss">
.user-edit {
  .choose-avatar {
    background-color: rgba(white, 0);
    border: 0 !important;
    padding: 0;
    .avatar {
      display: flex;
    }
  }
  .submit {
    width: 600px;
    margin: 40px auto;
  }
}
</style>
