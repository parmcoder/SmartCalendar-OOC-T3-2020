<template>
    <div id="register">
        <link href="https://fonts.googleapis.com/css?family=Material+Icons" rel="stylesheet">

        <v-toolbar color="orange accent-3" dense dark>
            <v-icon> face </v-icon>
            <v-toolbar-title class="ml-3"> Registration page</v-toolbar-title>
        </v-toolbar>

        <v-app class="theme--dark" >
            <v-container grid-list-xl text-xs-center>
                <v-layout row wrap>
                    <v-flex xs4 offset-xs4>
                        <v-card class="mt-15" dark color="dark" elevation-24 style="padding: 20px; border: 1px; border-radius: 50px;">
                            <v-card-text>
                                <h4 class="display-2 font-weight-bold">Register</h4>
                                <hr>
                                <br>
                                <v-form v-model="isValid">
                                    <v-text-field
                                            v-model="user.name"
                                            label="Real name"
                                            :rules="inputRules"
                                            required
                                            prepend-icon="face"
                                            type="text"
                                            color="white"
                                    >
                                    </v-text-field>
                                    <v-text-field
                                            v-model="user.surname"
                                            label="Surname"
                                            :rules="inputRules"
                                            required
                                            prepend-icon="face"
                                            type="text"
                                            color="white"
                                    >
                                    </v-text-field>
                                    <v-text-field
                                            v-model="user.username"
                                            label="Username"
                                            :rules="inputRules"
                                            required
                                            prepend-icon="face"
                                            type="text"
                                            color="white"
                                    >
                                    </v-text-field>
                                    <v-text-field
                                            v-model="user.password"
                                            label="Password"
                                            :rules="inputRules"
                                            required
                                            prepend-icon="lock"
                                            type="password"
                                            color="white"
                                    >
                                    </v-text-field>

                                <br>
                                <div class="text-xl-center">
                                    <v-btn @click="handleRegister" color="grey darken-2" :disabled="!isValid">
                                        Register
                                    </v-btn>
                                </div>
                                <br>
                                <div v-if="message" class="alert alert-danger" role="alert">{{message}}</div>
                                </v-form>
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
        name: 'Register',
        data() {
            return {
                user: new User('', '', '', ''),
                submitted: false,
                successful: false,
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
        mounted() {
            if (this.loggedIn) {
                this.$router.push('/calendar');
            }
        },
        methods: {
            handleRegister() {
                this.message = '';
                this.submitted = true;
                this.$store.dispatch('auth/register', this.user).then(
                    data => {
                        this.message = data.message;
                        this.successful = true;
                    },
                    error => {
                        this.message = error.message;
                        this.successful = false;
                    }
                );
            }
        }
    };
</script>
