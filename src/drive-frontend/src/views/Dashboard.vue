<template>
  <v-app id="App">
    <myAppbar />
    <sideNavigation />
    <router-view />
  </v-app>
</template>

<script>
import myAppbar from "../components/public/myAppbar";
import sideNavigation from "../components/public/sideNavigation";

export default {
  name: "DashboardView",

  components: {
    myAppbar,
    sideNavigation,
  },

  data: () => ({
    //
  }),
};
</script>
