<template>
  <v-container class="py-0 fill-height" :fluid="mobile.expandContainer">
        <v-row lass="slight-opacity">
          <v-col cols="2" v-if="mobile.showUserColumn">
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
import {mobileMixins} from "@/mixins/mobile-mixins";

export default {
  mixins: [mobileMixins],
  name: 'ChatRoomView',
  components: {ChatMessages, ChatInput, ChatUsers},
  data: () => ({
    mobile: {
      showUserColumn: true,
      expandContainer: true
    },
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
    window.addEventListener('resize', this.onResize, { passive: true });
    this.onResize();
  },
  methods: {
    setRoomAndUserData(){
      this.room.id = this.$route.params.roomId;
      this.user.id = this.$store.getters['userStorageModule/getUserId'];
      this.user.nickname = this.$store.getters['userStorageModule/getUserNickname'];
      this.user.accessToken = this.$store.getters['userStorageModule/getAccessToken'];
    },
    onResize(){
      this.mobile.showUserColumn = this.getWindowWidth() > 1400;
      this.mobile.expandContainer = this.getWindowWidth() < 1880;
    }
  }
}
</script>