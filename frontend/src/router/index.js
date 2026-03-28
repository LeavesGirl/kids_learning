import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: HomeView
    },
    {
      path: '/poetry',
      name: 'poetry',
      component: () => import('../views/poetry/list.vue')
    },
    {
      path: '/poetry/detail/:id',
      name: 'poetryDetail',
      component: () => import('../views/poetry/detail.vue')
    },
    {
      path: '/character',
      name: 'character',
      component: () => import('../views/character/list.vue')
    },
    {
      path: '/character/detail/:id',
      name: 'characterDetail',
      component: () => import('../views/character/detail.vue')
    },
    {
      path: '/english',
      name: 'english',
      component: () => import('../views/english/list.vue')
    },
    {
      path: '/english/detail/:id',
      name: 'englishDetail',
      component: () => import('../views/english/detail.vue')
    },
    {
      path: '/pinyin',
      name: 'pinyin',
      component: () => import('../views/pinyin/list.vue')
    },
    {
      path: '/pinyin/detail/:id',
      name: 'pinyinDetail',
      component: () => import('../views/pinyin/detail.vue')
    },
    {
      path: '/idiom',
      name: 'idiom',
      component: () => import('../views/idiom/list.vue')
    },
    {
      path: '/idiom/detail/:id',
      name: 'idiomDetail',
      component: () => import('../views/idiom/detail.vue')
    },
    {
      path: '/drawing',
      name: 'drawing',
      component: () => import('../views/drawing/list.vue')
    },
    {
      path: '/drawing/detail/:id',
      name: 'drawingDetail',
      component: () => import('../views/drawing/detail.vue')
    },
    {
      path: '/math',
      name: 'math',
      component: () => import('../views/math/list.vue')
    },
    {
      path: '/math/detail/:id',
      name: 'mathDetail',
      component: () => import('../views/math/detail.vue')
    }
  ]
})

export default router