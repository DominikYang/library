import Vue from "vue";
import VueRouter from "vue-router";

import Login from '@/views/Login'
import User from '@/views/UserManage'
import Warn from '@/views/WarnLog'
import Operate from '@/views/OperateLog'
import Book from "../views/BookManage";


Vue.use(VueRouter);

export default new VueRouter({
  routes:[
    {
      name:'Login',
      path:'/',
      component: Login
    },
    {
      name:'Book',
      path:'/book',
      component: Book
    },
    {
      name:'Operate',
      path:'/operate',
      component: Operate
    },
    {
      name:'User',
      path:'/user',
      component: User
    },
    {
      name:'Warn',
      path:'/warn',
      component: Warn
    },


  ]
})
