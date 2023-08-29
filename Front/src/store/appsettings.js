import Vue from 'vue'
import Vuex from 'vuex'
import axios from 'axios'

Vue.use(Vuex);

export const appSettingsModule = {
    namespaced: true,
    state: {
        availableAvatars: [],
        roomCapacitySelect: []
    },
    getters: {
        getAvailableAvatars: (state) => {
            return state.availableAvatars;
        },
        getRoomCapacitySelect: (state) => {
            return state.roomCapacitySelect;
        }
    },
    mutations: {
        setSettings: (state, settings) => {
            const { availableAvatars, roomCapacitySelect } = settings;
            state.availableAvatars = availableAvatars;
            state.roomCapacitySelect = roomCapacitySelect;
        }
    },
    actions: {
        loadAppSettings: (context) => {
            let settings = {};
            axios
                .get(`/chat/configuration`)
                .then((res) => {
                    settings = res.data;
                    settings.roomCapacitySelect = [];
                    for (let i = 2; i <= settings.maxRoomCapacity; i++) {
                        settings.roomCapacitySelect.push(i);
                    }
                    context.commit('setSettings', settings);
                });
        }
    }
};