import axios from 'axios'

const AUTHENTICATED_USER = 'authenticatedUser'
const SESSION_TOKEN = 'session_token';
class AuthService {
    // test(){
    //     return axios.get('http://localhost:8080/test');
    // }
    // testPost(t1, t2){
    //     return axios.post('http://localhost:8080/signin',{
    //         auth : {
    //             username : t1,
    //             password : t2
    //         }
    //     });
    // }
    executeBasicAuthService(username, password) {
        return axios.get('http://localhost:8080/signin',
            { 
                headers: { Authorization: this.createBasicAuthToken(username, password) } 
            }
              
        )
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