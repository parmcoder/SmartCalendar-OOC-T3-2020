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
                                <form name="form" @submit.prevent="handleLogin">
                                    <div class="form-group">
                                        <label for="username">Username</label>
                                        <input
                                                v-model="user.username"
                                                v-validate="'required'"
                                                type="text"
                                                class="form-control"
                                                name="username"
                                                color="white"
                                        />
                                        <div
                                                v-if="errors.has('username')"
                                                class="alert alert-danger"
                                                role="alert"
                                        >Username is required!</div>
                                    </div>
                                    <div class="form-group">
                                        <label for="password">Password</label>
                                        <input
                                                v-model="user.password"
                                                v-validate="'required'"
                                                type="password"
                                                class="form-control"
                                                name="password"
                                                color="white"
                                        />
                                        <div
                                                v-if="errors.has('password')"
                                                class="alert alert-danger"
                                                role="alert"
                                        >Password is required!</div>
                                    </div>
                                    <div class="form-group">
                                        <button class="btn btn-primary btn-block" color="grey darken-2" :disabled="loading">
                                            <span v-show="loading" class="spinner-border spinner-border-sm"></span>
                                            <span>Login</span>
                                        </button>
                                    </div>
                                    <div class="form-group">
                                        <div v-if="message" class="alert alert-danger" role="alert">{{message}}</div>
                                    </div>
                                </form>

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
    import User from '../models/user';

    export default {
        name: 'Login',
        data() {
            return {
                user: new User('', '', '', ''),
                loading: false,
                message: ''
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
                this.$validator.validateAll().then(isValid => {
                    if (!isValid) {
                        this.loading = false;
                        return;
                    }

                    if (this.user.username && this.user.password) {
                        this.$store.dispatch('auth/login', this.user).then(
                            () => {
                                this.$router.push('/calendar');
                            },
                            error => {
                                this.loading = false;
                                this.message =
                                    (error.response && error.response.data) ||
                                    error.message ||
                                    error.toString();
                            }
                        );
                    }
                });
            }
        }
    };
</script>
