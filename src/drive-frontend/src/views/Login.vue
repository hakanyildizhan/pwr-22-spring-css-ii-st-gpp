<template>
    <myAppbar />
    <LoginForm />
</template>

<script>
import myAppbar from "@/components/public/myAppbar.vue";

import LoginForm from "@/components/public/loginForm.vue";

export default {
  name: "LoginView",

  components: {
    myAppbar,
    LoginForm,
  },
};
</script>
