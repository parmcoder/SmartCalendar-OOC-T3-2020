<template>
    <div id="calendar">
        <v-app id="inspire">
            <v-sheet height="64">

                <v-toolbar flat color="orange accent-3">
                    <!--                    showing image of calendar-->
<!--                    <v-img-->
<!--                            :src="require('../assets/calendar_5-512.png')"-->
<!--                            contain-->
<!--                            left-->
<!--                            height="50"-->
<!--                    >-->
<!--                    </v-img>-->
                    <!-- today bottom, when you click it will go back to today -->
                    <v-btn outlined class="mr-4" color="grey darken-2" @click="setToday">
                        Today
                    </v-btn>

                    <!-- these 2 bottom go to previous and next month-->
                    <v-btn fab text small color="grey darken-2" @click="prev">
                        <v-icon small>mdi-chevron-left</v-icon>
                    </v-btn>

                    <v-btn fab text small color="grey darken-2" @click="next">
                        <v-icon small>mdi-chevron-right</v-icon>
                    </v-btn>

<!--                    showing the month and year from calendar-->
                    <v-toolbar-title v-if="$refs.calendar">
                        {{ $refs.calendar.title}}
                    </v-toolbar-title>
                    <v-spacer></v-spacer>
                    <v-btn color="grey darken-2" dark @click="addInfo = true"> New Event</v-btn>
                    <v-spacer></v-spacer>
                    <v-spacer></v-spacer>
                    <v-spacer></v-spacer>
                    <v-spacer></v-spacer>
                    <v-spacer></v-spacer>
                    <v-spacer></v-spacer>
                    <v-spacer></v-spacer>
                    <v-spacer></v-spacer>
                    <v-spacer></v-spacer>
                    <v-spacer></v-spacer>
                    <v-spacer></v-spacer>
                    <v-spacer></v-spacer>
                    <v-btn color="grey darken-2" dark @click="handleLogout">
                        <span>SIGN OUT</span>
                        <v-icon right>logout</v-icon></v-btn>
                </v-toolbar>
            </v-sheet>

            <v-dialog v-model="addInfo" max-width="50%">
                <v-card :class="ma-10" color="grey" dark>
                    <component>
                        <v-form ref="form" @submit.prevent="addEvent" v-model="isValid">
                            <v-text-field
                                    :rules="inputRules"
                                    required
                                    v-model="task.title"
                                    type="text"
                                    label=" Event name (Required) "></v-text-field>
                            <v-text-field
                                    v-model="task.description"
                                    type="text"
                                    label=" Description"></v-text-field>
<!--                            I want to filter date string, let me choose the v-model -->

<!--                            <datetime type="datetime" v-model="datetime"></datetime>-->
                            <v-datetime-picker
                                    :rules="inputRules"
                                    required
                                    v-model="task.dateStart"
                                    label=" Start date (Required) ">
                                <template slot="dateIcon">
                                    <v-icon>calendar_today</v-icon>
                                </template>
                                <template slot="timeIcon">
                                    <v-icon>access_time</v-icon>
                                </template>
                            </v-datetime-picker>
                            <v-datetime-picker
                                    :rules="inputRules"
                                    required
                                    v-model="task.dateEnd"
                                    label=" End date (Required) ">
                                <template slot="dateIcon">
                                    <v-icon>calendar_today</v-icon>
                                </template>
                                <template slot="timeIcon">
                                    <v-icon>access_time</v-icon>
                                </template>
                            </v-datetime-picker>
                            <v-text-field
                                    :rules="inputRules"
                                    required
                                    v-model="color"
                                    type="color"
                                    label=" Color (click to choose color) "></v-text-field>
                            <div class="text-xl-center">
                            <v-btn color="orange accent-3" class="mr-4" @click.stop="addInfo = false">
                                create event
                            </v-btn>
                                <v-btn
                                        color="error"
                                        class="mr-4"
                                        @click="reset"
                                >
                                    Reset Form
                                </v-btn>
                            </div>
                        </v-form>
                    </component>
                </v-card>
            </v-dialog>


            <v-sheet height="100%">
                <v-calendar
                        ref="calendar"
                        v-model="focus"
                        color="primary"
                        :events="events"
                        :event-color="getEventColor"
                        :type="type"
                        @click:event="showEvent"
                        @click:more="viewDay"
                        @click:date="viewDay"
                        @change="initialize"
                        dark
                >
                </v-calendar>
                <v-menu
                 v-model="selectedOpen"
                 :close-on-content-click="false"
                 :activator="selectedElement"
                 offset-x
                >
                <v-menu
                        v-model="selectedOpen"
                        :close-on-content-click="false"
                        :activator="selectedElement"
                        offset-x

                >
                    <v-card
                            color="grey lighten-4"
                            min-width="350px"
                            flat
                    >
                        <v-toolbar
                                :color="selectedEvent.color"
                                dark
                        >
                            <v-btn icon>
                                <v-icon>mdi-pencil</v-icon>
                            </v-btn>
                            <v-toolbar-title v-html="selectedEvent.title"></v-toolbar-title>
                            <v-spacer></v-spacer>
                        </v-toolbar>
                        <v-card-text>
                            <span v-html="selectedEvent.description"></span>
                        </v-card-text>
                        <v-card-actions>
                            <v-btn
                                    text
                                    color="secondary"
                                    @click="selectedOpen = false"
                            >
                                Cancel
                            </v-btn>
                        </v-card-actions>
                    </v-card>
                </v-menu>
                </v-menu>
            </v-sheet>
        </v-app>
    </div>
</template>


<script>
    import UserService from '../services/user.service'
    import task from '../models/task';

    export default {
        name: 'Calendar',

        data: () => ({
            task: new task('', '', '', '', ''),

            focus: '',
            type: 'month',

            tid: null,
            title: null,
            description: null,
            dateStart: null,
            dateEnd: null,
            color: '#1976D2', //default color
            addInfo: false,

            selectedEvent: {},
            selectedElement: null,
            selectedOpen: false,
            events: [],
            colors: ['blue', 'indigo', 'deep-purple', 'cyan', 'green', 'orange', 'grey darken-1'],
            names: ['Meeting', 'Holiday', 'PTO', 'Travel', 'Event', 'Birthday', 'Conference', 'Party'],

            inputRules: [v => !!v || 'This field is required'],
            isValid:true

        }),

        mounted () {
            this.$refs.calendar.checkChange()
        },

        methods: {

            addEvent(){
                this.$store.dispatch('')

            },

            viewDay ({ date }) {
                this.focus = date;
                this.type = '';
            },

            getEventColor (event) {
                return event.color;
            },
            setToday () {
                this.focus = '';
            },
            prev () {
                this.$refs.calendar.prev();
            },
            next () {
                this.$refs.calendar.next();
            },

            showEvent ({ nativeEvent, event }) {
                const open = () => {
                    this.selectedEvent = event;
                    this.selectedElement = nativeEvent.target;
                    setTimeout(() => this.selectedOpen = true, 10);
                };
                if (this.selectedOpen) {
                    this.selectedOpen = false;
                    setTimeout(open, 10);
                } else {
                    open();
                }
                nativeEvent.stopPropagation();
            },

            created () {
                this.initialize()
            },

            initialize() {
                const events = [];
                console.log(this.$store.state.auth.user);
                UserService.getTaskList(this.$store.state.auth.user).then(
                    taskList => {
                        const taskArr = taskList.data;

                        taskArr.forEach(
                            task => {
                                console.log(task);
                                events.push({
                                    tid: task.tid,
                                    title: task.title,
                                    description: task.description,
                                    dateStart: (task.dateStart).substring(0,19),
                                    dateEnd: (task.dateEnd).substring(0,19),
                                    color: this.colors[this.rnd(0, this.colors.length - 1)],
                                });
                            }
                        )

                    },error => {
                        console.log(error);
                    }
                );
                this.events = events;
            },
            rnd (a, b) {
                return Math.floor((b - a + 1) * Math.random()) + a;
            },
            handleLogout() {
                this.$store.dispatch('auth/logout').then(
                    () => {
                        this.$router.push('/');
                    }
                );
            }
        },
    }
</script>
