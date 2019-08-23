import Vue from 'vue'
import Router from 'vue-router'
import homePage from '@/components/common_assets/home'
import headAssets from '@/components/head'
import fileList from '@/components/cloudDriver/cloudDriverView'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/home',
      name: 'homePage',
      component: homePage,
      meta: { requireAuth: false }
    },
    {
      path: '/',
      name: 'test',
      component: headAssets,
      meta: { requireAuth: true }
    },
    {
      path: '/fileList',
      name: 'fileList',
      component: fileList,
      meta: { requireAuth: true }
    }
  ]
})
