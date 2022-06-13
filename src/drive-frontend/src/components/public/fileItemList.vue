<script>
import fileItem from "./fileItem";

export default {
  name: "fileItemList",
  components: {
    fileItem,
  },
  data() {
    return {
      files:[
        {
          name:'cv.pdf',
          owner:'me',
          lastmodified:'2022-02-01',
          filesize:1.2
        },
         {
          name:'ml_for_researcher.pdf',
          owner:'me',
          lastmodified:'2022-02-01',
          filesize:1.2
        }
      ],
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
  computed:{
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
  },
};
</script>

<template>
  <v-main>
    <v-container fluid class="grey lighten-5 mb-6">
      <!-- start data table -->
         <v-table>
           <thead>
             <th class="text-left">
                Name
                <v-icon
      large
      color="green darken-2"
    >
      mdi-arrow-up
    </v-icon>
             </th>
             <th class="text-left">
                Owner
             </th>
             <th class="text-left">
                Last Modified
             </th>
              <th class="text-left">
                File Size
             </th>
           </thead>
             <tbody>
               <tr v-for="(file,index) in files" :key="index">
               <td>  {{file.name}}</td>
               <td>  {{file.owner}}</td>
               <td>  {{file.lastmodified}}</td>
               <td>  {{file.filesize}}</td>
                
               </tr>
             </tbody>
         </v-table>

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

    <v-btn class="upload"
          icon="mdi-plus" 
          fab dark absolute 
          color="indigo"
          @click="openFilePicker"/>

    <v-file-input class="file-input"
                  @change="uploadFile">
    </v-file-input>
  </v-main>
</template>

<script>
import fileItem from "./fileItem";
//const http = require('http');
//import * as http from 'http'

export default {
  name: "fileItemList",
  components: {
    fileItem,
  },
  data() {
    return {
      id: 0,
      items: [
        // {
        //   id: "0B0tQEjbz9YbuS1ZQdXdaSGRydzg",
        //   type: "folder",
        //   title: "Folder 1",
        // },
        // {
        //   id: "0B0tQEjbz9YbuS1ZAdXdaSGRydzg",
        //   type: "folder",
        //   title: "Folder 2",
        // },
        // {
        //   id: "0B0tQEjbz9YbuS1ZAdWdaaGRydzg",
        //   type: "folder",
        //   title: "Folder 3",
        // },
        // {
        //   id: "0B0tQajbz9YbuS1ZAdXdaaGRydzg",
        //   type: "file",
        //   title: "File 1",
        // },
        // {
        //   id: "0B0tQajbz9YbuS1ZAdXdaaGRadzg",
        //   type: "file",
        //   title: "File 2",
        // },
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
    openFilePicker() {
      document.getElementsByClassName('file-input')[0].querySelector('input').click();
    },
    uploadFile(e) {
      var files = e.target.files;
      if (files.count == 0) {
        return;
      }

      var file = files[0]
      console.log(file)

      var FormData = require('form-data')
      var form = new FormData();
      form.append('file', file);
      form.append('filename', file.name);

      // var options = {
      //   method: 'post',
      //   host: 'http://localhost:8080',
      //   path: '/files/uploadFile',
      // }

      
      // const request = http.request({
      //   method: 'post',
      //   host: 'http://localhost:8080',
      //   path: '/files/uploadFile',
      // })

      // form.pipe(request);

      // request.on('response', function(res) {
      //   console.log(res.statusCode);
      // });

      // form.submit(options, function(err, res) {
      //   console.log(res.statusCode);
      // })

      this.items.push({
          id: ++this.id,
          type: "file",
          title: file.name,
        })
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