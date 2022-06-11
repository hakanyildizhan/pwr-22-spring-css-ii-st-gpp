<template>
  <v-app id="app">
    <my-appbar />
    <v-main>
      <v-card class="justify-center py-15" elevation="3" rounded="0">
        <v-card-title class="justify-center">
          <span class="text-green-darken-3">Email Confirmation</span>
        </v-card-title>
        <v-card-text>
          <v-card-title class="justify-center">
            <span class="text-red-lighten-3"
              >Please check your email at {{ $route.params.email }} to confirm
              your account.</span
            >
          </v-card-title>
        </v-card-text>
      </v-card>
    </v-main>
  </v-app>
</template>

<script>
import myAppbar from "@/components/public/myAppbar.vue";
export default {
  components: { myAppbar },
};
</script>

<style></style>
