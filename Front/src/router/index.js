import Vue from 'vue';
import VueRouter from 'vue-router';
import HomeView from '../views/HomeView.vue'
import ChatRoom from "@/views/ChatRoom.vue";
import ChatJoin from "@/views/ChatJoin.vue";

Vue.use(VueRouter);

const routes = [
  {
    path: '/',
    name: 'home',
    component: HomeView
  },
  {
    path: '/chat/:roomId',
    name: 'chatroom',
    component: ChatRoom,
    meta: {
      requiresAuth: true
    }
  },
  {
    path: '/chat/:roomId/join',
    name: 'chatjoin',
    component: ChatJoin,
    meta: {
      notInRoom: true
    }
  }
];

const router = new VueRouter({
  mode: 'hash',
  routes
});

router.beforeEach( async (to, from, next) => {
  const isAuthenticated = localStorage.getItem("ACCESS_TOKEN") != null;
  const roomId = localStorage.getItem("CHAT_ID");
  const identicalRoomIds = (roomId === to.params.roomId);
  if (to.meta.requiresAuth) {
    if (!isAuthenticated || !identicalRoomIds) next(`/chat/${to.params.roomId}/join`);
  }
  if (to.meta.notInRoom) {
    if (isAuthenticated && identicalRoomIds) next(`/chat/${to.params.roomId}`);
  }
  next();
});

export default router;
