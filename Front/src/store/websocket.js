import Vue from 'vue'
import Vuex from 'vuex'
import SockJS from 'sockjs-client';
import Stomp from 'webstomp-client'
import MessageType from "@/models/MessageType";
import axios from "axios";
import {Global} from "@/global";
import {EventBus} from "@/main";
Vue.use(Vuex);

export const webSocketModule = {
    namespaced: true,
    state: {
        socketURL: `${Global.baseUrl}/websocket`,
        connected: false,
        stomp: null,
        roomId: null,
        senderId: null,
        senderName: null,
        chatRoom: {
            users: [],
            activity: {
                messages: [],
                actions: [],
                spam: []
            }
        }
    },
    getters: {
        getWsConnected: state => {
            return state.connected;
        },
        getMessages: (state) =>{
            return state.chatRoom.activity.messages;
        },
        getActions: (state) =>{
            return state.chatRoom.activity.actions;
        },
        getSpam: (state) =>{
            return state.chatRoom.activity.spam;
        },
        getUsers: (state) => {
            return state.chatRoom.users;
        },
        getUserById: (state) => (userId) => {
            return state.chatRoom.users.find( user => {
                return (user.id === userId)
            });
        }
    },
    mutations: {
        setWsConnected: (state, connected) => {
            state.connected = connected;
        },
        setStompClient: (state, client) => {
            state.stomp = client;
        },
        setRoomId: (state, roomId) => {
            state.roomId = roomId;
        },
        setSenderId: (state, senderId) => {
            state.senderId = senderId;
        },
        setSenderName: (state, senderName) => {
            state.senderName = senderName;
        },
        setUsers: (state,users) => {
            state.chatRoom.users = users;
        },
        setMessageActivity: (state, activity) => {
            state.chatRoom.activity.messages.push(activity);
        },
        setActionActivity: (state, activity) => {
            state.chatRoom.activity.actions.push(activity);
        },
        setSpamActivity: (state, activity) => {
            state.chatRoom.activity.spam.push(activity);
        }
    },
    actions: {
        filterActivity: (context, activity) => {
            console.log(activity)
            const isLeave = (activity.messageType === MessageType.LEAVE.type);
            switch (activity.messageType) {
                case MessageType.MESSAGE.type:
                    context.commit('setMessageActivity',activity);
                    break;
                case MessageType.LEAVE.type:
                case MessageType.JOIN.type:
                    context.dispatch('loadUsers');
                    EventBus.$emit('showAlert', {
                        color: isLeave ? "warning" : "success",
                        type: isLeave ? "warning" : "success",
                        msg: activity.message
                    });
                    break;
                case MessageType.SPAM.type:
                    EventBus.$emit('showAlert', {
                        color: "warning",
                        type: "warning",
                        msg: activity.message
                    });
                    context.commit('setSpamActivity',activity);
                    break;
                default:
                    context.commit('setActionActivity',activity);
                    break;
            }
        },
        subscribeToChatRoom: (context, roomId) => {
            context.state.stomp.subscribe(`/topic/messages/${roomId}`, tick => {
                context.dispatch('filterActivity',JSON.parse(tick.body));
            });
        },
        connectWS: (context, connectionInfo) => {
            const { senderId, senderName, roomId } = connectionInfo;

            if ((!context.state.connected || !context.state.stomp) && (senderId && senderName && roomId)) {
                const sockjs = new SockJS(context.state.socketURL, null, {
                    sessionId: () => {
                        return roomId + ":" + senderId + ":" + senderName + ":" + Date.now();
                    }
                });
                const stompClient = Stomp.over(sockjs);
                stompClient.debug = () => {};
                stompClient.connect(connectionInfo,
                    () => {
                        context.commit('setRoomId',roomId);
                        context.commit('setSenderId',senderId);
                        context.commit('setSenderName',senderName);
                        context.commit('setStompClient',stompClient);
                        context.commit('setWsConnected',true);
                        context.dispatch('subscribeToChatRoom',roomId);
                        context.dispatch('loadUsers',roomId,senderId);
                    },
                    (error) => {
                        console.error("ERROR EN STOMPCLIENT");
                        console.error(error)
                    }
                );
            }
        },
        sendMessage: (context, message) => {
            context.state.stomp.send(`/ws/chat/${context.state.roomId}`,
                JSON.stringify({
                    roomId: context.state.roomId,
                    userId: context.state.senderId,
                    senderNickname: context.state.senderName,
                    message: message
                }),"");
        },
        setUsers: (context,users) => {
            context.commit('setUsers',users);
        },
        loadUsers: (context) => {
            axios.get(`/chat/${context.state.roomId}/users`)
                .then(res => {
                    context.state.chatRoom.users = [];
                    res.data.inscribedUsers.forEach( user => {
                        if (user.id === context.state.senderId) user.connected = true;
                        context.state.chatRoom.users.push(user);
                    });
                })
                .catch(err => console.log(err))
        },
    }
};