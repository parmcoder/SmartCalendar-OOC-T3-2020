import Vue from 'vue'
import Router from 'vue-router'
import User from './components/unused/User'
import Login from "./components/Login";
import Protected from './components/unused/Protected'
import Index from "./components/Index";
import Register from "./components/Registration"
import Calendar from "./components/Calendar"
import BasicDatetime from "./components/BasicDatetime"

import Admin from "./components/Admin";

Vue.use(Router);

export const router = new Router({
    mode: 'history', // uris without hashes #, see https://router.vuejs.org/guide/essentials/history-mode.html#html5-history-mode
    routes: [
        {
            path: '/',
            component: Index,
            meta: {title: 'Home Page - Squish Table'}
            },
        {
            path: '/testdatetime',
            component: BasicDatetime,
            meta: {title: 'Home Page - Squish Table'}
            },
        {
            path: '/calendar',
            component: Calendar,
            meta: {title: 'Calendar Page - Squish Table'}
            },
        {
            path: '/admin',
            component: Admin,
            meta: {title: 'Admin Page - Squish Table'}
        },
        {path: '/user', component: User},
        {
            path: '/login',
            component: Login,
            meta: {title: 'Login Page - Squish Table'}
        },
        {
            path: '/register',
            component: Register,
            meta: {title: 'Register Page - Squish Table'}
        },
        {
            path: '/protected',
            component: Protected,
            meta: {title: 'Protected Page - Squish Table'}
        },

        // otherwise redirect to home
        {path: '*', redirect: '/'}
    ]
})


router.beforeEach((to, from, next) => {

    // This goes through the matched routes from last to first, finding the closest route with a title.
    // eg. if we have /some/deep/nested/route and /some, /deep, and /nested have titles, nested's will be chosen.
    const nearestWithTitle = to.matched.slice().reverse().find(r => r.meta && r.meta.title);


    // If a route with a title was found, set the document (page) title to that value.
    if(nearestWithTitle) document.title = nearestWithTitle.meta.title;


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
