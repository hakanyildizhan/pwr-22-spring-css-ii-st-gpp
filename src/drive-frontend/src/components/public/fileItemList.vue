<script>
import fileItem from './fileItem';

export default {
    name: 'fileItemList',
    components: {
        fileItem
    },
    data() {
        return {
            items: [
                {
                    id: "0B0tQEjbz9YbuS1ZQdXdaSGRydzg", 
                    type: 'folder', 
                    title: 'Folder 1'
                },
                {
                    id: "0B0tQEjbz9YbuS1ZAdXdaSGRydzg", 
                    type: 'folder', 
                    title: 'Folder 2'
                },
                {
                    id: "0B0tQEjbz9YbuS1ZAdWdaaGRydzg", 
                    type: 'folder', 
                    title: 'Folder 3'
                },
                {
                    id: "0B0tQajbz9YbuS1ZAdXdaaGRydzg", 
                    type: 'file', 
                    title: 'File 1'
                },
                {
                    id: "0B0tQajbz9YbuS1ZAdXdaaGRadzg", 
                    type: 'file', 
                    title: 'File 2'
                },
            ]
        }
    },
    methods: {
        getFolders() {
            return this.items.filter(item => item.type == 'folder')
        },
        getFiles() {
            return this.items.filter(item => item.type == 'file')
        }
    }
}

</script>

<template>
    <v-main>
        
        <v-container fluid class="grey lighten-5 mb-6">

            <div class="item-type">Folders</div>

            <div v-for="rowNr in Math.floor(this.getFolders().length/3) + this.getFolders().length/3 % 3 != 0 ? 1 : 0" :key="rowNr">

                <v-row>
                    <v-col
                    v-for="colNr in 3"
                    :key="colNr">

                        <fileItem :id="this.getFolders()[(rowNr-1)*3+colNr-1].id" 
                                type="folder" 
                                :title="this.getFolders()[(rowNr-1)*3+colNr-1].title" />

                    </v-col>
                </v-row>
            </div>

            <div class="item-type">Files</div>

            <div v-for="rowNr in Math.floor(this.getFiles().length/3) + this.getFiles().length/3 % 3 != 0 ? 1 : 0" :key="rowNr">

                <v-row>
                    <v-col
                    v-for="colNr in 3"
                    :key="colNr">

                        <fileItem v-if="this.getFiles().length > (rowNr-1)*3+colNr-1"
                                :id="this.getFiles()[(rowNr-1)*3+colNr-1].id" 
                                type="file" 
                                :title="this.getFiles()[(rowNr-1)*3+colNr-1].title" />

                    </v-col>
                </v-row>
            </div>
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
        grid-template-columns: repeat(auto-fill,minmax(220px,1fr));
    }

</style>