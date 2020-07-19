import axios from 'axios'

const AXIOS = axios.create({
  baseURL: `/api`,
  timeout: 1000
});

/*
* Providing you guys good stuffs hehe
 */
export default {
    hello() {
        return AXIOS.get(`/hello`);

        /*
        TODO: adding users
         */

        /*
        TODO: calendar stuffs
         */


    /*
    ! This method should be changed soon
     */
    },
    getUser(userId) {
        return AXIOS.get(`/user/` + userId);
    },
    createUser(firstName, lastName) {
        return AXIOS.post(`/user/` + firstName + '/' + lastName);
    },
    getSecured(user, password) {
        return AXIOS.get(`/secured/`,{
            auth: {
                username: user,
                password: password
            }});
    }



}


