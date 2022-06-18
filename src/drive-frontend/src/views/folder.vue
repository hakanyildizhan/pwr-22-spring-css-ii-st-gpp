<script>
import { store } from "@/store";
import FileItem from "../components/public/fileItem.vue";

import { directive, Contextmenu, ContextmenuItem } from "v-contextmenu";
import "v-contextmenu/dist/themes/default.css";

export default {
  components: {
    FileItem,
    [Contextmenu.name]: Contextmenu,
    [ContextmenuItem.name]: ContextmenuItem,
  },
  name: "folder-page",

  data() {
    return {
      items: [],
      loading: false,
      showErrorSnack: false,
      store,
    };
  },
  directives: {
    contextmenu: directive,
  },
  mounted() {
    this.loading = true;
    const formData = new FormData();
    formData.append("parentFolder", this.$route.params.folderId);
    const requestOptions = {
      method: "POST",
      headers: {
        Authorization: "Bearer " + this.store.getToken(),
      },
      body: formData,
    };
    fetch(process.env.VUE_APP_BACKEND_URL + "/files/list", requestOptions)
      .then((response) => {
        this.loading = false;

        if (response.ok) {
          response.json().then((returnedItems) => {
            this.items = [];
            returnedItems.forEach((item) =>
              this.items.push({
                id: item.id,
                type: item.type == 2 ? "file" : "folder",
                title: item.displayName,
              })
            );
          });
        } else {
          response.text().then((err) => {
            let error = "An unknown error occurred. Please try again.";
            this.showErrorSnack = true;
            if (err == "Token expired") {
              this.store.logout();
              this.$router.push({ name: "login" });
            }
          });
        }
      })
      .catch((err) => {
        let error = "An unknown error occurred. Please try again.";
        this.showErrorSnack = true;
        this.loading = false;
      });
  },
};
</script>

<template>
  <v-main>
    <v-contextmenu ref="contextmenu">
      <v-contextmenu-item>Open</v-contextmenu-item>
      <v-contextmenu-item>Download</v-contextmenu-item>
    </v-contextmenu>
    <v-container>
      <div class="py-4">
        <v-btn @click="$router.go(-1)">Go Back</v-btn>
      </div>
      <v-divider />
      <div v-if="loading" class="py-4">loading</div>
      <div v-else class="py-4">
        <div v-if="items.length == 0">no item found</div>
        <div v-else>
          <v-row>
            <v-col
              v-for="element in items"
              cols="3"
              sm="2"
              md="3"
              :key="element.id"
              v-contextmenu:contextmenu
            >
              <file-item
                :id="element.id"
                :type="element.type"
                :title="element.title"
              />
            </v-col>
          </v-row>
        </div>
      </div>
    </v-container>
  </v-main>
</template>
