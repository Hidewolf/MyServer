<template>
  <div>
    <div>消息发送：
      <a-input placeholder v-model="msg" v-on:keyup.enter="send"></a-input>
    </div>
    <div v-for="item in msgList">{{item}}</div>
  </div>

</template>

<script>
    export default {
        name: "socketTest",
        data() {
            return {msg: '', socket: {}, msgList: []}
        },
        mounted() {
            this.initSocket();
        },
        methods: {
            initSocket() {
                // 实例化socket
                this.socket = new WebSocket('ws://192.168.7.194:8080/wolfServer/webSocketTest/1')
                // 监听socket连接
                this.socket.onopen = this.open
                // 监听socket错误信息
                this.socket.onerror = this.error
                // 监听socket消息
                this.socket.onmessage = this.getMessage
            },
            open: function () {
                console.log("socket连接成功")
            },
            error: function () {
                console.log("连接错误")
            },
            getMessage: function (msg) {
                this.msgList.push(msg.data)
                console.log(msg)
            },
            send: function () {
                this.socket.send(this.msg)
            },
            close: function () {
                console.log("socket已经关闭")
            }
        },
        destroyed() {
            // 销毁监听
            this.socket.onclose = this.close
        }
    }
</script>

<style scoped>

</style>
