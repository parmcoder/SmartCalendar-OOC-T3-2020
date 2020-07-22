import Vue from 'vue';
import App from './App.vue';
import { router } from './router';
import store from './stores';
import 'bootstrap';
import 'bootstrap/dist/css/bootstrap.min.css';
import vuetify from './plugins/vuetify';
import DatetimePicker from "vuetify-datetime-picker";

Vue.config.productionTip = false;

Vue.use(DatetimePicker);

new Vue({
    router,
    store,
    vuetify,
    render: h => h(App)
}).$mount('#app');

