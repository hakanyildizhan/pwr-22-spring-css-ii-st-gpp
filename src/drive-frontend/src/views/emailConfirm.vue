<template>
  <v-app id="app">
    <my-appbar />
    <v-main> Please check your email at {{ $route.params.email }} to confirm your account.</v-main>
  </v-app>
</template>

<script>
import myAppbar from "@/components/public/myAppbar.vue";
export default {
  components: { myAppbar },
};
</script>

<style>
</style>