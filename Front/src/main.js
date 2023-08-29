import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import vuetify from './plugins/vuetify'
import './styles/style.css';
import axios from 'axios'
import VueAxios from 'vue-axios'
import {Global} from "@/global";

Vue.use(VueAxios,axios);

Vue.config.productionTip = false
export const EventBus = new Vue();
axios.defaults.baseURL = Global.baseUrl;
Vue.config.productionTip = false;

axios.interceptors.request.use(
    async config => {
      const token = localStorage.getItem('ACCESS_TOKEN')

      if (token){
        config.headers.Authorization = `Bearer ${token}`;
      }

      config.headers.Accept = 'application/json';
      return config;
    },
    error => {
      return Promise.reject(error);
    }
);

new Vue({
  router,
  store,
  vuetify,
  render: h => h(App)
}).$mount('#app')
