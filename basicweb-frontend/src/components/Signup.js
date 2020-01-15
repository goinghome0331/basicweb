import React,{Component} from 'react';
import RequestService from '../api/RequestService';
import { SINGUP_URL } from '../URL';

class Signup extends Component {

	constructor(props){
		super(props);
		this.state = {
			username : '',
			password : '',
			confirmPassword : '',
			lastName : '',
			firstName : '',
			gender : '남',
			year : '2005',
			month : '1',
			day : '1',
		}
		this.handleInputChange = this.handleInputChange.bind(this)
		this.handleSubmit = this.handleSubmit.bind(this)

		this.yearData = [];
		this.monthData = [];
		this.dayData = [];
		for(var i = 2005 ; i >= 1910; i--)
			this.yearData.push(i)

		for(i = 1; i <= 12 ; i++)
			this.monthData.push(i)

		for(i = 1; i <= 31 ; i++)
			this.dayData.push(i)

	}
	handleInputChange(event){
		this.setState({
			[event.target.name] : event.target.value
		})
	}
	// select 태그 옵션 만들기
	createOptionInSelect(values){
		return values.map((value,index)=>{return <option value={value} key={index}>{value}</option>})
	}
	handleSubmit(event){
		event.preventDefault()
		// 데이터 검증
		if(this.state.username.length < 8){
			alert('아이디는 기본적으로 8자 이상입니다.')
			return ;
		}else if(this.state.password !== this.state.confirmPassword){
			alert('두 비밀번호가 일치하지 않습니다.')
			return ;
		}

		// form 데이터 보내기
		var formData = new FormData(event.target)
		RequestService.request(SINGUP_URL,formData,(data)=>{
			if(data === 0){
				alert('이미 존재하는 아이디입니다.');
			}else if(data === 1){
				alert('가입이 완료되었습니다.');
				this.props.history.push('/signin');
			}
		},'post','multipart/form-data');
	}
    render(){
        return (
            <div className="row justify-content-center">
		        <div className="col-md-6">
			        <div className="card">
				        <header className="card-header">
					        <a href={"/"} className="float-right btn btn-outline-primary mt-1">홈으로</a>
					        <h4 className="card-title mt-2">회원가입</h4>
				        </header>
				
					    <form  onSubmit={this.handleSubmit}>
						    <div className="form-group">
							    <label>아이디</label> 
							    <input type="text" name="username" className="form-control" placeholder="아이디를 입력하세요" onChange={this.handleInputChange} value={this.state.username}/>
						    </div>
						    <div className="form-group">
							    <label>내 사진</label>
							    <div className="custom-file">
    							    <input type="file" className="custom-file-input" id="inputGroupFile01" />
    						        <label className="custom-file-label" htmlFor="inputGroupFile01">Choose file</label>
  							    </div>
						    </div>
						
						    <div className="form-group">
							    <label>비밀번호</label>
							    <input type = "password" name="password" className="form-control" onChange={this.handleInputChange} value={this.state.password}/>
						    </div>

						    <div className="form-group">
							    <label>비밀번호 확인</label>
							    <input type="password" name="confirmPassword" className="form-control" onChange={this.handleInputChange} value={this.state.confirmPassword}/>
						    </div>
						    <div className="form-row">
							    <div className="col form-group">
								    <label>성</label>
								    <input type="text" name="lastName" className="form-control" placeholder="" onChange={this.handleInputChange} value={this.state.lastName}/> 
							    </div>

							    <div className="col form-group">
								    <label>이름</label> 
								    <input type="text" name="firstName" className="form-control" placeholder="" onChange={this.handleInputChange} value={this.state.firstName}/>
							    </div>
						    </div>
						
						    <div className="form-group">
							    <div>
								    <label>성별</label>
							    </div>
							    <label className="form-check form-check-inline">
								    <input type="radio" name="gender" className="form-check-input" value="남" defaultChecked onChange={this.handleInputChange} /> 
								    <span className="form-check-label">남성</span>
							    </label> 
							    <label className="form-check form-check-inline">
								    <input type="radio" className="form-check-input" name="gender" value="여" onChange={this.handleInputChange} /> 
								    <span className="form-check-label">여성</span>
							    </label>
						    </div>
						    <div className="form-group row">
							    <div className="col-md-12">
								    <label>생년월일</label>
							    </div>
							    <div className="form-group col-md-4">
								    <select name="year" className="form-control" defaultValue='2005' onChange={this.handleInputChange}>
									    {this.createOptionInSelect(this.yearData)}
								    </select>
							    </div>
							    <div className="form-group col-md-4">
								    <select name="month" className="form-control" defaultValue='1' onChange={this.handleInputChange}>
										{this.createOptionInSelect(this.monthData)}
								    </select>
							    </div>
							    <div className="form-group col-md-4" >
								    <select name="day" className="form-control"  defaultValue='1' onChange={this.handleInputChange}>
										{this.createOptionInSelect(this.dayData)}
								    </select>
							    </div>
						    </div>
						    <div className="form-group">
							    <button type="submit" className="btn btn-primary btn-block">Register</button>
						    </div>
						    <small className="text-muted">By clicking the 'Sign Up' button, you confirm that you accept our <br/> Terms of use and Privacy Policy.</small>
					    </form>
				        <div className="border-top card-body text-center">이미 계정이 있으신가요? <a href="/signin">로그인</a></div>
			        </div>
		        </div>
	        </div>
        );
    }
}

export default Signup;