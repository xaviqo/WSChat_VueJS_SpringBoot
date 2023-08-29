<template>
  <v-sheet rounded="lg">
    <v-toolbar
        class="greyish-element"
        elevation="0"
    >
      <v-toolbar-title>
        <v-icon>mdi-account</v-icon>
        <span>
            Users
        </span>
      </v-toolbar-title>
      <v-spacer></v-spacer>
      <v-btn icon>
        <v-icon>
          mdi-plus-thick
        </v-icon>
      </v-btn>
    </v-toolbar>
    <v-list color="transparent">
      <v-list-item
          v-for="user in onlineUsers"
          :key="user.id"
          link
      >
        <v-list-item-content
        >
          <v-list-item-title>
            <v-badge
                class="mt-1"
                bordered
                bottom
                color="green accent-4"
                dot
                offset-x="10"
                offset-y="10"
            >
              <v-avatar
                  size="32"
                  class="avatar-bg-style"
              >
                <v-img
                    :src="avatarURL(user.avatar,user.nickname)"
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
            </v-badge>
            <span class="ml-2">{{ user.nickname }}</span>
          </v-list-item-title>
        </v-list-item-content>
      </v-list-item>

      <v-divider class="my-2"
         v-if="offlineUsers.length > 0"
      />

      <v-list-item
          v-for="user in offlineUsers"
          :key="user.id"
          link
      >
        <v-list-item-content>
          <v-list-item-title>
            <v-badge
                class="mt-1"
                bordered
                bottom
                color="red accent-4"
                dot
                offset-x="10"
                offset-y="10"
            >
              <v-avatar
                  size="32"
                  class="avatar-bg-style"
              >
                <v-img
                    :src="avatarURL(user.avatar,user.nickname)"
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
            </v-badge>
            <span class="ml-2">{{ user.nickname }}</span>
          </v-list-item-title>
        </v-list-item-content>
      </v-list-item>
    </v-list>
  </v-sheet>
</template>
<script>
import {avatarMixins} from "@/mixins/avatar-mixins";
import {mapGetters} from "vuex";

export default {
  name: "ChatUsers",
  mixins: [avatarMixins],
  data: () => ({
  }),
  created() {
    //this.onlineUsers().forEach( user => avatar)
  },
  methods: {
  },
  computed: {
    onlineUsers(){
      return this.getUsers.filter( user => user.connected);
    },
    offlineUsers(){
      return this.getUsers.filter( user => !user.connected);
    },
    ...mapGetters('webSocketModule',
        ['getUsers']
    )
  }
}
</script>
<style scoped>
</style>