import { createRouter, createWebHistory } from "vue-router";
import RegisterView from "@/views/Register";
import DashboardView from "../views/Dashboard";
import IndexDashboard from "@/views/index";
import EmailConfrim from "../views/emailConfirm.vue";
import LoginView from "@/views/Login.vue";
import PageNotFound from "../views/404.vue";
import { store } from "../store";

const routes = [
  {
    path: "/",
    name: "home",
    component: RegisterView,
  },
  {
    path: "/dashboard",
    name: "dashboard",
    component: DashboardView,
    children: [
      {
        path: "",
        name: "index",
        component: IndexDashboard,
      },
    ],
  },

  {
    path: "/emailConfirm/:email",
    name: "emailConfirm",
    component: EmailConfrim,
  },
  {
    path: "/register",
    name: "register",
    component: RegisterView,
  },
  {
    path: "/login",
    name: "login",
    component: LoginView,
  },
  {
    path: "/about",
    name: "about",
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () =>
      import(/* webpackChunkName: "about" */ "../views/AboutView.vue"),
  },
  {
    path: "/:catchAll(.*)",
    name: "404",
    component: PageNotFound,
  },
];

const router = createRouter({
  history: createWebHistory(),
  base: process.env.BASE_URL,
  routes: routes,
});

router.beforeEach((to, from) => {
  let isAuthenticated = store.getToken() && store.getToken() !== "";

  if (
    (to.name == "login" || to.name == "register" || to.name == "home") &&
    isAuthenticated
  ) {
    return { name: "dashboard" };
  } else if (to.name == "dashboard" && !isAuthenticated) {
    return { name: "login" };
  } else if (
    to.name == "emailConfirm" &&
    from.name != "home" &&
    from.name != "register"
  ) {
    return { name: "404" };
  }
  return true;
});

export default router;
