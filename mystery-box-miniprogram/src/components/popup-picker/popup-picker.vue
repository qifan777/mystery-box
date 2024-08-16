<template>
  <div>
    <nut-input
      readonly
      @click="handleInputClick"
      :placeholder="placeholder"
      :model-value="pathText"
    ></nut-input>
    <nut-popup
      v-model:visible="visible"
      position="bottom"
      @close="visible = false"
    >
      <nut-picker
        :columns="columns"
        :model-value="pathIds"
        @confirm="handleConfirm"
        @cancel="visible = false"
      >
      </nut-picker>
    </nut-popup>
  </div>
</template>

<script setup lang="ts">
import { computed, onMounted, ref } from "vue";
type Column = { text: any; value: any; children?: Column[] };
const props = defineProps<{
  modelValue?: any;
  placeholder?: string;
  columns: Column[];
  disabled?: boolean;
}>();

const emit = defineEmits<{ "update:modelValue": [value: any] }>();
const visible = ref(false);
const handleInputClick = () => {
  if (!props.disabled) {
    visible.value = true;
  }
};
const handleConfirm = (val: { selectedValue: any[] }) => {
  emit("update:modelValue", val.selectedValue[val.selectedValue.length - 1]);
  console.log(val.selectedValue[val.selectedValue.length - 1]);
  visible.value = false;
};
onMounted(() => {
  if (!props.modelValue && props.columns.length > 0) {
    const value = findFirstColumn(props.columns);
    emit("update:modelValue", value);
  }
});
const findFirstColumn = (columns: Column[]) => {
  if (columns[0].children && columns[0].children.length > 0) {
    return findFirstColumn(columns[0].children);
  } else {
    return columns[0].value;
  }
};
const pathIds = computed(() => {
  return columnPath(props.modelValue, props.columns).map((c) => c.value);
});
const pathText = computed(() => {
  const paths = columnPath(props.modelValue, props.columns);
  if (paths.length > 0) {
    return paths[paths.length - 1].text;
  }
  return "";
});
const columnPath = (id: any, columns: Column[]): Column[] => {
  const column = columns.find((column) => column.value == id);
  if (column) {
    return [column];
  } else {
    for (const c of columns) {
      if (c.children) {
        const paths = columnPath(id, c.children);
        if (paths.length > 0) {
          return [c, ...paths];
        }
      }
    }
  }
  return [];
};
</script>

<style lang="scss"></style>
