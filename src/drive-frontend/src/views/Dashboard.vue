<template>
  <v-app id='App'>
    <myAppbar />
    <sideNavigation />
    <fileItemList />
  </v-app>
</template>

<script>
import myAppbar from '../components/public/myAppbar';
import fileItemList from '../components/public/fileItemList';
import sideNavigation from '../components/public/sideNavigation'

export default {
  name: 'DashboardView',

  components: {
    myAppbar,
    fileItemList,
    sideNavigation,
  },

  data: () => ({
    //
  }),
};
</script>
