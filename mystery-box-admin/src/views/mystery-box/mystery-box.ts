import { api } from '@/utils/api-instance'

export const mysteryBoxQueryOptions = async (keyword: string, id: string) => {
  return (await api.mysteryBoxForAdminController.query({ body: { query: { name: keyword, id } } }))
    .content
}
