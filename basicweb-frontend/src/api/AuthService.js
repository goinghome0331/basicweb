import axios from 'axios'
import { ERROR_401_MESSAGE, ERROR_CONN_MESSAGE } from '../Error';
const AUTHENTICATED_USER = 'authenticatedUser'
const SESSION_TOKEN = 'session_token';
class AuthService {
    executeBasicAuthService(username, password) {
        return axios.get('http://localhost:8080/signin',
            { 
                headers: { Authorization: this.createBasicAuthToken(username, password) } 
            }
              
        ).then(response=>{
            return -1;
        }).catch(err=>{
            var _failCode = 2;
			if(err.message === ERROR_401_MESSAGE){
				_failCode = 0;
			}else if(err.message === ERROR_CONN_MESSAGE){
				_failCode = 1;
			}
            return _failCode;
        })
    }

    createBasicAuthToken(username, password) {
        return 'Basic ' + window.btoa(username + ":" + password)
    }

    registerSuccessfulLogin(username, password) {
        sessionStorage.setItem(AUTHENTICATED_USER, username);
        sessionStorage.setItem(SESSION_TOKEN, this.createBasicAuthToken(username,password));
    }
    registerUpdatedUser(username){
        sessionStorage.setItem(AUTHENTICATED_USER, username);
    }
    logout() {
        sessionStorage.removeItem(AUTHENTICATED_USER);
        sessionStorage.removeItem(SESSION_TOKEN);
    }

    isUserLoggedIn() {
        let user = sessionStorage.getItem(AUTHENTICATED_USER)
        if (user === null) return false
        return true
    }
    getSessionToken(){
        return sessionStorage.getItem(SESSION_TOKEN)
    }
    getLoggedInUser() {
        let user = sessionStorage.getItem(AUTHENTICATED_USER)
        if (user === null) return ''
        return user
    }
}

export default new AuthService();