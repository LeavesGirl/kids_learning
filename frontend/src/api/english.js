import axios from './axios'

export const englishApi = {
  list: (params) => axios.get('/english/listData', { params }),
  detail: (id) => axios.get(`/english/detail/${id}`),
  update: (id, data) => axios.put(`/english/update/${id}`, data)
}
