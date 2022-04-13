<template>
  <v-app id='App'>
    <myAppbar />
    <myBodySection />
  </v-app>
</template>

<script>
import myAppbar from './components/public/myAppbar';
import myBodySection from './components/public/myBodySection';

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
