<template>
  <myAppbar />
  <RegisterForm />
</template>

<script>
import myAppbar from "@/components/public/myAppbar.vue";
import RegisterForm from "@/components/public/registerForm.vue";

export default {
  name: "RegisterView",

  components: {
    myAppbar,
    RegisterForm,
  },
};
</script>
