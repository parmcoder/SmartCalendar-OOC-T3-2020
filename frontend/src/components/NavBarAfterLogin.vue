<template>
    <nav>
        <v-toolbar flat app color="orange accent-3" dense dark>
            <v-btn @click="drawer = !drawer" class="grey--text"> <v-icon> home </v-icon></v-btn>
            <v-spacer></v-spacer>
            <v-spacer></v-spacer>
            <v-btn flat color="grey">
                <span>Sign Out</span>
                <v-icon right>exit_to_app</v-icon>
            </v-btn>
        </v-toolbar>

        <v-navigation-drawer app v-model="drawer" class="orange">
            <p>test</p>
        </v-navigation-drawer>

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