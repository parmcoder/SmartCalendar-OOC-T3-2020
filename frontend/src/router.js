import Vue from 'vue'
import Router from 'vue-router'
import Hello from '@/components/unused/Hello'
import Bootstrap from '@/components/unused/Bootstrap'
import User from '@/components/unused/User'
import Login from "@/components/Login";
import Protected from '@/components/unused/Protected'
import Index from "./components/Index";
import Register from "./components/Registration"
import Calendar from "./components/Calendar"

import store from './stores/store'
import Admin from "./components/Admin";

Vue.use(Router);

const router = new Router({
    mode: 'history', // uris without hashes #, see https://router.vuejs.org/guide/essentials/history-mode.html#html5-history-mode
    routes: [
        {path: '/', component: Index},
        {path: '/hello', component: Hello},
        {path: '/callservice', component: Service},
        {path: '/bootstrap', component: Bootstrap},
        {path: '/user', component: User},
        {path: '/calendar', component: Calendar},

    });
//         {
//             path: '/admin',
//             component: Admin,
//             meta: {
//                 requiresAuth: true
//             }
//         },
//         {
//             path: '/login', component: Login,
//             meta: {
//                 hideForAuth: true
//             }
//         },
//         {
//             path: '/register', component: Register,
//             meta: {
//                 hideForAuth: true
//             }
//         },
//         {
//             path: '/protected',
//             component: Protected,
//             meta: {
//                 requiresAuth: true
//             }
//         },
//
//         // otherwise redirect to home
//         {path: '*', redirect: '/'}
//     ]
// })


router.beforeEach((to, from, next) => {
    if (to.matched.some(record => record.meta.requiresAuth)) {
        // this route requires auth, check if logged in
        // if not, redirect to login page.
        if (!store.getters.isLoggedIn) {
            next({
                path: '/login'
            })
        } else {
            next();
        }

    } else {
        next(); // make sure to always call next()!
    }
    if (to.matched.some(record => record.meta.hideForAuth)) {
        if (store.getters.isLoggedIn) {
            next({path: '/'});
        } else {
            next();
        }
    } else {
        next();
    }
});

export default router;