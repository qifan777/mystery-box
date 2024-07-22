import { api } from '@/utils/api-instance'

export const productCategoryQueryOptions = async (keyword: string, id: string) => {
  return (
    await api.productCategoryForAdminController.query({ body: { query: { name: keyword, id } } })
  ).content
}
