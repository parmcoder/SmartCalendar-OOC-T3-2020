<template>
    <div id="login">
        <link href="https://fonts.googleapis.com/css?family=Material+Icons" rel="stylesheet">

        <v-toolbar color="orange accent-3" dense dark>
            <v-icon> face </v-icon>
            <v-toolbar-title class="ml-3"> Log-in page</v-toolbar-title>
        </v-toolbar>

        <v-app class="theme--dark" >
            <v-container grid-list-xl text-xs-center>
                <v-layout row wrap>
                    <v-flex xs4 offset-xs4>
                        <v-card class="mt-15" dark color="dark" elevation-24 style="padding: 20px; border: 1px; border-radius: 50px;">
                            <v-card-text>
                                <h4 class="display-2 font-weight-bold">Login</h4>
                                <hr>
                                <br>
                                <v-form @submit.prevent="callLogin()">
                                    <v-text-field
                                            clearable
                                            v-model="user"
                                            label="Username"
                                            prepend-icon="face"
                                            type="text"
                                            color="white"
                                    >
                                    </v-text-field>
                                    <v-text-field
                                            clearable
                                            v-model="password"
                                            label="Password"
                                            prepend-icon="lock"
                                            type="password"
                                            color="white"
                                    >
                                    </v-text-field>
                                    <br>
                                    <v-btn variant="success" type="submit" color="grey darken-2" large>Enter</v-btn>
                                </v-form>
<<<<<<< HEAD
                                <br>
                                <div class="text-xl-center">
                                    <v-btn color="grey darken-2" large>Enter</v-btn>
                                </div>
=======
>>>>>>> master
                            </v-card-text>
                        </v-card>
                    </v-flex>
                </v-layout>
            </v-container>
        </v-app>
    </div>
</template>

<script>
    export default {
        name: 'login',

        data () {
            return {
                loginError: false,
                user: '',
                password: '',
                error: false,
                errors: []
            }
        },
        methods: {
            callLogin() {
                this.errors = [];
                this.$store.dispatch("login", { user: this.user, password: this.password})
                    .then(() => {
                        this.$router.push('/Admin')
                    })
                    .catch(error => {
                        this.loginError = true;
                        this.errors.push(error);
                        this.error = true
                    })
            }
        }
    }
</script>