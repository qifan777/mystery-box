import { createRouter, createWebHashHistory } from 'vue-router'

import { useHomeStore } from '@/stores/home-store'

const router = createRouter({
  history: createWebHashHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'layout-view',
      component: () => import('@/layout/layout-view.vue'),
      children: [
        { path: '/user', component: () => import('@/views/user/user-view.vue') },
        {
          path: '/role',
          name: 'role',
          component: () => import('@/views/role/role-view.vue')
        },
        {
          path: '/menu',
          name: 'menu',
          component: () => import('@/views/menu/menu-view.vue')
        },
        {
          path: '/dict',
          name: 'dict',
          component: () => import('@/views/dict/dict-view.vue')
        },
        {
          path: '/product',
          component: () => import('@/views/product/product-view.vue')
        },
        {
          path: '/product-details',
          component: () => import('@/views/product/product-details-view.vue'),
          props(to) {
            return { id: to.query.id }
          }
        },
        {
          path: '/product-category',
          component: () => import('@/views/product-category/product-category-view.vue')
        },
        {
          path: '/product-category-details',
          component: () => import('@/views/product-category/product-category-details-view.vue'),
          props(to) {
            return { id: to.query.id }
          }
        },
        {
          path: '/mystery-box',
          component: () => import('@/views/mystery-box/mystery-box-view.vue')
        },
        {
          path: '/mystery-box-details',
          component: () => import('@/views/mystery-box/mystery-box-details-view.vue'),
          props(to) {
            return { id: to.query.id }
          }
        },
        {
          path: '/slideshow',
          component: () => import('@/views/slideshow/slideshow-view.vue')
        },
        {
          path: '/slideshow-details',
          component: () => import('@/views/slideshow/slideshow-details-view.vue'),
          props(to) {
            return { id: to.query.id }
          }
        }
      ]
    },
    {
      path: '/login',
      name: 'login',
      component: () => import('@/views/login/login-view.vue')
    },
    {
      path: '/register',
      name: 'register',
      component: () => import('@/views/login/register-view.vue')
    },
    {
      path: '/rest-password',
      component: () => import('@/views/login/rest-password-view.vue')
    }
  ]
})
// 添加路由拦截，在进入路由之前需要校验是否有该菜单的权限
// eslint-disable-next-line no-sparse-arrays
const whiteList = ['/login', '/register', '/rest-password', '/']
router.beforeEach(async (to, from, next) => {
  const homeStore = useHomeStore()
  if (
    whiteList.includes(to.path) ||
    (await homeStore.getMenuList()).findIndex((menu) => menu.path === to.path) >= 0
  ) {
    next()
  } else {
    return next('/')
  }
})
export default router
