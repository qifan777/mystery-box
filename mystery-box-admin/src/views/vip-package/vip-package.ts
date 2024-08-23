import { api } from '@/utils/api-instance'

export const vipPackageQueryOptions = async (keyword: string, id: string) => {
  return (await api.vipPackageForAdminController.query({ body: { query: { name: keyword, id } } }))
    .content
}
