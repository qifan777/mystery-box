import { defineStore } from "pinia";
import { ref } from "vue";
import { api } from "@/utils/api-instance";
import { UserDto } from "@/apis/__generated/model/dto";

export const useHomeStore = defineStore("home", () => {
  const userInfo = ref<UserDto["UserRepository/USER_ROLE_FETCHER"]>({
    createdTime: "",
    editedTime: "",
    id: "",
    nickname: "",
    password: "",
    phone: "",
    rolesView: [],
    status: "NORMAL",
  });
  const roles = ref<string[]>([]);
  const registerShow = ref(false);
  const getUserInfo = async () => {
    userInfo.value = await api.userForFrontController.getUserInfo();
    roles.value = userInfo.value.rolesView.map((item) => item.name);
    return userInfo.value;
  };
  return { userInfo, roles, registerShow, getUserInfo };
});
