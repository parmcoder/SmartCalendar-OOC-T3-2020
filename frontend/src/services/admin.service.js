import axios from 'axios';
import authHeader from './auth-header';

const API_URL = 'http://localhost:8080/api/admin/';

class AdminService {
    /**
     * get request function will return a list of users
     * @param user
     * @returns {Promise<AxiosResponse<T>>}
     */
    getUserlist() {
        return axios.get(API_URL + 'userlist', {headers: authHeader()});
    }

    /**
     * Post request function will return message if success, use username to remove user
     * @param user
     * @returns {Promise<AxiosResponse<T>>}
     */
    postRemoveUser(user) {
        return axios.get(API_URL + 'remove' + '/' + user, {headers: authHeader()});
    }
}

export default new AdminService();