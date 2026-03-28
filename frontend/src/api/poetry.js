import axios from './axios'

export const poetryApi = {
  list: (params) => axios.get('/poetry/listData', { params }),
  detail: (id) => axios.get(`/poetry/detail/${id}`),
  tags: () => axios.get('/poetry/tags'),
  update: (id, data) => axios.put(`/poetry/update/${id}`, data)
}
