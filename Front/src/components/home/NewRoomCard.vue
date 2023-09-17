<template>
  <v-row
      class="d-flex justify-center align-center fill-height"
  >
    <template>
      <v-card
          class="slight-opacity mb-16"
          elevation="1"
          outlined
          max-width="800px"
      >
        <v-toolbar
            color="transparent"
            elevation="0"
            class="pa-2"
        >
          <v-icon class="mr-5">mdi-tune-variant</v-icon>
          <v-toolbar-title
              class="text-h5 text-cap"
          >
            Create a new chat room
          </v-toolbar-title>
        </v-toolbar>
        <v-row
            class="pa-10"
        >
          <v-col :cols="12">
            <v-text-field
                v-model="createRoomReq.topic"
                label="Room name/topic"
                filled
                dense
            >
            </v-text-field>
          </v-col>
          <v-col :cols="mobile.mobile ? 6 : 4">
            <v-select
                v-model="createRoomReq.roomCapacity"
                :items="getRoomCapacitySelect"
                label="Room capacity"
                filled
            ></v-select>
          </v-col>
          <v-col :cols="mobile.mobile ? 6 : 5">
            <v-text-field
                v-model="createRoomReq.nickname"
                label="Nickname"
                filled
                dense
                :rules="[nicknameRules.minl,nicknameRules.maxl]"
            >
            </v-text-field>
          </v-col>
          <v-col
              class="d-flex justify-space-around"
              :class="mobile.mobile ? 'mt-n6' : ''"
              :cols="mobile.mobile ? 12 : 3"
          >
            <v-avatar
                size="56"
                class="avatar-bg-style"
            >
              <v-icon
                  v-if="createRoomReq.nickname.length < 6"
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
                      class="fill-height"
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
<!--      ########## TODO: V2 ##########
º         <v-col :cols="mobile.mobile ? 6 : 4" class="d-flex justify-center">
            <div class="mt-n1">
              <v-checkbox
                  label="Password protected"
                  filled
              ></v-checkbox>
            </div>
          </v-col>
          <v-col :cols="mobile.mobile ? 6 : 4" class="d-flex justify-center">
            <div class="mt-n1">
              <v-checkbox
                  label="Show in explorer"
                  filled
              ></v-checkbox>
            </div>
          </v-col>-->
          <v-col class="d-flex justify-center">
            <v-btn
                :class="mobile.mobile ? '' : 'mt-n5'"
                elevation="2"
                x-large
                style="border: rgba(128,128,128,.3) 1px solid !important; background-color: rgba(255,255,255,.3) !important;"
                @click="createNewRoom()"
            >
              <v-icon class="mr-1">
                mdi-plus
              </v-icon>
              Create room
            </v-btn>
          </v-col>
        </v-row>
      </v-card>
    </template>
  </v-row>
</template>

<script>
import {avatarMixins} from "@/mixins/avatar-mixins.js";
import {mapGetters} from "vuex";
import {EventBus} from "@/main";
import {mobileMixins} from "@/mixins/mobile-mixins";
export default {
  name: "NewRoomCard",
  mixins: [avatarMixins, mobileMixins],
  data: () => ({
    mobile: {
      mobile: false
    },
    createRoomReq: {
      topic: '',
      nickname: '',
      avatarStyle: null,
      roomCapacity: 2
    },
    nicknameRules: {
      minl: v => v.length >= 6 || 'Min 6 characters',
      maxl: v => v.length <= 14 || 'Max 6 characters'
    }
  }),
  created() {
    this.$store.dispatch('appSettingsModule/loadAppSettings');
    window.addEventListener('resize', this.onResize, { passive: true });
    this.onResize();
  },
  computed:{
    ...mapGetters('appSettingsModule',
        ['getRoomCapacitySelect']
    ),
    ...mapGetters('appSettingsModule',
        ['getAvailableAvatars']
    )
  },
  methods: {
    onResize(){
      this.mobile.mobile = this.getWindowWidth() < 600;
    },
    pickedAvatar(){
      if (!this.createRoomReq.avatarStyle) this.randomAvatar();
      return this.avatarURL(
          this.createRoomReq.avatarStyle,
          this.createRoomReq.nickname
      );
    },
    randomAvatar(){
      this.createRoomReq.avatarStyle = this.randomizeStyle(
          this.getAvailableAvatars,
          this.createRoomReq.avatarStyle
      )
    },
    createNewRoom(){
      this.axios
          .post(`/chat/create`,this.createRoomReq)
          .then((res) => {
            EventBus.$emit('showAlert', {
              color: "success",
              type: "success",
              msg: `Room ${res.data.roomTopic} successfully created`
            });
            this.$store.dispatch('userStorageModule/disconnectUser');
            this.$store.dispatch(
                'userStorageModule/setUserStorageAction',
                res.data
            );
            this.$router.push(`/chat/${res.data.chatId}`)
          });
    }
  }
}
</script>

<style scoped>
>>> .v-input__slot::before {
  border-style: none !important;
}
</style>