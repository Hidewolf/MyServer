import Vue from 'vue'
import Vuex from 'vuex'

//使用Vuex
Vue.use(Vuex)

//创建仓库实例
const store = new Vuex.Store({
  state: {
    userInfo: {}
  },
  getters: {
    //校验是否登陆
    checkIsLog(state) {
      if (state.userInfo.userName != null && state.userInfo.userName != '') {
        return true
      } else {
        //从session中确认是否登陆以解决刷新后丢失登录状态
        state.userInfo =
          sessionStorage.getItem('userInfo') == null
            ? {}
            : JSON.parse(sessionStorage.getItem('userInfo'))
        if (state.userInfo.userName != null && state.userInfo.userName != '') {
          return true
        } else {
          return false
        }
      }
    }
  },
  mutations: {
    //登陆
    addUser(state, userInfo) {
      state.userInfo = userInfo
    },
    //清除登录状态
    removeUser(state) {
      state.userInfo = {}
    }
  },
  actions: {
    //登陆
    logIn(context, userInfo) {
      context.commit('addUser')
      sessionStorage.setItem('userInfo', JSON.stringify(userInfo))
    },
    //清除登录状态
    clear(context) {
      context.commit('removeUser')
      sessionStorage.removeItem('userInfo')
    }
  }
})

export default store
