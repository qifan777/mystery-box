import { api } from '@/utils/api-instance'

export const mysteryBoxCategoryQueryOptions = async (keyword: string, id: string) => {
  return (
    await api.mysteryBoxCategoryForAdminController.query({ body: { query: { name: keyword, id } } })
  ).content
}
