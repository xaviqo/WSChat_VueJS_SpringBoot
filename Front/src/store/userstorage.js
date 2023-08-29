import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex);

export const userStorageModule = {
    namespaced: true,
    getters: {
        getUserId: () => {
            return localStorage.getItem("USER_ID");
        },
        getUserNickname: () => {
            return localStorage.getItem("USER_NICKNAME");
        },
        getRoomTopic: () => {
            return localStorage.getItem("ROOM_TOPIC");
        },
        getAccessToken: () => {
            return localStorage.getItem("ACCESS_TOKEN");
        },
        getAvatarStyle: () => {
            return localStorage.getItem("AVATAR_STYLE");
        },
        getChatId: () => {
            return localStorage.getItem("CHAT_ID");
        },
        isUserConnected: () => {
            return localStorage.getItem("ACCESS_TOKEN") != null;
        }
    },
    mutations: {
        setUserStorage: (state, payload) => {
            localStorage.setItem("USER_ID",payload.userId);
            localStorage.setItem("USER_NICKNAME",payload.userNickname);
            localStorage.setItem("ROOM_TOPIC",payload.roomTopic);
            localStorage.setItem("ACCESS_TOKEN",payload.tokenPayload.accessToken);
            localStorage.setItem("AVATAR_STYLE",payload.avatarStyle);
            localStorage.setItem("CHAT_ID",payload.chatId);
        },
        clearUserStorage: () => {
            localStorage.clear();
        }
    },
    actions: {
        setUserStorageAction: (context, payload) => {
            context.commit('setUserStorage',payload);
        },
        disconnectUser: (context) => {
            context.commit('clearUserStorage');
        }
    }
};