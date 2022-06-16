<template>
  <v-main>
    <v-card style="height: 80vh; background-color: #e8f5e9" rounded="0">
      <v-row>
        <v-col cols="5"></v-col>
        <v-col cols="7" class="py-10 my-auto">
          <v-card class="justify-center py-15" elevation="3" rounded="0">
            <v-card-title class="justify-center"
              ><span class="text-green-darken-3">Login</span></v-card-title
            >
            <v-card-text>
              <v-card-title class="justify-center">
                <template v-if="showErrorSnack">
                  <span class="text-red-lighten-3">{{
                    this.validationError
                  }}</span>
                </template>
              </v-card-title>
              <v-card class="px-16" elevation="0">
                <v-form
                  class="px-16"
                  ref="form"
                  v-model="valid"
                  lazy-validation
                >
                  <v-text-field
                    label="Email"
                    v-model="loginForm.email"
                    :rules="loginValidate.emailRules"
                    @keydown="checkValidation"
                  />
                  <v-text-field
                    label="Password"
                    type="password"
                    @click="showPasswordValidations = true"
                    v-model="loginForm.password"
                    :rules="loginValidate.passwordRules"
                    @keydown="checkValidation"
                  />

                  <!--<template v-if="showPasswordValidations">
                                        <p
                                            :class="passwordLengthvalid? 'green--text' : 'red--text'"
                                        >
                                        Password length must be greater than 6
                                        </p>
                                        <p>Password must contain uppercase</p>
                                        <p>Password must contain lowercase</p>
                                        <p>Password must contain symbol</p>
                                        <p>Password must contain number</p> 
                                    </template>-->
                  <v-card-title class="justify-center">
                    <v-btn
                      class="success"
                      :loading="loading"
                      :disabled="loading || !valid"
                      @click="submitLogin"
                      large
                    >
                      Login
                    </v-btn>
                  </v-card-title>
                </v-form>
              </v-card>
            </v-card-text>
          </v-card>
        </v-col>
      </v-row>
    </v-card>
  </v-main>
</template>

<script>
import { store } from "../../store";

export default {
  name: "LoginForm",
  data: () => ({
    store,
    validationError: "Incorrect email or password. Please try again.",
    valid: true,
    showErrorSnack: false,
    loading: false,
    passwordLengthvalid: false,
    showPasswordValidations: false,
    loginForm: {
      email: "",
      password: "",
    },
    loginValidate: {
      passwordRules: [
        (v) => !!v || "Password is required",
        (v) => (v && v.length >= 8) || "Password length must be greater than 8",
        (v) => /[A-Z]/.test(v) || "Password does not contain uppercase",
        (v) => /[a-z]/.test(v) || "Password does not contain lowercase",
        (v) => /[0-9]/.test(v) || "Password does not contain number",
        (v) =>
          /[!@#$%^&.+-/*{}()[\]"'=]/.test(v) ||
          "Password does not contain a special character",
      ],
      emailRules: [
        (v) => !!v || "E-mail is required",
        //  v => /.+@.+/.test(v) || 'E-mail must be valid',
        (v) =>
          !v ||
          /^\w+([.-]?\w+)*@\w+([.-]?\w+)*(\.\w{2,3})+$/.test(v) ||
          "E-mail must be valid",
      ],
    },
  }),
  methods: {
    submitLogin() {
      this.loading = true;
      this.showErrorSnack = false;

      if (this.$refs.form.validate()) {
        const requestOptions = {
          method: "POST",
          headers: { "Content-Type": "application/json" },
          body: JSON.stringify({
            email: this.loginForm.email,
            password: this.loginForm.password,
          }),
        };
        fetch(process.env.VUE_APP_BACKEND_URL + "/login", requestOptions)
          .then((response) => {
            this.loading = false;
            if (!response.ok) {
              // show error
              if (response.status == 400) {
                this.validationError =
                  "Incorrect email or password. Please try again.";
              } else {
                this.validationError =
                  "An unknown error occurred. Please try again.";
              }
              this.showErrorSnack = true;
            } else {
              return response.json();
            }
          })
          .then((data) => {
            if (data) {
              this.store.setToken(data.token);
              this.store.setUser(data.userId);
              this.$router.push({
                name: "dashboard",
                params: { email: data.userId },
              });
            }
          });
      } else {
        this.loading = false;
        console.log("error");
      }
    },
    checkValidation() {
      this.$refs.form.validate();
    },
  },
  watch: {
    "loginForm.password": function () {
      if (this.loginForm.password.length > 6) {
        this.passwordLengthvalid = true;
      } else {
        this.passwordLengthvalid = false;
      }
    },
  },
};
</script>

<style></style>
