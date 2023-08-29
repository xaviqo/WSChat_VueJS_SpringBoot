<template>
  <v-container class="py-0 fill-height">
        <v-row lass="slight-opacity">
          <v-col cols="2">
            <ChatUsers :room-id="gettersVuex.roomId"></ChatUsers>
          </v-col>
          <v-col>
            <v-sheet
                min-height="90vh"
                rounded="lg"
            >
              <v-col cols="12" style="height: 82vh;">
                <ChatMessages></ChatMessages>
              </v-col>
              <v-col cols="12" style="height: 8vh;">
                <ChatInput></ChatInput>
              </v-col>
            </v-sheet>
          </v-col>
        </v-row>
  </v-container>
</template>

<script>
import ChatUsers from "@/components/chat/ChatUsers.vue";
import ChatInput from "@/components/chat/ChatInput.vue";
import ChatMessages from "@/components/chat/ChatMessages.vue";

export default {
  name: 'ChatRoomView',
  components: {ChatMessages, ChatInput, ChatUsers},
  data: () => ({
    room: {
      id: 0
    },
    user: {
      id: 0,
      nickname: '',
      accessToken: ''
    },
    gettersVuex: {
      isConnected: false,
      chatId: ''
    }
  }),
  created() {
    this.gettersVuex.isConnected = this.$store.getters['userStorageModule/isUserConnected'];
    this.gettersVuex.chatId = this.$store.getters['userStorageModule/getChatId'];
    this.setRoomAndUserData();
    this.$store.dispatch('webSocketModule/connectWS', {
      senderId: this.user.id,
      token: this.user.accessToken,
      senderName: this.user.nickname,
      roomId: this.room.id
    });
  },
  methods: {
    setRoomAndUserData(){
      this.room.id = this.$route.params.roomId;
      this.user.id = this.$store.getters['userStorageModule/getUserId'];
      this.user.nickname = this.$store.getters['userStorageModule/getUserNickname'];
      this.user.accessToken = this.$store.getters['userStorageModule/getAccessToken'];
    }
  }
}
</script>