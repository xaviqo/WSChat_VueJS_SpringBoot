<template>
    <div id="chat-msg-input" style="height: 6vh" class="greyish-element">
        <v-text-field
                color="gray"
                v-model="inputMessage"
                append-outer-icon="mdi-send"
                @click:append-outer="sendMessage(inputMessage)"
                @keyup.enter="sendMessage(inputMessage)"
                outlined
        ></v-text-field>
    </div>
</template>

<script>
export default {
    name: "ChatInput",
    data: () => ({
        inputMessage: ''
    }),
    methods: {
        sendMessage(msg){
            this.$store.dispatch('webSocketModule/sendMessage',msg);
            this.inputMessage = '';
        }
    }
}
</script>

<style scoped>
</style>