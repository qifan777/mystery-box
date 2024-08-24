import { api } from "@/utils/api-instance";
import Taro from "@tarojs/taro";
export const handleTrackingDetails = (trackingNumber?: string) => {
  if (!trackingNumber) {
    return Taro.showToast({
      title: "暂无物流信息",
      icon: "none",
    });
  }
  Taro.navigateToMiniProgram({
    appId: "wx6885acbedba59c14",
    path: `pages/result/result?nu=${trackingNumber}&com=&querysource=third_xcx`,
  });
};
export const boxWeChatPay = (id: string) => {
  return new Promise<boolean>((resolve, reject) => {
    Taro.showLoading();
    api.mysteryBoxOrderForFrontController
      .prepayWechat({ id })
      .then((res) => {
        Taro.requestPayment({
          nonceStr: res.nonceStr,
          package: res.packageValue,
          paySign: res.paySign,
          timeStamp: res.timeStamp,
          signType: res.signType as "RSA",
          success: () => {
            Taro.showToast({
              title: "支付成功",
              icon: "success",
            });
            resolve(true);
            Taro.hideLoading();
          },
          fail: (res) => {
            console.log(res);
            reject(res);
            Taro.hideLoading();
          },
        });
      })
      .catch((err) => {
        reject(err);
      });
  });
};
export const boxOrderCancel = (id: string) => {
  return new Promise<boolean>((resolve, reject) => {
    Taro.showModal({
      title: "是否确认取消订单",
      success: (actionRes) => {
        if (actionRes.confirm) {
          Taro.showLoading();
          api.mysteryBoxOrderForFrontController
            .unpaidCancelForUser({ id })
            .then(() => {
              Taro.showToast({
                title: "取消成功",
                icon: "success",
              });
              resolve(true);
              Taro.hideLoading();
            })
            .catch((err) => {
              reject(err);
            });
        } else {
          reject();
        }
      },
    });
  });
};
