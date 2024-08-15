import { defineConfig, type UserConfigExport } from "@tarojs/cli";
import TsconfigPathsPlugin from "tsconfig-paths-webpack-plugin";
import devConfig from "./dev";
import prodConfig from "./prod";
import ComponentsPlugin from "unplugin-vue-components/webpack";
import NutUIResolver from "@nutui/auto-import-resolver";
import path from "path";
// https://taro-docs.jd.com/docs/next/config#defineconfig-辅助函数
export default defineConfig<"webpack5">(async (merge, { command, mode }) => {
  const baseConfig: UserConfigExport<"webpack5"> = {
    projectName: "mystery-box-miniprogram",
    date: "2024-8-15",
    // 开启 HTML 插件
    plugins: ["@tarojs/plugin-html"],
    designWidth(input) {
      // 配置 NutUI 375 尺寸
      if (input?.file?.replace(/\\+/g, "/").indexOf("@nutui") > -1) {
        return 375;
      }
      // 全局使用 Taro 默认的 750 尺寸
      return 750;
    },
    deviceRatio: {
      640: 2.34 / 2,
      750: 1,
      828: 1.81 / 2,
      375: 2 / 1,
    },
    alias: {
      "@/components": path.resolve(__dirname, "..", "src/components"),
      "@/utils": path.resolve(__dirname, "..", "src/utils"),
      "@/typings": path.resolve(__dirname, "..", "src/typings"),
      "@/apis": path.resolve(__dirname, "..", "src/apis"),
      "@/stores": path.resolve(__dirname, "..", "src/stores"),
      "@/assets": path.resolve(__dirname, "..", "src/assets"),
      "@/pages": path.resolve(__dirname, "..", "src/pages"),
    },
    // sass: {
    //   resource: [path.resolve(__dirname, "..", "src/app.scss")],
    //   data: `@import "@nutui/nutui-taro/dist/styles/variables.scss";`,
    // },
    sourceRoot: "src",
    outputRoot: "dist",
    defineConstants: {},
    copy: {
      patterns: [],
      options: {},
    },
    framework: "vue3",
    compiler: "webpack5",
    cache: {
      enable: false, // Webpack 持久化缓存配置，建议开启。默认配置请参考：https://docs.taro.zone/docs/config-detail#cache
    },
    mini: {
      postcss: {
        pxtransform: {
          enable: true,
          config: {},
        },
        cssModules: {
          enable: false, // 默认为 false，如需使用 css modules 功能，则设为 true
          config: {
            namingPattern: "module", // 转换模式，取值为 global/module
            generateScopedName: "[name]__[local]___[hash:base64:5]",
          },
        },
      },
      webpackChain(chain) {
        chain.plugin("unplugin-vue-components").use(
          ComponentsPlugin({
            resolvers: [NutUIResolver({ taro: true })],
          }),
        );
        chain.resolve.plugin("tsconfig-paths").use(TsconfigPathsPlugin);
      },
    },
    h5: {
      publicPath: "/",
      staticDirectory: "static",
      output: {
        filename: "js/[name].[hash:8].js",
        chunkFilename: "js/[name].[chunkhash:8].js",
      },
      miniCssExtractPluginOption: {
        ignoreOrder: true,
        filename: "css/[name].[hash].css",
        chunkFilename: "css/[name].[chunkhash].css",
      },
      postcss: {
        autoprefixer: {
          enable: true,
          config: {},
        },
        cssModules: {
          enable: false, // 默认为 false，如需使用 css modules 功能，则设为 true
          config: {
            namingPattern: "module", // 转换模式，取值为 global/module
            generateScopedName: "[name]__[local]___[hash:base64:5]",
          },
        },
      },
      webpackChain(chain) {
        chain.plugin("unplugin-vue-components").use(
          ComponentsPlugin({
            resolvers: [NutUIResolver({ taro: true })],
          }),
        );
        chain.resolve.plugin("tsconfig-paths").use(TsconfigPathsPlugin);
      },
    },
    rn: {
      appName: "taroDemo",
      postcss: {
        cssModules: {
          enable: false, // 默认为 false，如需使用 css modules 功能，则设为 true
        },
      },
    },
  };
  if (process.env.NODE_ENV === "development") {
    // 本地开发构建配置（不混淆压缩）
    return merge({}, baseConfig, devConfig);
  }
  // 生产构建配置（默认开启压缩混淆等）
  return merge({}, baseConfig, prodConfig);
});
