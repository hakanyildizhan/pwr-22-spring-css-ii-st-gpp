<template>
  <v-app id="app">
    <my-appbar />
    <v-main>
      <v-card elevation="0" color="blue">
        <v-card-title class="justify-center"> link is send </v-card-title>
        <router-link to="/emailConfirm"> go to email confirm page </router-link>
      </v-card>
    </v-main>
  </v-app>
</template>

<script>
import myAppbar from "./myAppbar.vue";
export default {
  components: { myAppbar },
  methods: {
    //   gotoconrim(){
    //       this.$router.push("/")
    //   }
  },
};
</script>

<style>
</style>