import {  createApp } from 'vue'
// Import Bootstrap and BootstrapVue CSS files (order is important)
//import 'bootstrap/dist/css/bootstrap.css'

import "bootstrap/dist/css/bootstrap.min.css"
import "bootstrap";
import "prismjs";
import "prismjs/themes/prism-tomorrow.css";
// global.jQuery = require('jquery');
// var $ = global.jQuery;
// window.$ = $;

//import 'bootstrap-vue/dist/bootstrap-vue.css'

import App from './App.vue'

const app = createApp(App)
app.mount('#app')
