import { type Ref, ref } from 'vue'

export const useQueryHelper = <T>(initQuery: T) => {
  const query = ref({ ...initQuery }) as Ref<T>
  const restQuery = () => {
    console.log('重置查询条件')
    query.value = { ...initQuery }
  }
  return { query, restQuery }
}
