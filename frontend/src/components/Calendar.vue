<template>
    <div id="calendar">
        <v-app id="inspire">
            <v-sheet height="64">
                <v-toolbar flat color="orange accent-3">
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


<!--                    showing image of calendar-->
                    <v-img
                            :src="require('../assets/calendar_5-512.png')"
                            contain
                            height="70"
                    >
                    </v-img>
                    <v-spacer></v-spacer>

                    <v-btn color="grey darken-2" dark @click="addInfo = true"> New Event</v-btn>

                </v-toolbar>
            </v-sheet>

            <v-dialog v-model="addInfo" max-width="1000">
                <v-card color="grey" dark>
                    <component>
                        <v-form @submit.prevent="addEvent">
                            <v-text-field v-model="name" type="text" label="event name (required)"></v-text-field>
                            <v-text-field v-model="details" type="text" label="detail"></v-text-field>
<!--                            I want to filter date string, let me choose the v-model -->
                            <v-datetime-picker v-model="start" type="datetime" label="start (required)"></v-datetime-picker>
                            <v-datetime-picker v-model="end" type="datetime" label="end (required)"></v-datetime-picker>
                            <v-text-field v-model="color" type="color" label="color (click to open color menu)"></v-text-field>
                            <v-btn color="orange accent-3" class="mr-4" @click.stop="addInfo = false">
                                create event
                            </v-btn>
                        </v-form>
                    </component>
                </v-card>
            </v-dialog>


            <v-sheet height="760">
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
                        @change="updateRange"
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
                            <v-toolbar-title v-html="selectedEvent.name"></v-toolbar-title>
                            <v-spacer></v-spacer>
                        </v-toolbar>
                        <v-card-text>
                            <span v-html="selectedEvent.details"></span>
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
    export default {
        name: 'Calendar',

        data: () => ({
            focus: '',
            type: 'month',
            typeToLabel: {
                month: 'Month',
                week: 'Week',
                day: 'Day',
                '4day': '4 Days',
            },

            name: null,
            details: null,
            start: null,
            end: null,
            color: '#1976D2', //default color
            addInfo: false,

            selectedEvent: {},
            selectedElement: null,
            selectedOpen: false,
            events: [],
            colors: ['blue', 'indigo', 'deep-purple', 'cyan', 'green', 'orange', 'grey darken-1'],
            names: ['Meeting', 'Holiday', 'PTO', 'Travel', 'Event', 'Birthday', 'Conference', 'Party'],
        }),

        mounted () {
            this.$refs.calendar.checkChange()
        },

        methods: {

            addEvent({date, event}){
                const open = () => {
                    this.selectedEvent = event
                    this.selectedElement = date.target
                    setTimeout(() => this.selectedOpen = true, 1)
                }
                if (this.selectedOpen) {
                    this.selectedOpen = false
                    setTimeout(open, 1)
                } else {
                    open()
                }

                date.stopPropagation()
            },

            viewDay ({ date }) {
                this.focus = date
                this.type = ''
            },

            getEventColor (event) {
                return event.color
            },
            setToday () {
                this.focus = ''
            },
            prev () {
                this.$refs.calendar.prev()
            },
            next () {
                this.$refs.calendar.next()
            },

            showEvent ({ nativeEvent, event }) {
                const open = () => {
                    this.selectedEvent = event
                    this.selectedElement = nativeEvent.target
                    setTimeout(() => this.selectedOpen = true, 10)
                }
                if (this.selectedOpen) {
                    this.selectedOpen = false
                    setTimeout(open, 10)
                } else {
                    open()
                }
                nativeEvent.stopPropagation()
            },

            updateRange ({ start, end }) {
                const events = []
                const min = new Date(`${start.date}T00:00:00`)
                const max = new Date(`${end.date}T23:59:59`)
                const days = (max.getTime() - min.getTime()) / 86400000
                const eventCount = this.rnd(days, days + 20)
                for (let i = 0; i < eventCount; i++) {
                    const allDay = this.rnd(0, 3) === 0
                    const firstTimestamp = this.rnd(min.getTime(), max.getTime())
                    const first = new Date(firstTimestamp - (firstTimestamp % 900000))
                    const secondTimestamp = this.rnd(2, allDay ? 288 : 8) * 900000
                    const second = new Date(first.getTime() + secondTimestamp)
                    events.push({
                        name: this.names[this.rnd(0, this.names.length - 1)],
                        start: first,
                        end: second,
                        color: this.colors[this.rnd(0, this.colors.length - 1)],
                        timed: !allDay,
                    })
                }
                this.events = events
            },
            rnd (a, b) {
                return Math.floor((b - a + 1) * Math.random()) + a
            },
        },
    }
</script>