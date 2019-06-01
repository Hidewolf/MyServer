<template>
  <div id="app">
    <!-- <img src="./assets/logo.png"> -->
      <span>玩家数量：</span><input v-model="playerNumber" id="playerNumber"/>
      <span>每名玩家角色数：</span><input id="chessPerPlayer" :value="chessPerPlayer" />
    <router-view/>
  </div>
</template>

<script>
export default {
  name: 'App1',
  data () {
    return {
      playerNumber: 4,
      chessPerPlayer: 3
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
