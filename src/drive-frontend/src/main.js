import 'vuetify/styles'
import { createApp } from 'vue'
import { createVuetify } from 'vuetify'
import App from './App.vue'
import * as components from 'vuetify/components'
import * as directives from 'vuetify/directives'

//Vue.config.productionTip = false

const app = createApp(App)
const vuetify = createVuetify({
    components,
    directives
})

app.use(vuetify)
app.mount('#app')