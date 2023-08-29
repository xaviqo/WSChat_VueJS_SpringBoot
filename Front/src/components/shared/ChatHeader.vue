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
      <v-btn text>
        <v-icon small>
          {{ opt.icon }}
        </v-icon>
        {{ opt.title }}
      </v-btn>
    </v-toolbar-items>
  </v-app-bar>
</template>
<script>
export default {
  name: "ChatHeader",
  data: () => ({
    menu: [
      {
        title: 'create room',
        icon: 'mdi-plus',
        link: '/create'
      },
      {
        title: 'join room',
        icon: 'mdi-chat-plus-outline',
        link: '/join'
      },
      {
        title: 'source code',
        icon: 'mdi-github',
        link: '/code'
      },
      {
        title: 'xavi.tech',
        icon: 'mdi-code-braces',
        link: '/xavitech'
      }
    ]
  })
}
</script>
