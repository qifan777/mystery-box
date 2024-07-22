import { api } from '@/utils/api-instance'

export const productQueryOptions = async (keyword: string, id: string) => {
  return (await api.productForAdminController.query({ body: { query: { name: keyword, id } } }))
    .content
}
