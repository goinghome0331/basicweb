import React,{Component} from 'react';
import './error.css';

import { faHome } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";

class NotFound extends Component {

    render(){
        return (
<div class="container">
    <div class="row">
        <div class="col-md-12">
            <div class="error-template">
                <h1></h1>
                <h2>404 Not Found 에러</h2>
                <div class="error-details">
                    죄송합니다. 해당 경로의 페이지는 존재하지 않습니다.
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

export default NotFound;
