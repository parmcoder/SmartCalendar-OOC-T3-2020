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
                                <h4 class="display-2 font-weight-bold">Log in</h4>
                                <hr>
                                <br>
                                <v-form v-model="isValid">
                                        <v-text-field
                                                label="Username"
                                                v-model="user.username"
                                                :rules="inputRules"
                                                required
                                                prepend-icon="face"
                                                type="text"
                                                color="white"
                                        />
                                        <v-text-field
                                                label="Password"
                                                v-model="user.password"
                                                :rules="inputRules"
                                                required
                                                prepend-icon="lock"
                                                type="password"
                                                color="white"
                                        />
                                    <br>

                                        <div class="text-xl-center">
                                        <v-btn @click="handleLogin" color="grey darken-2" :disabled="!isValid" :loading="loading">
                                            Login
                                        </v-btn>
                                        </div>
                                    <br>
                                        <div v-if="message" class="alert alert-danger" role="alert">{{message}}</div>
                                </v-form>
                                <br>
                            </v-card-text>
                        </v-card>
                    </v-flex>
                </v-layout>
            </v-container>
        </v-app>
    </div>
</template>

<script>
    import user from '../models/user';

    export default {
        name: 'Login',
        data() {
            return {
                user: new user('', '', '', ''),
                loading: false,
                message: '',
                inputRules: [v => !!v || 'This field is required'],
                isValid: true
            };
        },
        computed: {
            loggedIn() {
                return this.$store.state.auth.status.loggedIn;
            }
        },
        created() {
            if (this.loggedIn) {
                this.$router.push('/calendar');
            }
        },
        methods: {
            handleLogin() {
                this.loading = true;
                if (this.user.username && this.user.password) {
                    this.$store.dispatch('auth/login', this.user).then(
                        () => {
                            this.$router.push('/calendar');
                        },
                        error => {
                            this.loading = false;
                            if (error.response.status === 401) {
                                this.message = "Wrong username or password";
                            }
                        }
                    );
                }
            }
        }
    };
</script>
