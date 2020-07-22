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
        return axios
            .get(API_URL + 'tasklist/' + user, {headers: authHeader()});
    }

    /**
     * Post request function will return message if success, use username to track user
     * @param user
     * @param task
     * @returns {Promise<AxiosResponse<T>>}
     */
    postCreateTask(user, task) {
        return axios
            .post(API_URL + user + '/' + task.title + '/' + task.description + '/' + task.dateStart + '/' + task.dateEnd, {headers: authHeader()});

    }

    /**
     * Post request function will return message if success, only need an ID from
     * task to remove task
     * @param task
     * @returns {Promise<AxiosResponse<T>>}

     */
    postRemoveTask(task) {
        return axios
            .post(API_URL + task.tid, {headers: authHeader()});

    }

    /**
     * Post request function will return message if success, use task id to track user
     * @param user
     * @returns {Promise<AxiosResponse<T>>}
     */
    postEditTask(task) {
        return axios
            .post(API_URL + task.tid + '/' + task.title + '/' + task.description + '/' + task.dateStart + '/' + task.dateEnd, {headers: authHeader()});

    }
}

export default new UserService();
