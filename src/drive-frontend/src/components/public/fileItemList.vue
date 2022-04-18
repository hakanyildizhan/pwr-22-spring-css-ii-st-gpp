<script>
import fileItem from "./fileItem";

export default {
  name: "fileItemList",
  components: {
    fileItem,
  },
  data() {
    return {
      items: [
        {
          id: "0B0tQEjbz9YbuS1ZQdXdaSGRydzg",
          type: "folder",
          title: "Folder 1",
        },
        {
          id: "0B0tQEjbz9YbuS1ZAdXdaSGRydzg",
          type: "folder",
          title: "Folder 2",
        },
        {
          id: "0B0tQEjbz9YbuS1ZAdWdaaGRydzg",
          type: "folder",
          title: "Folder 3",
        },
        {
          id: "0B0tQajbz9YbuS1ZAdXdaaGRydzg",
          type: "file",
          title: "File 1",
        },
        {
          id: "0B0tQajbz9YbuS1ZAdXdaaGRadzg",
          type: "file",
          title: "File 2",
        },
      ],
    };
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
  },
};
</script>

<template>
  <v-main>
    <v-container fluid class="grey lighten-5 mb-6">
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
  </v-main>
</template>

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
</style>