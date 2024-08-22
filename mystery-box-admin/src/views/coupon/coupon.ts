import { api } from '@/utils/api-instance'

export const couponQueryOptions = async (keyword: string, id: string) => {
  return (await api.couponForAdminController.query({ body: { query: { name: keyword, id } } }))
    .content
}
