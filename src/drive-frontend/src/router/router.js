import { createRouter, createWebHistory  } from 'vue-router'
import RegisterView from '@/views/Register'
import DashboardView from "../views/Dashboard"
import EmailRedirect from "../components/public/emailRedirect"
import EmailConfrim from "../views/emailConfirm.vue"
import LoginView from "@/views/Login.vue"
import PageNotFound from "../views/404.vue"

const routes = [
  {
    path: '/',
    name: 'home',
    component: RegisterView
  },
  {
    path: '/email',
    name: 'email',
    component: EmailRedirect
  },
  {
    path: '/dashboard',
    name: 'dashboard',
    component: DashboardView
  },
  {
    path: '/emailConfirm/:email',
    name: 'emailConfirm',
    component: EmailConfrim
  },
  {
    path: '/register',
    name: 'register',
    component: RegisterView
  },
  {
    path: '/login',
    name: 'login',
    component: LoginView
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
    path: "/:catchAll(.*)",
    name: '404',
    component: PageNotFound
  },
]

const router = createRouter({
  history: createWebHistory(),
  base: process.env.BASE_URL,
  routes: routes
})

export default router
