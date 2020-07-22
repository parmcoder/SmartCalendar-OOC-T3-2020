import axios from 'axios';

const API_URL = 'http://localhost:8080/api/auth/';

class AuthService {
    username;

    login(user) {
        return axios
            .post(API_URL + 'signin', {
                username: user.username,
                password: user.password
            }).then(res=>console.log(res))
            .then(response => {
                if (response.data.token) {
                    localStorage.setItem('user', JSON.stringify(response.data));
                }
                return response.data.userInfo.username;
            });
    }

    logout() {
        localStorage.removeItem('user');
    }

    register(user) {
        return axios.post(API_URL + 'signup', {
            username: user.username,
            password: user.password,
            name: user.name,
            surname: user.surname
        }).then(res=>console.log(res));
    }
}

export default new AuthService();
