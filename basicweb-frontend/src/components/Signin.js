import React,{Component} from 'react';
import AuthService from '../api/AuthService';
import './signin.css';
import {Link} from "react-router-dom"
import { ERROR_401_MESSAGE, ERROR_CONN_MESSAGE } from '../Error';
const SAVED_USERNAME = 'savedUserName';
class Signin extends Component {

	constructor(props){
		super(props);
		
		this.errMsg = [
			'아이디나 비밀번호가 일치하지 않습니다',
			'서버에 연결되지 않고 있습니다.'
		]
		var _username = localStorage.getItem(SAVED_USERNAME)
		this.state = {
			username : _username === null ? '' : _username,
			password : '',
			loginFailed : false,
			failCode : -1,
			checked : _username === null ? false : true
		}
		
		this.handleInputChange = this.handleInputChange.bind(this)
		this.handleCheckChange = this.handleCheckChange.bind(this)
		this.handleSubmit = this.handleSubmit.bind(this)
	}
	handleInputChange(event){
        this.setState(
            {
                [event.target.name]
                  :event.target.value
            }
        )
	}
	handleCheckChange(event){
		this.setState({
			checked : event.target.checked
		})
	}
	handleSubmit(event){
		event.preventDefault();
		AuthService
			.executeBasicAuthService(this.state.username, this.state.password)
            .then((response) => {
				if(response.data === -1){
					alert('처리중에 문제가 발생했습니다. 관계자에게 연락바랍니다.')
					return ;
				}

				console.log(this.state.checked)
				if(this.state.checked)
					localStorage.setItem(SAVED_USERNAME, this.state.username);
				else
					localStorage.removeItem(SAVED_USERNAME);
				AuthService.registerSuccessfulLogin(this.state.username,this.state.password)
				this.setState({
					username : '',
					password : '',
					loginFailed : false,
					failCode : -1
				})
				this.props.history.push('/')
            }).catch((err) => {
				
				var _failCode = -1;
				if(err.message === ERROR_401_MESSAGE){
					_failCode = 0
				}else if(err.message === ERROR_CONN_MESSAGE){
					_failCode = 1
				}
				this.setState({
					username : '',
					password : '',
					loginFailed : true,
					failCode : _failCode
				})
            })
		
	}
	renderError(){
		if(this.state.loginFailed){
			
			return <div className="form-group" style={{color : 'red'}}>{this.errMsg[this.state.failCode]}</div>
		}
		return '';
	}

    render(){
		var errSeg = this.renderError();
        return (
            <div className="login-form">
				<h5 className="text-center"><a href={'/'}>BasicWeb</a></h5>
			    <h2 className="text-center">로그인</h2>
		        <form onSubmit={this.handleSubmit} action="/signin" method="post" ref={fm => {this.form=fm}}>
					<input type="text" name = "username" value={this.state.username} onChange={this.handleInputChange} className="form-group form-control" placeholder="아이디" required />
					<input type="password" name="password" value={this.state.password} onChange={this.handleInputChange} className="form-group form-control" placeholder="비밀번호" required />
					{errSeg}
					<input type="submit" className="btn btn-primary btn-block" value='로그인' />
				</form>
				<div className="clearfix">
			        <label className="pull-left checkbox-inline"><input name='checked' type="checkbox" onChange={this.handleCheckChange} defaultChecked = {this.state.checked}/>아이디 저장</label>
			    </div>
		        <p className="text-right">
					회원이 아닌가요? <Link to={"/signup"}>계정만들기</Link>
		        </p>
	        </div>
        );
    }
}

export default Signin;