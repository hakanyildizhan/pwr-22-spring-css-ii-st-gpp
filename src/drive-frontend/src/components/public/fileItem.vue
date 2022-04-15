<script>
// import { mdiFolder } from '@mdi/js';
// import { mdiFile } from '@mdi/js';

export default {
    name: 'fileItem',
    props: {
        id: String,
        type: String,
        title: String
    },
    // data() {
    //     return {
    //         icons: {
    //             mdiFolder,
    //             mdiFile
    //         },
    //         id: "0B0tQEjbz9YbuS1ZQdXdaSGRydzg", 
    //         type: 'folder', 
    //         title: 'Folder 1'
    //     }
    // },
    computed: {
        // a computed getter
        icon() {
            // `this` points to the component instance
            return this.type == "file" ? "mdi-file" : "mdi-folder"
        }
    }
}
</script>

<template>
  <!-- <div class="item">
      <div class="wrapper"> -->
          <!-- <div class="icon-wrapper">
            <div class="icon">
                <v-icon :icon="icon"></v-icon>
            </div>
          </div>
          <div class="item-title">{{ this.title }}</div> -->

<v-hover v-slot="{ isHovering, props }">
    <v-card
        class="pa-2"
        outlined
        tile
        :elevation="isHovering ? 6 : 1"
        v-bind="props">
                    
          <!-- <v-chip draggable
          :elevation="isHovering ? 2 : 0"
            class="ma-2"
            color="black"
            label
            text-color="white"
            > -->
                <v-icon start :icon="icon"></v-icon>
            {{ this.title }}
        <!-- </v-chip> -->

    </v-card>
</v-hover>
      <!-- </div>
  </div> -->
</template>

<style>
.item {
    border: 1px solid;
    display: flex;
    flex-direction: column;
    overflow: hidden;
    position: relative;
}

    .item .wrapper {
        display: block;
        color: rgba(0,0,0,.72);
        touch-action: pan-x pan-y;
        display: flex;
        flex: 0 0 48px;
    }

        .wrapper .icon-wrapper {
            flex: 0 0 auto;
            height: 24px;
            padding: 12px 16px;
            position: relative;
            width: 24px;
            z-index: 1;
        }

            .icon-wrapper .icon {
                height: 24px;
                position: relative;
                width: 24px;
            }

        .wrapper .item-title {
            -webkit-align-self: center;
            align-self: center;
            -webkit-box-sizing: border-box;
            box-sizing: border-box;
            -webkit-box-flex: 1 1 auto;
            -webkit-flex: 1 1 auto;
            flex: 1 1 auto;
            font-weight: 500;
            overflow: hidden;
            padding-right: 12px;
            position: relative;
            text-overflow: ellipsis;
            white-space: nowrap;
            width: -webkit-calc(100% - 56px);
            width: calc(100% - 56px);
        }
</style>