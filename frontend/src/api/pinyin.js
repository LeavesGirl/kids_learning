import axios from './axios'

export const pinyinApi = {
  list: (params) => axios.get('/pinyin/listData', { params }),
  detail: (id) => axios.get(`/pinyin/detail/${id}`),
  update: (id, data) => axios.put(`/pinyin/update/${id}`, data)
}
