import axios from './axios'

export const mathApi = {
  list: (params) => axios.get('/math/listData', { params }),
  detail: (id) => axios.get(`/math/detail/${id}`),
  generate: (data) => axios.post('/math/generate', data)
}