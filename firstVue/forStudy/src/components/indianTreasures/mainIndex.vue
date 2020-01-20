<template>
  <div style="padding: 3%">
    <a-row type="flex" justify="start">
      <a-col :span="3">
        <a-button @click="getRoomList">查询</a-button>
      </a-col>
      <a-col :span="3">
        <a-button @click="creatRoom">创建房间</a-button>
      </a-col>
    </a-row>
    <a-row>
      <a-col v-for="room in roomList" :span="8" style="padding: 15px" :key="room.roomNo">
        <a-card :title="room.hostName+'的房间'" style="">
          <p>人数：{{room.playerNum}}/{{room.maxPlayerNum}}</p>
        </a-card>
      </a-col>
    </a-row>
  </div>
</template>

<script>
    export default {
        name: "mainIndex",
        data() {
            return {url: '/indianTreasures/rooms', roomList: []}
        },
        mounted: function () {
            this.getRoomList()
        },
        methods: {
            getRoomList: function () {
                this.$axios.post('/indianTreasures/rooms').then(res => {
                    this.roomList = res.data.res;
                    console.log(res)
                })
            },
            creatRoom: function () {
                this.$axios.post('/indianTreasures/createRoom').then(res => {
                    console.log(res)
                    this.joinRoom(res.data.res.roomNo)
                })
            },
            joinRoom: function (roomNo) {

            }
        }
    }
</script>

<style scoped>

</style>
