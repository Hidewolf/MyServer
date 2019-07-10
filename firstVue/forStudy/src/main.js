// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import store from './store'

//参数序列化
var qs = require('qs')

/* 引用axios，并设置基础URL为后端服务api地址*/
var axios = require('axios')
axios.defaults.baseURL = 'http://localhost:8080/wolfServer'

//拦截器序列化请求的参数
axios.interceptors.request.use(function(config) {
  config.data = qs.stringify(config.data)
  return config
})
//拦截器判断登录状态和处理服务端的重定向要求
axios.interceptors.response.use(function(response) {
  if (response.data.resultCode == '250') {
    router.replace({
      path: '/login',
      query: { redirect: router.currentRoute.fullPath }
    })
  } else if (response.data.resultCode == '233') {
    router.replace({
      path: response.data.res == null ? '' : response.data.res,
      query: { redirect: router.currentRoute.fullPath }
    })
  }
  return response
})

// 将API方法绑定到全局
Vue.prototype.$axios = axios

Vue.config.productionTip = false

//校验登陆状态
router.beforeEach((to, from, next) => {
  if (to.meta.requireAuth) {
    if (store.state.userInfo.userName) {
      next()
    } else {
      next({
        path: '/login',
        query: {redirect: to.fullPath}
      })
    }
  } else {
    next()
  }
}
)

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  store,
  components: { App },
  template: '<App/>'
})
