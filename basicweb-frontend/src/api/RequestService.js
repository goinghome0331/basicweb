import axios from 'axios';
import AuthService from './AuthService';

class RequestService {
    request(_url,_data,callback,method,cType){
        var promise;
        switch(method){
            case 'post':
                if(cType){
                    promise = this.requestPostForContentType(_url,_data,cType);                
                }else{
                    promise = this.requestPost(_url,_data);
                }
                break;
            default :
                    promise = this.requestGet(_url,_data,callback);
                break;
        }
        promise
        .then(response=>{
            if(response.data !== null)
                callback(response.data);
        }).catch(err=>{
            this.handleError(err);
        });
    }

    get(_url,_params){
        return axios.get(_url,{
            params : _params
        }).then(response=>{
            return response.data;
        }).catch(err=>{
            this.handleError(err);
        });
    }
    requestGet(_url,_params){
        return axios.get(_url,{
            headers : {
                'Authorization' : AuthService.getJWTToken()
            },
            params : _params
        });
    }

    requestPostForContentType(_url,_data,cType){
        return axios.post(_url,_data,{
            headers : {
                'Content-Type' : cType,
                'Authorization' : AuthService.getJWTToken()
            },
        });
    }

    requestPost(_url,_data){
        return axios.post(_url,_data,{
            headers :{
                'Authorization' : AuthService.getJWTToken()
            }
        });
    }



    handleError(err){
        if(err.message === 'Network Error'){
            alert('죄송합니다. 현재 서버와 연결이 끊겼습니다. 관계자에 연락바랍니다.');
        }else if(err.message === 'Request failed with status code 401'){
            alert('인증 또는 서버 내부 처리가 실패했습니다. 관계자에 연락바랍니다.');
        }
		
    }
}

export default new RequestService();