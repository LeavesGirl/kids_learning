import axios from './axios'

export const characterApi = {
  list: (params) => axios.get('/character/listData', { params }),
  detail: (id) => axios.get(`/character/detail/${id}`),
  update: (id, data) => axios.put(`/character/update/${id}`, data)
}
