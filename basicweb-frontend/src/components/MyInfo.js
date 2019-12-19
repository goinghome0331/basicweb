import path from 'path';
import React,{Component} from 'react';
import AuthService from '../api/AuthService';
import RequestService from '../api/RequestService';
import {USER_IMAGE_URL, UPDATE_USER_IMAGE_URL, UPDATE_USER_PASSWORD_URL, DELETE_USER_URL, DELETE_USER_IMAGE_URL,USER_INFO_URL} from '../URL';
import jQuery from 'jquery';
import alt_user from '../alt_user.png'
import { faHome } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";

window.$ = window.jQuery = jQuery;


const PASSWORD_MESSAGE1 = '비밀번호는 기본적으로 8자 이상입니다.';
const PASSWORD_MESSAGE2 = '처리중에 문제가 발생했습니다. 관계자에게 연락바랍니다.';
const PASSWORD_MESSAGE3 = '현재 비밀번호가 일치하지 않습니다.';

class MyInfo extends Component {
	constructor(props){
		super(props)
		this.me = AuthService.getLoggedInUser();
		this.state = {
			username : '',
			gender : '',
			firstName : '',
			lastName : '',
			birth : '',
			image_path : '',
			info_loaded : false,
			image_loaded : true,
			image_data : '',
			file_path : '',
			currentPassword : '',
			newPassword : '',
			newPasswordConfirm : '',
			deletePassword : ''
		}
		
		this.form = []
		this.handleSubmit = this.handleSubmit.bind(this)
		this.handleChange = this.handleChange.bind(this)
		// this.handleError = this.handleError.bind(this)
	}
	
	handleSubmit(event){
		event.preventDefault()

		if(this.form[0] === event.target){
			var formData = new FormData(this.form[0]);
			var filename = formData.values().next().value.name;
			if(filename === ''){
				alert('파일을 선택하세요')
				return ;
			}

			if(window.confirm('정말 변경하시겠습니까?')){
					formData.append('username', this.me)
					RequestService.requestPostForMultipart(UPDATE_USER_IMAGE_URL, formData)
						.then((response) => {
							this.setState({
								image_loaded: false,
								image_path : response.data,
								file_path: 'C:\\fakepath\\' + path.basename(response.data)
							})
						}).catch(err=>{RequestService.handleError(err,this.props)})
			}
		}else if(this.form[1] === event.target){
			
			if(this.state.newPassword.length < 8 || this.state.newPasswordConfirm.length < 8){
				alert(PASSWORD_MESSAGE1)
				this.setState({
					newPassword : '',
					newPasswordConfirm : ''
				})
				return ;
			}else if(this.state.newPassword !== this.state.newPasswordConfirm){
				alert('두 비밀번호가 일치하지 않습니다.')
				this.setState({
					newPassword : '',
					newPasswordConfirm : ''
				})
				return ;
			}else if(this.state.currentPassword.length < 8){
				alert(PASSWORD_MESSAGE1)
				this.setState({
					currentPassword : ''
				})
				return ;
			}
			var formData = new FormData(this.form[1]);
			RequestService.requestPost(UPDATE_USER_PASSWORD_URL,formData)
			.then((response)=>{
				if(response.data === -1){
					alert(PASSWORD_MESSAGE2)
					this.setState({
						currentPassword : '',
						newPassword : '',
						newPasswordConfirm : ''
					})
				}else if(response.data === 0){
					alert(PASSWORD_MESSAGE3);
					this.setState({
						currentPassword : '',
					})
				}else if(response.data === 1){
					alert('변경이 완료되었습니다.')
					AuthService.registerSuccessfulLogin(AuthService.getLoggedInUser(),this.state.newPassword);
					this.setState({
						currentPassword : '',
						newPassword : '',
						newPasswordConfirm : ''
					})
					window.$('#passwdUpadteModal').modal('hide');
				}
			}).catch(err=>{RequestService.handleError(err,this.props)})
		}else if(this.form[2] === event.target){
			if(this.state.deletePassword < 8){
				alert(PASSWORD_MESSAGE1);
			}
			if(window.confirm('정말 삭제하시겠습니까?')){
				var formData = new FormData(this.form[2]);
				RequestService.requestPost(DELETE_USER_URL, formData)
					.then((response) => {
						if (response.data === -1) {
							alert(PASSWORD_MESSAGE2)
							return;
						} else if (response.data === 0) {
							alert(PASSWORD_MESSAGE3);
							this.setState({
								deletePassword: ''
							})
							return;
						} else if (response.data === 1) {
							alert('삭제가 완료되었습니다.')
							AuthService.logout()
							window.$('#deleteUserModal').modal('hide')
							this.props.history.push('/')
						}
					}).catch(err=>{RequestService.handleError(err,this.props)})
			}
		}
		
	}

	handleChange(event){
		this.setState({
			[event.target.name] : event.target.value
		})
	}
	loadMyInfo(){
		RequestService.requestGet(USER_INFO_URL,{'username':this.me})
		.then((response)=>{
			console.log(response)
			if(response.data === ''){
				alert(PASSWORD_MESSAGE2)
				return ;
			}else{
				this.setState({
					username : response.data.username,
					gender : response.data.gender,
					firstName : response.data.firstName,
					lastName : response.data.lastName,
					birth : response.data.birth,
					image_path : response.data.imagePath === null ? '' : response.data.imagePath,
					info_loaded : true,
					image_loaded : false,
				})
			}
		}).catch(err=>{RequestService.handleError(err,this.props)})
	}
	loadMyImage(){
		RequestService.requestGet(USER_IMAGE_URL,{'username':this.me})
		.then((response)=>{
			console.log(response)	
			var _file_path = this.state.image_path === '' ? this.state.image_path : 'C:\\fakepath\\'+path.basename(this.state.image_path);
			this.setState({
				image_data : response.data,
				image_loaded : true,
				file_path : _file_path
			})
		}).catch(err=>{RequestService.handleError(err,this.props)})
	}
	render(){
		if(!this.state.info_loaded){
			this.loadMyInfo()
		}
		if(!this.state.image_loaded){
			this.loadMyImage();
		}
		var img;
		if(this.state.info_loaded && this.state.image_loaded){
			
			if(this.state.image_data === ''){
				img = <img alt="이미지 없음" className="mx-auto d-block" src={alt_user} width="100px" height="100px" />
			}else{
				img = <img alt="이미지 없음" className="mx-auto d-block" src={"data:image/jpg;base64,"+this.state.image_data} width="100px" height="100px" />
			}
			
		}else{
			img = <img alt="이미지 없음" className="mx-auto d-block" src={this.state.image_path} width="100px" height="100px" />
		}
		return (
			<div className="row justify-content-center">
            	<div className="col-md-6">
		            <div className="card">
			            <header className="card-header">
				            <a href={"/"} className="float-right btn btn-outline-primary mt-1"><FontAwesomeIcon icon={faHome} /></a>
				            <h4 className="card-title mt-2">내 정보</h4>
			            </header>
			            <article className="card-body">
							{img}
                            {/* <img alt="이미지 없음" className="mx-auto d-block" src={img_url} width="100px" height="100px" />	 */}
				            <div className="justify-content-center" style={{width:'100%'}}>
				                <form name = 'file-form' ref={f=>{this.form[0]=f}} onSubmit={this.handleSubmit} className="form-group row">
				                    <div className="col-md-12">
					                    <div className="input-group">
						                    <div className="input-group-prepend">
							                    <span className="input-group-text" id="inputGroupFileAddon01">게시할 사진</span>
						                    </div>
						                    <div className="custom-file">
                                                <input type="file" name="file" className="custom-file-input" onChange={function(e){this.setState({file_path:path.basename(e.target.value)})}.bind(this)}/>
							                    <label className="custom-file-label" htmlFor="inputGroupFile01">{this.state.file_path}</label>
  						                    </div>
					                    </div>				
				                    </div>
				                    <div className="col-md-6">
					                    <button type="submit" className="btn btn-primary btn-block">변경</button>
				                    </div>
				                    <div className="col-md-6">
					                    <a href={"/"} className="btn btn-danger btn-block" onClick={function(e){
											e.preventDefault()
											if(window.confirm('정말 삭제하시겠습니까?')){
												if(this.state.image_path === ''){
													alert('기본 이미지는 삭제할 수 없습니다.')
													return ;
												}
												RequestService.requestGet(DELETE_USER_IMAGE_URL)
													.then((response) => {
														if(response.data){
															this.setState({
																image_loaded: false,
																image_path: '',
																file_path: '',
															})
														}else{
															alert('삭제되지 않았습니다. 다시한번 시도하세요.')
														}	
													}).catch(err=>{RequestService.handleError(err,this.props)})
											}
										}.bind(this)}>제거</a>
				                    </div>
			                    </form>				
			                </div>
				            <div className="justify-content-center" style={{width:'100%'}}>
								<h4 className="card-title">아이디 : {this.state.username}</h4>
				            </div>
				            <div className="card-columns" >
					            <div className="card bg-light">
   						            <div className="card-body text-center">
										<p className="card-text">이름 : {this.state.lastName} {this.state.firstName}</p>
   						            </div>
   					            </div>
   					            <div className="card bg-light">
   						            <div className="card-body text-center">
										<p className="card-text">생년월일 : {this.state.birth}</p>
   						            </div>
   					            </div>
   					            <div className="card bg-light">
   						            <div className="card-body text-center">
										<p className="card-text">성별 : {this.state.gender}</p>
   						            </div>
   					            </div>
				            </div>
			            </article>
			        <div className="card-footer">
				    <div className="row">
					    <div className="col-md-6">
						    <button type="button" className="btn btn-success btn-block" data-toggle="modal" data-target='#passwdUpadteModal'>비밀번호 변경</button>
					    </div>
					    <div className="col-md-6">
						    <button type="button" className="btn btn-danger btn-block" data-toggle="modal" data-target='#deleteUserModal'>계정 삭제</button>
					    </div>
					    <div className="modal fade" id="passwdUpadteModal">
						    <div className="modal-dialog modal-lg">
							    <div className="modal-content">
        						    <div className="modal-header">
									    <h4 className="modal-title">비밀번호 변경</h4>
									    <button type="button" className="close" data-dismiss="modal">&times;</button>
        						    </div>
        						    <div className="modal-body">
        							    <form name='password-update-form' ref={f=>{this.form[1]=f}} onSubmit={this.handleSubmit}>
        							        <table>
												<tbody>
        								        	<tr className="form-group">
        									        	<td><label>현재 비밀번호</label></td>
        									        	<td><input type="password" name="currentPassword" required value={this.state.currentPassword} onChange={this.handleChange}/></td>
        								        	</tr>
        								        	<tr className="form-group">
        									        	<td><label>새 비밀번호</label></td>
        									        	<td><input type="password" name="newPassword" value={this.state.newPassword} required onChange={this.handleChange}/></td>
        								        	</tr>
        								        	<tr className="form-group">
        									        	<td><label>새 비밀번호 확인</label></td>
        									        	<td><input type="password" name="newPasswordConfirm" value={this.state.newPasswordConfirm} required onChange={this.handleChange}/></td>
        								        	</tr>
												</tbody>
        							        </table>
        								        <div className="row justify-content-center">
											        <div className="col-md-6">
												        <button type="submit" id="updatePasswordBtn" className="btn btn-success btn-block">변경</button>
											        </div>
										        </div>
        							    </form>
        						    </div>
        						    <div className="modal-footer">
          							    <button type="button" className="btn btn-secondary" data-dismiss="modal">Close</button>
        						    </div>
        
      						    </div>
						    </div>
					    </div>
					    <div className="modal fade" id="deleteUserModal">
						    <div className="modal-dialog modal-lg">
      						    <div className="modal-content">
								    <div className="modal-header">
									    <h4 className="modal-title">계정 삭제</h4>
									    <button type="button" className="close" data-dismiss="modal">&times;</button>
								    </div>
        						    <div className="modal-body">
        							    <form name='user-delete-form' ref={f=>{this.form[2]=f}} onSubmit={this.handleSubmit}>
          							        <div className="form-group">
        									    <label>현재 비밀번호</label>
        									    <input type="password" name="deletePassword" value={this.state.deletePassword} onChange={this.handleChange}/>
        							        </div>
        							        <div className="row justify-content-center">
											    <div className="col-md-6">
												    <button type="submit" className="btn btn-success btn-block">삭제</button>
											    </div>
									        </div>
        							    </form>
        						    </div>
        						    <div className="modal-footer">
          							    <button type="button" className="btn btn-secondary" data-dismiss="modal">Close</button>
        						    </div>
        
        					    </div>
						    </div>
					    </div>
				    </div>
			    </div>
		    </div>
	    </div>
    </div>
		)
	}

}

export default MyInfo;