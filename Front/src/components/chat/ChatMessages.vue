<template>
  <v-card
      flat
      class="fill-height greyish-element"
  >
    <v-toolbar
        class="greyish-element mt-n2"
        elevation="0"
    >
      <v-toolbar-title>
        <v-icon>
          mdi-forum
        </v-icon>
        {{ 'Room Topic' }}
      </v-toolbar-title>
      <v-spacer></v-spacer>
      <v-btn icon>
        <v-icon>
          mdi-cog
        </v-icon>
      </v-btn>
    </v-toolbar>
    <v-card-text class="overflow-y-auto" style="max-height: 71vh">
      <template v-for="(msg) in getMessages">
        <div
            :class="{ 'd-flex flex-row-reverse': itsMe(msg.userId)  }"
            :key="msg.message"
        >
          <v-menu offset-y>
            <template v-slot:activator="{ on }">
                <v-chip
                    :color="itsMe(msg.userId) ? 'primary' : ''"
                    dark
                    style="height:auto;white-space: normal;"
                    class="pa-4 mb-2"
                    v-on="on"
                >
                  {{ msg.message }}
                  <v-avatar
                      size="32"
                      class="avatar-bg-style mx-2"
                      color="white"
                  >
                    <v-img
                        :src="avatarURL(
                            getAvatarById(msg.userId),
                            getNicknameById(msg.userId)
                            )"
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
                              size="32px"
                          ></v-progress-circular>
                        </v-row>
                      </template>
                    </v-img>
                  </v-avatar>
                </v-chip>
            </template>
            <v-list>
              <v-list-item>
                <v-list-item-content>
                  <v-list-item-title>{{getNicknameById(msg.userId)}}</v-list-item-title>
                  <v-list-item-subtitle>Nickname</v-list-item-subtitle>
                </v-list-item-content>
              </v-list-item>
              <v-list-item>
                <v-list-item-content>
                  <v-list-item-title>{{formatedSentDate(msg.sentDate)}}</v-list-item-title>
                  <v-list-item-subtitle>Sent Date</v-list-item-subtitle>
                </v-list-item-content>
              </v-list-item>
            </v-list>
          </v-menu>
        </div>
      </template>
    </v-card-text>
  </v-card>
</template>

<script>

import {mapGetters} from "vuex";
import {avatarMixins} from "@/mixins/avatar-mixins";

export default {
  // TODO: LOS MENSAJES NUEVO QUE LLEGAN POR WS SE GUARDAN EN MESSAGES (EN EL FE)
  name: "ChatMessages",
  mixins: [avatarMixins],
  computed: {
    ...mapGetters('userStorageModule',
        ['getUserId']
    ),
    ...mapGetters('webSocketModule',
        ['getMessages']
    ),
  },
  methods: {
    itsMe(id){
      return this.getUserId === id;
    },
    getAvatarById(userId){
      const user = this.$store.getters["webSocketModule/getUserById"](userId);
      return user ? user.avatar : "Usuario no encontrado";
    },
    getNicknameById(userId){
      const user = this.$store.getters["webSocketModule/getUserById"](userId);
      return user ? user.nickname : "Usuario no encontrado";
    },
    formatedSentDate(sentDate){
      const dateTime = new Date(sentDate);
      return `${dateTime.toLocaleTimeString()} - ${dateTime.toLocaleDateString()}`;
    }
  },
  data: () => ({
  })
}
</script>

<style scoped>
</style>