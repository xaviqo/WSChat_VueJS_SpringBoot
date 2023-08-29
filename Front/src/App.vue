<template>
  <v-app>
    <ChatHeader />
    <v-alert
        border="bottom"
        dismissible
        colored-border
        :type="alert.type"
        :color="alert.color"
        elevation="4"
        width="20%"
        v-model="alert.show"
        transition="slide-y-transition"
        style="position: absolute; right: 5%; top: 5%; z-index:20001;"
    >
      {{ alert.message }}
    </v-alert>
    <v-main class="fill-height background">
      <router-view/>
    </v-main>
  </v-app>
</template>

<script>

import ChatHeader from "@/components/shared/ChatHeader.vue";
import {EventBus} from "@/main";

export default {
  name: 'App',
  components: {ChatHeader},
  created() {
    EventBus.$on('showAlert', model => {
      this.showAlert(model);
    });
  },
  data: () => ({
    alert: {
      message: null,
      type: null,
      color: null,
      show: false
    }
  }),
  methods: {
    showAlert(model) {
      this.alert = {
        color: model.color,
        type: model.type,
        message: model.msg,
        show: true
      }
      setTimeout(() => this.alert.show = false, 2200);
    }
  },
};
</script>
<style>
.background {
  background-image: url("@/assets/img/background.jpg");
  background-position: center;
  background-size: cover;
}
</style>