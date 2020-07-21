import axios from 'axios';
import authHeader from './auth-header';

const API_URL = 'http://localhost:8080/api/user/';

class UserService {
    /**
     * Get request function will return a list of tasks
     * @param user
     * @returns {Promise<AxiosResponse<T>>}
     */
    getTaskList(user) {
        return axios.get(API_URL + 'tasklist/' + user, {headers: authHeader()});
    }

    /**
     * Post request function will return message if success, use username to track user
     * @param user
     * @returns {Promise<AxiosResponse<T>>}
     */
    postCreateTask(user, title, description, dateStart, dateEnd) {
        return axios.get(API_URL + user + '/' + title + '/' + description + '/' + dateStart + '/' + dateEnd, {headers: authHeader()});
    }

    /**
     * Post request function will return message if success, only need an ID from
     * task to remove task
     * @param user
     * @returns {Promise<AxiosResponse<T>>}
     */
    postRemoveTask(tid) {
        return axios.get(API_URL + tid, {headers: authHeader()});
    }

    /**
     * Post request function will return message if success, use task id to track user
     * @param user
     * @returns {Promise<AxiosResponse<T>>}
     */
    postEditTask(tid, title, description, dateStart, dateEnd) {
        return axios.get(API_URL + + tid + '/' + title + '/' + description + '/' + dateStart + '/' + dateEnd, {headers: authHeader()});
    }
}

export default new UserService();