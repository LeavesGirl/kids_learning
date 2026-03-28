import axios from './axios'

export const idiomApi = {
  list: (params) => axios.get('/idiom/listData', { params }),
  detail: (id) => axios.get(`/idiom/detail/${id}`),
  update: (id, data) => axios.put(`/idiom/update/${id}`, data)
}
