<script>
import { directive, Contextmenu, ContextmenuItem } from "v-contextmenu";
import "v-contextmenu/dist/themes/default.css";
import { store } from "../../store";

export default {
  name: "fileItem",
  components: {
    [Contextmenu.name]: Contextmenu,
    [ContextmenuItem.name]: ContextmenuItem,
  },
  props: {
    id: String,
    type: String,
    title: String,
  },
  data() {
    return {
      store,
    };
  },
  computed: {
    icon() {
      return this.type == "file" ? "mdi-file" : "mdi-folder";
    },
    showView() {
      if (this.type === "file") {
        var fileExt = this.title.split(".").pop();
        let fileExtension = ["pdf", "doc", "jpg", "jpeg", "png"];
        if (fileExtension.includes(fileExt)) {
          return true;
        } else {
          return false;
        }
      }
      return false;
    },
    showDownload() {
      if (this.type === "file") {
        return true;
      }
      return false;
    },
  },
  directives: {
    contextmenu: directive,
  },
  methods: {
    view() {
      // we can only view a file
      if (this.type === "file") {
      //   // show the download option
      this.showDownload = true;
      var fileExt = this.title.split(".").pop();
      let fileExtension = ["pdf", "doc", "jpg", "jpeg", "png"];
      if (fileExtension.includes(fileExt)) {
      //     // show the view icon
       this.showView = true;
      }
     }
    },
    download() {
      const requestOptions = {
        method: "GET",
        headers: {
          Authorization: "Bearer " + this.store.getToken(),
        }
      };

      fetch(process.env.VUE_APP_BACKEND_URL + "/files/downloadFile?" +
          new URLSearchParams({
            key: this.id,
          }),
        {
          requestOptions,
        }
      )
      .then((response) => response.blob())
      .then((blob) => {
        const link = document.createElement('a')
        link.href = URL.createObjectURL(blob)
        link.download = this.title;
        link.click()
        URL.revokeObjectURL(link.href)
      });
    },
  },
};
</script>

<template>
  <v-hover v-slot="{ isHovering, props }">
    <v-contextmenu ref="contextmenu2">
      <v-contextmenu-item @click="download">Download</v-contextmenu-item>
      <v-contextmenu-item>Share</v-contextmenu-item>
    </v-contextmenu>
    <v-card
      class="pa-2"
      outlined
      tile
      :elevation="isHovering ? 6 : 1"
      v-bind="props"
      v-contextmenu:contextmenu2
    >
      <v-icon start :icon="icon"></v-icon>
      {{ this.title }}
    </v-card>
  </v-hover>
</template>
