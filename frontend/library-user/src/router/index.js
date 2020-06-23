import Vue from "vue";
import VueRouter from "vue-router";

import Login from '@/views/Login'
import Home from '@/views/Home'
import Book from '@/views/Book'


Vue.use(VueRouter);

export default new VueRouter({
  routes:[
    {
      name:'Login',
      path:'/',
      component: Login
    },
    {
      name:'Home',
      path:'/home',
      component: Home
    },
    {
      name:'Book',
      path:'/book',
      component: Book
    },

  ]
})
