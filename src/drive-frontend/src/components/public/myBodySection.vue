<template>
    <v-main>
        <v-card style="height: 80vh; background-color: #E8F5E9" rounded="0"> 
            <v-row>
                <v-col cols="6"></v-col>
                <v-col cols="6" class="py-10 my-auto">
                    <v-card class="justify-center py-15" elevation="3" rounded="0">
                        <v-card-title class="justify-center"><span class="text-green-darken-3">Register</span></v-card-title>
                        <v-card-text>
                            <v-card-title class="justify-center">
                                <template v-if="showErrorSnack">
                                    <span class="text-red-lighten-3">Email exists</span>
                                </template>
                            </v-card-title>
                            <v-card class="px-16" elevation="0">
                                <v-form class="px-16">
                                    <v-text-field
                                        label="Email"
                                        v-model="registrationForm.email"
                                        :rules="registrationValidate.emailRules"
                                    />
                                    <v-text-field
                                        label="Password"
                                        type="password"
                                        @click="showPasswordValidations=true"
                                        v-model="registrationForm.password"
                                        :rules="registrationValidate.passwordRules"
                                    />
                                    <v-card-title class="justify-center">
                                       <v-btn
                                            class="success"
                                            :loading="loading"
                                            :disabled="loading"
                                            @click="submitRegistration"
                                            large
                                        >
                                            Register
                                        </v-btn>
                                    </v-card-title>
                                </v-form>
                            </v-card>
                            <v-card-title class="justify-center">
                                <span class="text-subtitle-2"> You already have an account?</span> 
                                <v-btn variant="plain">Login</v-btn>
                            </v-card-title>
                        </v-card-text>
                    </v-card>
                </v-col>
            </v-row>
        </v-card>
        <v-card elevation="0">
            <v-card-title class="justify-center">About us</v-card-title>
            <v-card-text>
                Lorem ipsum dolor sit amet consectetur adipisicing elit. Architecto
                tempore sit magni tenetur vitae nisi laborum quaerat dolorem officia
                debitis voluptas veniam porro facere, dolorum reiciendis exercitationem
                qui, vero accusamus! Lorem ipsum dolor sit amet consectetur adipisicing
                elit. Ut eaque aut provident? Culpa magni ut assumenda quam odit eius
                dolores cum quas consequatur voluptate, natus ab velit minus rerum
                facere.
            </v-card-text>
        </v-card>
    </v-main>
</template>

<script>
    export default {
        name: "myBodySection",
        data:()=>({
            showErrorSnack: false,
            loading: false,
            passwordLengthvalid: false,
            showPasswordValidations: false,
            registrationForm:{
                email:"",
                password:"",
            },
            registrationValidate:{
                passwordRules: [
                    v => !!v || "Password is required",
                    v => (v && v.length >= 8) || "Password length must be greater than 8",
                    v => /[A-Z]/.test(v) || "Password does not contain uppercase",
                    v => /[a-z]/.test(v) || "Password does not contain lowercase",
                    v => /[0-9]/.test(v) || "Password does not contain number",
                    v => /[!@#$%^&*]/.test(v) || "Password does not contain a special character",
                ],
                emailRules: [
                    v => !!v || 'E-mail is required',
                    //  v => /.+@.+/.test(v) || 'E-mail must be valid',
                    v =>
                        !v ||
                        /^\w+([.-]?\w+)*@\w+([.-]?\w+)*(\.\w{2,3})+$/.test(v) ||
                        "E-mail must be valid",
                ],
            },
        }),
        method: {
            submitRegistration(){
                this.loading=true;
                this.showErrorSnack=true;
                // redirect
                //this.$router.push("/item");
                this.$router.push('/dashboard')
            },
        },
        watch: {
            loading() {
                setTimeout(() => (this.loading = false), 3000);
            },
            "registartionForm.password": function () {
                console.log("called here");
                if (this.registrationForm.password.length > 6) {
                    this.passwordLengthvalid = true;
                } else {
                    this.passwordLengthvalid = false;
                }
            }
        },
    };
</script>

<style>

</style>