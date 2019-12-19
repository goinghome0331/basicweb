import React,{Component} from 'react';
import {Link, withRouter} from "react-router-dom";
import AuthService from '../../api/AuthService';

import jQuery from 'jquery';

window.$ = window.jQuery = jQuery;


class Header extends Component {
	 
	constructor(props){
		super(props);
		this.hInfo = [
			{path : '/', name : '홈'},
			{path : '/board', name : '게시판'},
			{path : '/signin', name : '로그인'},
			{path : '/signup', name : '회원가입'},
			{path : '/myinfo', name : '내 정보'}
		]
		

		
		

	}
    render(){
		var isUserLoggedin = AuthService.isUserLoggedIn();

		var v;
		if(this.props.location.pathname === '/signin' || 
		   this.props.location.pathname === '/signup' || 
		   this.props.location.pathname === '/notfound'){
			v = false;
		}else{
			v = true;
		}
		var list = [];
		this.hInfo.forEach((item,i)=>{
			// console.log(item.path, item.name);
			if(isUserLoggedin === false){
				if(item.path !== '/myinfo'){
					list.push(<li key={i} className="nav-item"><Link to={item.path} className='nav-link' onClick={(e)=>{
						window.$('.navbar-collapse').collapse('hide');
					}}>{item.name}</Link></li>);
				}
			}else{
				if(item.path !== '/signin' && item.path !== '/signup'){
					list.push(<li key={i} className="nav-item"><Link to={item.path} className='nav-link' onClick={(e)=>{
						window.$('.navbar-collapse').collapse('hide');
						// window.$('.navbar-nav>li>a').on('click', function(){
						// 	window.$('.navbar-collapse').collapse('hide');
						// });
					}}>{item.name}</Link></li>);
				}
			}
		});
		if(isUserLoggedin){
			list.push(<li key={3} className="nav-item"><a href={"/"} className='nav-link' onClick={(e)=>{
				e.preventDefault()
				AuthService.logout()
				this.props.history.push('/')		
			}}>로그아웃</a></li>);
		}
		var navbar = <nav id='header' className="navbar navbar-expand-lg navbar-dark fixed-top navbar-inner">
		<div className="container">
			<a className="navbar-brand" href="/">{this.props.title}</a>
			<button className="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarResponsive" aria-controls="navbarResponsive" aria-expanded="false" aria-label="Toggle navigation">
				<span className="navbar-toggler-icon"></span>
			</button>
		<div className="collapse navbar-collapse" id="navbarResponsive">
			<ul className="navbar-nav ml-auto text-muted">				
				{list}
			</ul>
		</div>
		</div>
	  </nav>
		
		if(v) {
			return navbar;
		}
		else {
			return (<nav></nav>);
		}
    }
}

export default withRouter(Header);