<template>
  <v-app-bar app elevation="0" class="slight-opacity">
    <div>
      <h1>
        <v-icon x-large color="black" class="mr-2">mdi-forum-outline</v-icon>
        <span class="grey--text text--darken-2 font-weight-medium">free</span>
        <span class="grey--text text--darken-3 font-weight-bold">fast</span>
        <span class="grey--text text--darken-4 font-weight-black">chat</span>
      </h1>
    </div>
    <v-spacer></v-spacer>
    <v-toolbar-items app v-for="(opt, i) in menu" :key="'tb_'+i">
      <v-btn text v-if="mobile.showLinks && shouldShowLink(opt)" @click="goToLink(opt)">
        <v-icon small>
          {{ opt.icon }}
        </v-icon>
        {{ opt.title }}
      </v-btn>
    </v-toolbar-items>
  </v-app-bar>
</template>
<script>
import {mobileMixins} from "@/mixins/mobile-mixins";

export default {
  mixins: [mobileMixins],
  name: "ChatHeader",
  created() {
    window.addEventListener('resize', this.onResize, { passive: true });
    this.onResize();
  },
  methods: {
    onResize() {
      this.mobile.showLinks = this.getWindowWidth() > 850;
    },
    shouldShowLink(link){
      const currentRoute = this.$route.path;
      return currentRoute === "/" ? link.showInMain : true;
    },
    goToLink(link){
      window.location.href = link.link;
    }
  },
  data: () => ({
    mobile: {
      showLinks: true
    },
    menu: [
      {
        title: 'create room',
        icon: 'mdi-plus',
        link: '/create',
        showInMain: false
      },
      {
        title: 'join room',
        icon: 'mdi-chat-plus-outline',
        link: '/join',
        showInMain: false
      },
      {
        title: 'source code',
        icon: 'mdi-github',
        link: 'https://github.com/xaviqo/WSChat_VueJS_SpringBoot',
        showInMain: true
      },
      {
        title: 'xavi.tech',
        icon: 'mdi-code-braces',
        link: 'https://xavi.tech',
        showInMain: true
      }
    ]
  })
}
</script>
