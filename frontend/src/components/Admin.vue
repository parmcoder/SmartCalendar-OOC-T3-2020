<template xmlns:v-slot="http://www.w3.org/1999/XSL/Transform">
    <div id="admin">
        <v-app id="inspire">
            <v-data-table
                    height="85vh"
                    fixed-header
                    :headers="headers"
                    :items="userList"
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
                        <v-btn color="grey darken-2" dark @click="handleCalendar">
                            <span>BACK TO CALENDAR</span>
                            <v-icon right>calendar_today</v-icon>
                        </v-btn>
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
    import AdminService from '../services/admin.service';

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
                { text: 'User ID', value: 'id' },
                { text: 'Username', value: 'username' },
                { text: 'Name', value: 'name' },
                { text: 'Surname', value: 'surname', sortable: false },
                { text: 'Action', value: 'actions', sortable: false },

            ],
            userList: [],

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
        computed: {
            username() {
                return this.$store.state.auth.user.username;
            }
        },
        methods: {
            initialize() {
                AdminService.getUserlist().then(
                    response => {
                        this.userList = response.data;
                        console.log(this.userList);
                    },
                    error => {
                        this.$router.push('/');
                        console.log(error);
                    }
                )
            },

            deleteUser (item) {
                const index = this.userList.indexOf(item)
                confirm('Are you sure you want to delete this item?') && this.userList.splice(index, 1)
                if(item.name != this.username) AdminService.postRemoveUser(item.username).then(
                    response => {
                        console.log(response.data);
                    },
                    error => {
                        console.log(error);
                    }
                )
            },
            handleCalendar() {
                this.$router.push('/calendar');
            }
        },
    }
</script>