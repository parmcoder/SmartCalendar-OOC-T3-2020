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
    },
        /*
        TODO: adding users
         */


    /*
    * A function for creating user
     */
    createUser(username, password, firstName, lastName) {
        return AXIOS.post(`/auth/signup/`,{
            username: username,
            password: password,
            name: firstName,
            surname: lastName
        });
    },
    loginUser(user, password) {
        return AXIOS.post(`/auth/signin/`,{
            username: username,
            password: password
        });
    }


        /*
        TODO: calendar stuffs
         */

}


