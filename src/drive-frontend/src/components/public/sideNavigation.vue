<script>

export default {
    name: 'sideNavigation',
    props: {
        id: String,
        type: String,
        title: String
    },
}
</script>

<template>

    <v-navigation-drawer
      app
      hide-overlay
    >
      <v-list>
        <v-list-item
          prepend-avatar="https://icon-library.com/images/anonymous-user-icon/anonymous-user-icon-2.jpg"
          title="User"
          :subtitle="$route.params.email"
        ></v-list-item>
      </v-list>

      <v-divider></v-divider>

      <v-list density="compact" nav>
        <v-list-item prepend-icon="mdi-folder" title="My Files" value="myfiles"></v-list-item>
        <v-list-item prepend-icon="mdi-account-multiple" title="Shared with me" value="shared"></v-list-item>
        <v-list-item prepend-icon="mdi-star" title="Starred" value="starred"></v-list-item>
      </v-list>
    </v-navigation-drawer>

</template>