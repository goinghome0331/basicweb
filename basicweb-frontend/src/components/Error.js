import React,{Component} from 'react'
import './error.css';

import { faHome } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
class Error extends Component {
    
    constructor(props){
        super(props)
        

        this.title = '404 Not Found 에러'
        this.detail = '죄송합니다. 해당 경로의 페이지는 존재하지 않습니다.'
        if(this.props.location.state.detail === 'Network Error'){
            this.title = '서버 연결 끊김'
            this.detail = '죄송합니다. 현재 서버와 연결이 끊겼습니다. 관계자에 연락바랍니다.'
        }else{
            this.title = '서버 내부 오류'
            this.detail = '죄송합니다. 요청 처리 중에 서버에서 오류가 발생했습니다. 관계자에 연락바랍니다.'
        }
    }

    render(){
        return (
        <div class="container">
    <div class="row">
        <div class="col-md-12">
            <div class="error-template">

                <h2>{this.title}</h2>
                <div class="error-details">
                    {this.detail}
                </div>
                <div class="error-actions">
                    <a href={'/'} class="btn btn-primary btn-lg"><FontAwesomeIcon icon={faHome} />홈으로</a>
                </div>
            </div>
        </div>
    </div>
</div>
        );
    }
}

export default Error;