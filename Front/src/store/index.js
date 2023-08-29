import Vue from 'vue'
import Vuex from 'vuex'
import {appSettingsModule} from "@/store/appsettings";
import {userStorageModule} from "@/store/userstorage";
import {webSocketModule} from "@/store/websocket";

Vue.use(Vuex)

export default new Vuex.Store({
  modules: {
    appSettingsModule: appSettingsModule,
    userStorageModule: userStorageModule,
    webSocketModule: webSocketModule
  }
})
