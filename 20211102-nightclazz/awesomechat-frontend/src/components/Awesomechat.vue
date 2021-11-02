<template>
  <div class="hello">
    <h1>Awesome Chat</h1>
    <input v-model="newMessageBody" placeholder="message...">
    <button @click="postMessage">Post</button>
    <div id="message-list">
      <p v-for="message in messages" :key="message.id">
        {{ message.body }}
      </p>
    </div>
  </div>
</template>

<script>
export default {
  name: 'Awesomechat',
  props: {
    msg: String
  },
  data() {
    return {
      messages: [],
      newMessageBody: ''
    }
  },
  methods: {
    async fetchMessages() {
      // TODO-08 : Add missing URL
      const response = await fetch('BACKEND_URL')
      this.messages = await response.json()
    },
    async postMessage() {
      await fetch('BACKEND_URL', {
        method: 'POST',
        body: this.newMessageBody
      })
      await this.fetchMessages()
      this.newMessageBody = ''
    }
  },
  async mounted() {
    await this.fetchMessages()
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
h3 {
  margin: 40px 0 0;
}

ul {
  padding: 0;
}

li {
  margin: 0 10px;
}

a {
  color: #42b983;
}
</style>
