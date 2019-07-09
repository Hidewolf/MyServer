<template>
  <div>
    <head-part>
    </head-part>
    <router-view />
    <foot-part>
    </foot-part>

    <div><button v-on:click="test">测试</button></div>
  </div>
</template>

<script>
import headPart from '@/components/head.vue'
import footPart from '@/components/foot.vue'

export default {
  name: 'App1',
  components: { headPart, footPart },
  data () {
    return {
    }
  },
  methods: {
    test: function () {
      this.$axios.post('/privateCloudDriver/getFileList').then(successResponse => {
      })
    }
  }
}
</script>

<style>
#app {
  font-family: "Avenir", Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
</style>
