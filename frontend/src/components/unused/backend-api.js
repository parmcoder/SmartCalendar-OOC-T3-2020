import axios from 'axios'
/*
! Move to services
 */
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
    },

    /*
    TODO: calendar stuffs
     */
    createTask(user, title, description, dateStart, dateEnd) {
        return AXIOS.post(`/user/create/`,{
            headers: {
                'Authorization': 'test-value'
            }
        })
    },
    removeTask(user, password) {
    },
    editTask(user, password) {
    },
    getTaskList(user, password) {
        return AXIOS.get(`/user/tasklist/`+user,{
            headers: {
                'Authorization': 'test-value'
            }
        })
    }


}


