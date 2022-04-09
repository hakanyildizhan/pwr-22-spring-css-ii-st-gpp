<template>
  <v-app id='App'>
      <my-appbar/>
      <my-body-section/>
  </v-app>
</template>

<script>
import myAppbar from './components/public/myAppbar';
import myBodySection from './components/public/myBodySection.vue';

export default {
  name: 'App',

  components: {
    myAppbar,
    myBodySection
  },

  data: () => ({
    //
  }),
};
</script>
