<template>
  <v-row
      class="d-flex justify-center align-center fill-height"
  >
    <v-card
        class="slight-opacity mb-16"
        elevation="1"
        outlined
        min-width="40vw"
    >
      <v-toolbar
          color="transparent"
          elevation="0"
          class="pa-2"
      >
        <v-icon class="mr-5">mdi-chat-plus</v-icon>
        <v-toolbar-title
            class="text-h5 text-cap"
        >
          Join room: {{room.roomName}} #{{room.roomId}}
        </v-toolbar-title>
      </v-toolbar>
      <v-row
          class="pa-10"
      >
        <v-col cols="9">
          <v-text-field
              v-model="joinReq.nickname"
              label="Nickname"
              filled
              dense
              :rules="[nicknameRules.minl,nicknameRules.maxl]"
          >
          </v-text-field>
        </v-col>
        <v-col
            class="d-flex justify-space-around"
            cols="3"
        >
          <v-avatar
              size="56"
              class="avatar-bg-style"
          >
            <v-icon
                v-if="joinReq.nickname.length < 6"
                x-large
            >
              mdi-help-circle-outline
            </v-icon>
            <v-img
                v-else
                :src="pickedAvatar()"
            >
              <template v-slot:placeholder>
                <v-row
                    class="fill-height ma-0"
                    align="center"
                    justify="center"
                >
                  <v-progress-circular
                      indeterminate
                      color="grey lighten-5"
                  ></v-progress-circular>
                </v-row>
              </template>
            </v-img>
          </v-avatar>
          <v-avatar
              size="56"
              class="avatar-bg-style"
          >
            <v-btn
                color="grey-darken-4"
                large
                elevation="0"
                icon
                @click="randomAvatar"
            >
              <v-icon
              >
                mdi-dice-5-outline
              </v-icon>
            </v-btn>
          </v-avatar>
        </v-col>
        <v-col
            cols="12"
            class="d-flex justify-center"
        >
          <v-btn
              class="mt-n5"
              elevation="2"
              x-large
              style="border: rgba(128,128,128,.3) 1px solid !important; background-color: rgba(255,255,255,.3) !important;"
              @click="joinRoom()"
          >
            <v-icon class="mr-1">
              mdi-plus
            </v-icon>
            Join room
          </v-btn>
        </v-col>
      </v-row>
    </v-card>
  </v-row>
</template>
<script>
import {mapGetters} from "vuex";
import {avatarMixins} from "@/mixins/avatar-mixins";
import {EventBus} from "@/main";

export default  {
  name: 'ChatRoomJoin',
  mixins: [avatarMixins],
  data: () => ({
    room: {
      id: 0
    },
    joinReq: {
      nickname: '',
      avatar: null
    },
    gettersVuex: {
      isConnected: false,
      chatId: ''
    },
    nicknameRules: {
      minl: v => v.length >= 6 || 'Min 6 characters',
      maxl: v => v.length <= 14 || 'Max 6 characters'
    }
  }),
  created() {
    this.$store.dispatch('appSettingsModule/loadAppSettings');
    this.getRoomInfo();
  },
  methods: {
    getRoomInfo(){
      this.axios.get(`/chat/${this.$route.params.roomId}/join`)
          .then(res => {
            if (res.data.available) {
              this.room = res.data;
            } else {
              EventBus.$emit('showAlert', {
                color: "warning",
                type: "warning",
                msg: `Room #${this.$route.params.roomId} is full. Try again later`
              });
              this.$router.push(`/`);
            }
          })
          .catch(err => {
            EventBus.$emit('showAlert', {
              color: "error",
              type: "error",
              msg: err.response.data.message
            });
            setTimeout(() => this.$router.push("/"),1000);
          })
    },
    joinRoom(){
      this.axios
          .post(`/chat/${this.$route.params.roomId}/join`,this.joinReq)
          .then((res) => {
            EventBus.$emit('showAlert', {
              color: "success",
              type: "success",
              msg: `Welcome to room ${res.data.roomTopic}`
            });
            this.$store.dispatch('userStorageModule/disconnectUser');
            this.$store.dispatch(
                'userStorageModule/setUserStorageAction',
                res.data
            );
            this.$router.push(`/chat/${this.$route.params.roomId}`)
          })
          .catch( err => {
            EventBus.$emit('showAlert', {
              color: "error",
              type: "error",
              msg: err.response.data.message
            });
          });
    },
    pickedAvatar(){
      if (!this.joinReq.avatar) this.randomAvatar();
      return this.avatarURL(
          this.joinReq.avatar,
          this.joinReq.nickname
      );
    },
    randomAvatar(){
      this.joinReq.avatar = this.randomizeStyle(
          this.getAvailableAvatars,
          this.joinReq.avatar
      )
    },
  },
  computed: {
    ...mapGetters('appSettingsModule',
        ['getAvailableAvatars']
    )
  }
}
</script>