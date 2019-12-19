import axios from 'axios';
import AuthService from './AuthService';

class RequestService {

    requestGet(_url,_params, props){
        return axios.get(_url,{
            headers : {
                'Authorization' : AuthService.getSessionToken()
            },
            params : _params
        })
    }

    requestPostForMultipart(_url,_data){
        return axios.post(_url,_data,{
            headers : {
                'Content-Type' : 'multipart/form-data',
                'Authorization' : AuthService.getSessionToken()
            },
        });
    }

    requestPost(_url,_data){
        return axios.post(_url,_data,{
            headers : {
                'Authorization' : AuthService.getSessionToken()
            },
        });
    }
    handleError(err,props){
		console.log(1)
		props.history.push({
			pathname : '/error',
			state : {
				title : err.name,
				detail : err.message
			}
		})
	}
}

export default new RequestService();