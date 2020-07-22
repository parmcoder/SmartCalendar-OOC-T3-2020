<template xmlns:v-slot="http://www.w3.org/1999/XSL/Transform">
    <div id="admin">
        <v-app id="inspire">
            <v-data-table
                    height="85vh"
                    fixed-header
                    :headers="headers"
                    :items="UserInfo"
                    sort-by="calories"
                    class="elevation-1"
                    style="width: 100%"
                    dark
            ><template v-slot:top>
                    <v-toolbar flat color="orange">
                        <v-toolbar-title>Admin Control Page</v-toolbar-title>
                        <v-divider
                                class="mx-4"
                                inset
                                vertical
                        ></v-divider>
                        <v-spacer></v-spacer>

                    </v-toolbar>
                </template>
                <template v-slot:item.actions="{ item }">
                    <v-icon
                            small
                            @click="deleteUser(item)"
                            class="ml-3"
                    >
                        mdi-delete
                    </v-icon>
                </template>
                <template v-slot:no-data>
                    <v-btn color="primary" @click="initialize">Reset</v-btn>
                </template>
            </v-data-table>
        </v-app>
    </div>
</template>

<!--TODO: We need to work on retriving the data from backend using axios method-->

<script>
    export default {
        name: 'admin',

        data: () => ({
            dialog: false,
            headers: [
                {
                    text: 'ID ',
                    align: 'start',
                    sortable: false,
                    value: 'id',
                },
                { text: 'Username', value: 'username' },
                { text: 'Name', value: 'name' },
                { text: 'Surname', value: 'surname', sortable: false },
                { text: 'Action', value: 'actions', sortable: false },


            ],
            UserInfo: [],

            defaultItem: {
                id: 0,
                username: '',
                password: '',
                cf: null,
                name: '',
                surname: '',
            },
        }),

        watch: {
            dialog (val) {
                val || this.close()
            },
        },

        created () {
            this.initialize()
        },

        methods: {
            initialize () {
                this.UserInfo = [
                    {
                        id: 1,
                        username: "adminparm",
                        password: "$2a$10$PrI5Gk9L.tSZiW9FXhTS8O8Mz9E97k2FZbFvGFFaSsiTUIl.TCrFu",
                        confirmPassword: null,
                        name: "boi",
                        surname: "boi"

                    },
                    {
                        id: 3,
                        username: "parmmy",
                        password: "$2a$10$t/P8OJN8GI7sdpjrbK1WROaELBpLadqEbH1m8Ja4wJbNZKa4TaQLm",
                        confirmPassword: null,
                        name: null,
                        surname: null

                    },
                ]
            },

            deleteUser (item) {
                const index = this.UserInfo.indexOf(item)
                confirm('Are you sure you want to delete this item?') && this.UserInfo.splice(index, 1)
            },
        },
    }
</script>