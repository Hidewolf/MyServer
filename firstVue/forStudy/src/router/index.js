import Vue from 'vue'
import Router from 'vue-router'
import homePage from '@/components/common_assets/home'
import headAssets from '@/components/head'
import fileList from '@/components/cloudDriver/cloudDriverView'
import socketTest from '@/components/practiseForNewTec/socketTest'
import indianTreasures from '@/components/indianTreasures/mainIndex'

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
    },
    {
      path: '/socketTest',
      name: 'socketTest',
      component: socketTest,
      meta: { requireAuth: true }
    },
    {
      path: '/indianTreasures',
      name: 'indianTreasures',
      component: indianTreasures,
      meta: { requireAuth: true }
    }
  ]
})
