import { api } from '@/utils/api-instance'

export const vipLevelQueryOptions = async (keyword: string, id: string) => {
  return (await api.vipLevelForAdminController.query({ body: { query: { name: keyword, id } } }))
    .content
}
