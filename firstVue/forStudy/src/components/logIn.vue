<template>
  <div>
    <div>
      <span>用户名：</span><span><input
          id='userName'
          type='text'
          v-model="logInfo.userName"
          placeholder="请输入用户名"
        /></span></div>
    <div>
      <span>密码：</span><span><input
          id='psw'
          type='password'
          v-model="logInfo.psw"
          placeholder="请输入密码"
        /></span></div>
    <div><button v-on:click="logIn">登陆</button></div>
    <div><button v-on:click="test">测试</button></div>
  </div>
</template>
<script>
export default {
  name: 'logAssets',
  data () {
    return { logInfo: { userName: '', psw: '' } }
  },
  methods: {
    logIn: function () {
      this.$axios.post('/login/log', this.logInfo).then(successResponse => {
        if (successResponse.data.resultCode == '200') {
          this.$store.commit("logIn", successResponse.data.res);
          var path = this.$route.query.redirect
          this.$router.replace({ path: path === '/' || path === undefined ? '/' : path })
        } else {

        }
      })
    },
    test: function () {
      this.$axios.post('/privateCloudDriver/getFileList').then(successResponse => {
      })
    }
  }
}
</script>
