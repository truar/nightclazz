<template>
  <img alt="Vue logo" src="./assets/logo.png">
  <Awesomechat />
</template>

<script>
import Awesomechat from '@/components/Awesomechat'

export default {
  name: 'App',
  components: {
    Awesomechat
  }
}
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
</style>
