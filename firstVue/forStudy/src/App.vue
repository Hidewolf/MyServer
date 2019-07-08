<template>
  <div>
    <head-part>
    </head-part>
    <main-part>
    </main-part>
    <foot-part>
    </foot-part>
  </div>
</template>

<script>
import headPart from '@/components/head.vue'
import footPart from '@/components/foot.vue'
import mainPart from '@/components/logIn.vue'

export default {
  name: 'App1',
  components: { headPart, footPart, mainPart },
  data () {
    return {
    }
  }
}
</script>

<style>
#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
</style>
