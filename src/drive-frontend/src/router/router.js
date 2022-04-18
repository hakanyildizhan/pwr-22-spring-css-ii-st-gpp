import Vue from 'vue'
import VueRouter from 'vue-router'
import HomeView from '../views/HomeView.vue'
import item from "../components/public/item"
import EmailRedirect from "../components/public/emailRedirect"
import EmailConfrim from "../views/emailConfirm.vue"
import PageNotFound from "../views/404.vue"

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'home',
    component: HomeView
  },
  {
    path: '/email',
    name: 'email',
    component: EmailRedirect
  },
  {
    path: '/dashboard',
    name: 'dashboard',
    component: item
  },
  {
    path: '/emailConfirm',
    name: 'emailConfirm',
    component: EmailConfrim
  },
  {
    path: '/about',
    name: 'about',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import(/* webpackChunkName: "about" */ '../views/AboutView.vue')
  },
  {
    path: '*',
    name: '404',
    component: PageNotFound
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
