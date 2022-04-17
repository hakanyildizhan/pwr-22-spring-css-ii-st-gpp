<template>
  <v-app id='App'>
  
    <myAppbar />
    <!--<sideNavigation />
    <fileItemList /> -->
    <myBodySection />
    
  </v-app>
</template>

<script>
import myAppbar from './components/public/myAppbar';
// import fileItemList from './components/public/fileItemList';
// import sideNavigation from './components/public/sideNavigation'
import myBodySection from './components/public/myBodySection';

export default {
  name: 'App',

  components: {
    myAppbar,
    // fileItemList,
    // sideNavigation,
    myBodySection,
  },

  data: () => ({
    //
  }),
};
</script>
