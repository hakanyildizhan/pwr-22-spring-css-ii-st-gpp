import 'vuetify/styles'
import { createApp } from 'vue'
import { createVuetify } from 'vuetify'
import App from './App.vue'
import * as components from 'vuetify/components'
import * as directives from 'vuetify/directives'
import router from './router/router'
import contextmenu from "v-contextmenu";
import "v-contextmenu/dist/themes/default.css";

const vuetify = createVuetify({
    components,
    directives
})

createApp(App)
.use(vuetify)
.use(router)
.use(contextmenu)
.mount('#app')