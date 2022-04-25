<script>
export default {
  name: "fileItem",
  props: {
    id: String,
    type: String,
    title: String,
  },
  computed: {
    icon() {
      return this.type == "file" ? "mdi-file" : "mdi-folder";
    },
  },
};
</script>

<template>
  <v-hover v-slot="{ isHovering, props }">
    <v-card
      class="pa-2"
      outlined
      tile
      :elevation="isHovering ? 6 : 1"
      v-bind="props"
    >
      <v-icon start :icon="icon"></v-icon>
      {{ this.title }}
    </v-card>
  </v-hover>
</template>
