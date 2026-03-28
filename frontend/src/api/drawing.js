import axios from './axios'

export const drawingApi = {
  list: (params) => axios.get('/drawing/listData', { params }),
  detail: (id) => axios.get(`/drawing/detail/${id}`)
}