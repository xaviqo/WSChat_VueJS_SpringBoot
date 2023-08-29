<template>
  <v-container class="fill-height">
    <v-row>
      <v-col>
        <v-row class="d-flex justify-center align-center">
          <NewRoomCard></NewRoomCard>
        </v-row>
      </v-col>
<!--      <v-col class="d-flex justify-center align-center">-->
<!--        <v-card>-->
<!--          zxcv-->
<!--        </v-card>-->
<!--      </v-col>-->
    </v-row>
  </v-container>
</template>

<script>
  import NewRoomCard from "@/components/home/NewRoomCard.vue";

  export default {
    name: 'HomeView',
    components: {NewRoomCard}
  }
</script>
<style>

</style>
