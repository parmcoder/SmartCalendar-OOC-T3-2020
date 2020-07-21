import Vue from 'vue';
import App from './App.vue';
import { router } from './router';
import store from './stores';
import 'bootstrap';
import 'bootstrap/dist/css/bootstrap.min.css';
import VeeValidate from 'vee-validate';
import vuetify from './plugins/vuetify';

Vue.config.productionTip = false;

Vue.use(VeeValidate);

new Vue({
    router,
    store,
    vuetify,
    render: h => h(App)
}).$mount('#app');

