<template>
    <nav>
        <v-app-bar flat app color="orange accent-3" dense dark>
            <v-btn to="/register" color="grey darken-2">
                <span>SIGN UP</span>
                <v-icon right>accessibility</v-icon>
            </v-btn>
            <v-spacer></v-spacer>
            <v-btn to="/login" color="white--text">
                <span>SIGN IN</span>
                <v-icon right>login</v-icon>
            </v-btn>
        </v-app-bar>

    </nav>
</template>

<script>
    export default {
        data() {
            return {
                drawer: false
            }
        }
    }
</script>

<style>
</style>
