import axios from 'axios'
import { ERROR_401_MESSAGE, ERROR_CONN_MESSAGE } from '../Error';
const AUTHENTICATED_USER = 'authenticatedUser'
const SESSION_TOKEN='session_token';
class AuthService {
    executeJwtAuthService(username, password) {
        return axios.post('http://localhost:8080/signin',
            { 
                username,
                password
            }
        ).then(response=>{
            this.registerSuccessfulLoginForJwt(username,response.data);
            return -1;
        }).catch(err=>{
            console.log(err);
            var _failCode = 2;
			if(err.message === ERROR_401_MESSAGE){
				_failCode = 0;
			}else if(err.message === ERROR_CONN_MESSAGE){
				_failCode = 1;
			}
            return _failCode;
        })
    }

    createJWTToken(token) {
        return 'Bearer ' + token;
    }

    registerSuccessfulLoginForJwt(username, token) {
        sessionStorage.setItem(AUTHENTICATED_USER, username)
        sessionStorage.setItem(SESSION_TOKEN,this.createJWTToken(token));
    }


    logout() {
        sessionStorage.removeItem(AUTHENTICATED_USER);
        sessionStorage.removeItem(SESSION_TOKEN);
    }

    getJWTToken(){
        return sessionStorage.getItem(SESSION_TOKEN);
    }
    isUserLoggedIn() {
        let user = sessionStorage.getItem(AUTHENTICATED_USER)
        if (user === null) return false
        return true
    }
    getLoggedInUser() {
        let user = sessionStorage.getItem(AUTHENTICATED_USER)
        if (user === null) return ''
        return user
    }

    setupAxiosInterceptors(token) {

        axios.interceptors.request.use(
            (config) => {
                if (this.isUserLoggedIn()) {
                    config.headers.authorization = token
                }
                return config
            }
        )
    }
}

export default new AuthService();