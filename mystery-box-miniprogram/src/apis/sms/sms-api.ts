import requestWithToken from "@/utils/request";

export const sendSMS = (phone: string) => {
  return requestWithToken<boolean>("/sms/send?phone=" + phone, "POST", null);
};
