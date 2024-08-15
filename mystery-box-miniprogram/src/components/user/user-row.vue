<template>
  <div class="row">
    <div class="avatar">
      <user-avatar :src="avatar" :width="width"></user-avatar>
    </div>
    <div class="info">
      <div class="info-top">
        <div class="name">{{ name ?? "默认用户" }}</div>
        <div class="label">{{ label }}</div>
      </div>
      <div class="time">{{ time }}</div>
    </div>
    <div class="rear">
      <slot></slot>
    </div>
  </div>
</template>

<script lang="ts" setup>
withDefaults(
  defineProps<{
    avatar?: string;
    width?: string;
    name?: string;
    label: string;
    time: string;
  }>(),
  { avatar: "", width: "85rpx", label: "", time: "" },
);
</script>

<style lang="scss">
.row {
  display: flex;
  align-items: center;
  background-color: white;

  .avatar {
    image {
      border-radius: 15px;
    }
  }

  .info {
    display: flex;
    flex-direction: column;
    margin-left: 20px;

    .info-top {
      display: flex;
      align-items: center;

      .name {
        flex-shrink: 0;
        margin-right: 20px;
        font-size: 28px;
        color: rgba(black, 0.9);
      }

      .label {
        font-size: 24px;
        color: rgba(black, 0.7);
      }
    }

    .time {
      margin-top: 15px;
      font-size: 24px;
      color: rgba(black, 0.7);
    }
  }

  .rear {
    flex: 1;
    display: flex;
    justify-content: flex-end;
  }
}
</style>
