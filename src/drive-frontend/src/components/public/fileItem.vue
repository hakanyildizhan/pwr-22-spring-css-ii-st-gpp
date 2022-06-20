<script>
export default {
  name: "fileItem",
  props: {
    id: String,
    type: String,
    title: String,
  },
  data() {
    return {};
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
      const formData = new FormData();
      formData.append("parentFolder", ".");

      const requestOptions = {
        method: "POST",
        headers: {
          Authorization: "Bearer " + this.store.getToken(),
        },
        body: formData,
      };
      fetch(
        process.env.VUE_APP_BACKEND_URL +
          "/files/downloadFile?" +
          new URLSearchParams({
            key: this.id,
          }),
        {
          requestOptions,
        }
      )
        .then((response) => response.blob())
        .then((blob) => {
          var url = window.URL.createObjectURL(blob);
          var a = document.createElement("a");
          a.href = url;
          a.download = "filename.xlsx";
          document.body.appendChild(a); // we need to append the element to the dom -> otherwise it will not work in firefox
          a.click();
          a.remove();
        });
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
      <p class="text-wrap">
        {{ this.title }}
      </p>

      <v-icon v-if="showView">mdi-eye</v-icon>
      <v-icon v-if="showDownload">mdi-download</v-icon>
    </v-card>
  </v-hover>
</template>
