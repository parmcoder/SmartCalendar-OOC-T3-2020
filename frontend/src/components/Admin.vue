<template>
    <div class="index">
        <v-app class="theme--dark">
            <v-row class="text-center">
                <v-col cols="12">
                    <v-img
                            :src="require('../assets/calendar_5-512.png')"
                            class="my-3"
                            contain
                            height="200"
                    />
                </v-col>

                <v-col class="mb-4">
                    <h1 class="display-2 font-weight-bold mb-3">
                        Welcome to Admin Page!
                    </h1>
                </v-col>

                <v-col
                        class="mb-5"
                        cols="12"
                >
                </v-col>

                <v-col
                        class="mb-5"
                        cols="12"
                >
                </v-col>

                <v-col
                        class="mb-5"
                        cols="12"
                >
                </v-col>
            </v-row>
        </v-app>
    </div>
</template>

<script>
    export default {
        name: "Admin.vue"
    }
</script>

<style scoped>

</style>