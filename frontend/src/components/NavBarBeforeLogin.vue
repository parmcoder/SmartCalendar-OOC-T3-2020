<template>
    <nav>
        <v-toolbar flat app color="orange accent-3" dense dark>
            <v-btn to="/register" flat color="grey darken-2">
                <span>SIGN UP</span>
                <v-icon right>accessibility</v-icon>
            </v-btn>
            <v-spacer></v-spacer>
            <v-btn to="/login" flat color="white--text">
                <span>SIGN IN</span>
                <v-icon right>login</v-icon>
            </v-btn>
        </v-toolbar>

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