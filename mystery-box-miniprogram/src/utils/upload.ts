import Taro from "@tarojs/taro";
export type UploadFile = {
  name: string;
  url: string;
  status: string;
  message: string;
  type: string;
};
export const uploadFile = async (filePath: string) => {
  return await new Promise<string>((resolve, reject) => {
    Taro.uploadFile({
      url: (process.env.TARO_APP_API ?? "") + "/oss/upload",
      header: {
        token: Taro.getStorageSync("token"),
      },
      name: "file",
      filePath,
      success: (res) => {
        resolve(JSON.parse(res.data).result);
      },
      fail: (result) => {
        console.log(result);
        reject(result);
      },
    });
  });
};
export const beforeXhrUpload = async (_: any, options: any) => {
  return await new Promise<string>((resolve, reject) => {
    Taro.compressImage({
      src: options.taroFilePath,
      compressedWidth: 1000,
      quality: 30,
      success(result) {
        //taroUploadFile  是 Taro.uploadFile ， 你也可以自定义设置其它函数
        const uploadTask = Taro.uploadFile({
          url: `${process.env.TARO_APP_API}/oss/upload`,
          filePath: result.tempFilePath,
          header: {
            "Content-Type": "multipart/form-data",
            token: Taro.getStorageSync("token"),
            ...options.headers,
          }, //
          formData: options.formData,
          name: options.name,
          success(response) {
            if (options.xhrState == response.statusCode) {
              resolve(JSON.parse(response.data).result.url);
              options.onSuccess?.(
                JSON.parse(response.data).result.url,
                options,
              );
            } else {
              reject();
              options.onFailure?.(response, options);
            }
          },
          fail(e) {
            reject();
            options.onFailure?.(e, options);
          },
        });
        options.onStart?.(options);
        uploadTask.progress((res) => {
          options.onProgress?.(res, options);
        });
      },
    });
  });
};

export const onSuccess = ({
  data,
  fileItem,
}: {
  data: string;
  options: any;
  fileItem: UploadFile;
}) => {
  fileItem.url = data;
};
