<template>
    <v-main>
        <v-card color="secondary">
            <v-row>
                <v-col cols="6">Image goes here</v-col>
                <v-col  cols="5" class="py-10 my-auto">
                    <v-card style="height: 80vh" rounded="0"  class="justify-center py-15" elevation="3" >
                        <v-card-title class="justify-center">Register</v-card-title>
                        <v-card-text>
                            <v-card class="px-16" elevation="1" >
                                <v-form class="px-16">
                                    <v-card-text>
                                        <v-text-field
                                            label="Email"
                                            v-model="registrationForm.email"
                                            :rules="registrationValidate.emailRules"
                                        />
                                        {{registrationForm.email}}
                                        <v-text-field
                                            label="Password"
                                            type="password"
                                            @click="showPasswordValidations=true"
                                            v-model="registrationForm.password"
                                            :rules="registrationValidate.passwordRules"
                                        />
                                        {{registrationForm.password}}
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
                                    </v-card-text>
                                    <v-card-title class="justify-center">
                                        <v-btn 
                                        class="success" 
                                        elevation="4" 
                                        @click="loader = 'loading'"
                                        >Register</v-btn>
                                    </v-card-title>
                                </v-form>
                            </v-card>
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
            passwordLengthvalid: false,
            showPasswordValidations: false,
            registrationForm:{
                email:"",
                password:"",
            },
            registrationValidate:{
                passwordRules: [
                    (v) => !!v || "Password is required",
                    (v) => (v && v.length >= 8) || "Password length must be greater than 8",
                    (v) => /[A-Z]/.test(v) || "Password does not contain uppercase",
                    (v) => /[a-z]/.test(v) || "Password does not contain lowercase",
                    (v) => /[0-9]/.test(v) || "Password does not contain number",
                    (v) => /[!@#$%^&*]/.test(v) || "Password does not contain a special character",
                ],
                emailRules: [
                    (v) => !!v || "Email is required",
                    //  v => /.+@.+/.test(v) || 'E-mail must be valid',
                    (v) =>
                        !v ||
                        /^\w+([.-]?\w+)*@\w+([.-]?\w+)*(\.\w{2,3})+$/.test(v) ||
                        "E-mail must be valid",
                ],
            },
        }),
        /*watch: {
            "registartionForm.password": function () {
                console.log("called here");
                if (this.registrationForm.password.length > 6) {
                    this.passwordLengthvalid = true;
                } else {
                    this.passwordLengthvalid = false;
                }
            }
        },*/
    };
</script>

<style>

</style>