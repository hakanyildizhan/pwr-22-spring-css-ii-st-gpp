<template>
  <v-app id="app">
    <my-appbar />
    <my-body-section />
  </v-app>
</template>

<script>
import MyAppbar from "@/components/public/myAppbar.vue";

import MyBodySection from "@/components/public/myBodySection.vue";

export default {
  name: "Home-page",

  components: {
    MyAppbar,
    MyBodySection,
  },
};
</script>
