<template>
  <v-main v-contextmenu:contextmenu>
    <v-container fluid class="grey lighten-5 mb-6">
      <!-- end data table  -->
      <div class="item-type">Folders</div>
      <div
        class="drop-zone"
        @drop="onDrop($event)"
        @dragenter.prevent
        @dragover.prevent
      >
        <v-row>
          <v-col
            v-for="element in getFolders()"
            :key="element.id"
            cols="3"
            sm="2"
            md="3"
          >
            <div
              class="drag-el"
              draggable="true"
              @dragstart="startDrag($event, element)"
            >
              <fileItem :id="element.id" type="folder" :title="element.title" />
            </div>
          </v-col>
        </v-row>
      </div>
      <div class="item-type">Files</div>
      <v-row>
        <v-col
          v-for="element in getFiles()"
          cols="3"
          sm="2"
          md="3"
          :key="element.id"
        >
          <div
            class="drag-el"
            draggable="true"
            @dragstart="startDrag($event, element)"
          >
            <fileItem :id="element.id" type="file" :title="element.title" />
          </div>
        </v-col>
      </v-row>

    </v-container>

    <v-btn
      class="upload"
      icon="mdi-plus"
      fab
      dark
      absolute
      color="indigo"
      @click="openFilePicker"
    />

    <v-file-input class="file-input" @change="uploadFile"> </v-file-input>
  </v-main>

  <v-contextmenu ref="contextmenu">
    <v-contextmenu-item @click="openCreateFolderPopup">Create Folder</v-contextmenu-item>
  </v-contextmenu>

  <div class="text-center">
    <v-dialog
      v-model="createFolderPopupIsVisible"
    >
      <v-card width="300" style="padding:20px;">

          <v-text-field
            v-model="folderToCreate"
            label="Folder Name"
            variant="outlined"
            shaped clearable
          ></v-text-field>

        <v-card-actions>
          <v-row>
            <v-col sm="6" align="center" justify="center">
              <v-btn color="primary" @click="createFolderPopupIsVisible = false; this.folderToCreate = 'New folder'">Cancel</v-btn>
            </v-col>
            <v-col sm="6" align="center" justify="center">
              <v-btn color="primary" @click="createFolder">Create</v-btn>
            </v-col>
          </v-row>
          
          
        </v-card-actions>

      </v-card>
    </v-dialog>
  </div>

</template>

<script>
import fileItem from "./fileItem";
import { store } from "../../store";
import { directive, Contextmenu, ContextmenuItem } from "v-contextmenu";
import "v-contextmenu/dist/themes/default.css";

export default {
  name: "fileItemList",
  components: {
    fileItem,
    [Contextmenu.name]: Contextmenu,
    [ContextmenuItem.name]: ContextmenuItem,
  },
  data() {
    return {
      folderToCreate: "New folder",
      createFolderPopupIsVisible: false,
      showMenu: false,
      x: 0,
      y: 0,
      menuItems: [
        { title: "style 1 title", menuAction: "style 1" },
        {
          title: "style 2 title",
          menuAction: (action) => {
            alert(action);
          },
        },
        {
          title: "style 3 title",
          menuAction: (action) => {
            this.someFunc(action);
          },
        },
      ],
      currentPath: ".",
      loading: false,
      showErrorSnack: false,
      store,
      items: [],
    };
  },
  mounted() {
    // fetch user's files on init
    this.loading = true;

    const formData = new FormData();
    formData.append("parentFolder", ".");

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
  directives: {
    contextmenu: directive,
  },
  computed: {
    //   sortedfiles(){
    //     return this.files.sort((a,b)=>{
    //         const aName  = a.name.toUpperCase();
    //         const bName  = b.name.toUpperCase();
    //         let comparison = 0;
    //         if (aName > bName) {
    //   comparison = 1;
    // } else if (aName < bName) {
    //   comparison = -1;
    // }
    //         return comparison;
    //     });
    //   }
  },
  methods: {
    getFolders() {
      return this.items.filter((item) => item.type == "folder");
    },
    getFiles() {
      return this.items.filter((item) => item.type == "file");
    },
    startDrag(event, item) {
      event.dataTransfer.dropEffect = "move";
      event.dataTransfer.effectAllowed = "move";
      event.dataTransfer.setData("itemID", item.id);
    },
    onDrop(event) {
      const sourceItemId = event.dataTransfer.getData("itemID");
      const sourceItem = this.items.filter((i) => i.id == sourceItemId)[0];
      const targetItem = this.items.filter(
        (i) => i.title == event.target.outerText.trim()
      )[0];
      if (sourceItem.id == targetItem.id || targetItem.type == "file") {
        return;
      }
      console.log(sourceItem.title + "-->" + targetItem.title);
    },
    openFilePicker() {
      document
        .getElementsByClassName("file-input")[0]
        .querySelector("input")
        .click();
    },
    uploadFile(e) {
      var files = e.target.files;
      if (files.count == 0) {
        return;
      }

      var file = files[0];
      console.log(file);
      const formData = new FormData();
      formData.append("file", file);
      formData.append("parentFolder", this.currentPath);

      const requestOptions = {
        method: "POST",
        headers: {
          Authorization: "Bearer " + this.store.getToken(),
        },
        body: formData,
      };
      fetch(process.env.VUE_APP_BACKEND_URL + "/files/upload", requestOptions)
        .then((response) => {
          this.loading = false;
          if (!response.ok) {
            // show error
            let error = "An unknown error occurred. Please try again.";
            this.showErrorSnack = true;
          } else {
            return response;
          }
        })
        .then((itemId) => {
          this.items.push({
            id: itemId,
            type: "file",
            title: file.name,
          });
        })
        .catch((err) => {
          let error = "An unknown error occurred. Please try again.";
          this.showErrorSnack = true;
          this.loading = false;
        });
    },
    openCreateFolderPopup() {
      this.createFolderPopupIsVisible = true;
    },
    createFolder() {
      this.createFolderPopupIsVisible = false;
      let folder = this.folderToCreate;
      this.folderToCreate = 'New folder';
      if (!folder) {
        return;
      }
      
      this.loading = true;
      const formData = new FormData();
      formData.append("folderName", folder);
      formData.append("parentFolder", this.currentPath);
      
      const requestOptions = {
        method: "POST",
        headers: {
          Authorization: "Bearer " + this.store.getToken(),
        },
        body: formData,
      };
      fetch(process.env.VUE_APP_BACKEND_URL + "/files/createFolder", requestOptions)
        .then((response) => {
          this.loading = false;
          if (!response.ok) {
            // show error
            throw("Server responded with " + response.status);
          } else {
            return response;
          }
        })
        .then((itemId) => {
          this.items.push({
            id: itemId,
            type: "folder",
            title: folder,
          });
        })
        .catch((err) => {
          this.showErrorSnack = true;
          this.loading = false;
        });
    }
  },
};
</script>

<style>
.item-list {
  grid-area: view;
  overflow: hidden;
  position: relative;
}

.item-list-wrapper {
  flex: 1 1 auto;
  height: 100%;
  width: 100%;
  overflow-y: scroll;
  position: relative;
  display: flex;
  -webkit-flex-flow: column nowrap;
  flex-flow: column nowrap;
}

.item-type {
  color: #5f6368;
  margin-left: 22px;
  padding: 8px 0 16px;
  display: block;
}

.item-grid {
  display: grid;
  grid-gap: 0;
  grid-template-columns: repeat(auto-fill, minmax(220px, 1fr));
}

.upload {
  position: absolute !important;
  bottom: 10% !important;
  right: 25% !important;
}

.file-input {
  display: none;
}
</style>
