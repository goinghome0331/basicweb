import React,{Component} from 'react';
import {Link} from "react-router-dom"
import RequestService from '../api/RequestService'
import {ADD_POST_URL} from '../URL'

class RegisterPost extends Component {

    constructor(props){
        super(props)
        
        if(this.props.location.query === undefined){
            this.state = {
                id : -1,
                title : '',
                content : '',
                submitBtn : '저장하기',
                header : '게시글 작성'
            }    
        }else{
            this.state = {
                id : this.props.location.query.post.id,
                title : this.props.location.query.post.title,
                content : this.props.location.query.post.content,
                submitBtn : '수정하기',
                header : '게시글 수정'
            }
        }
        this.handleChange = this.handleChange.bind(this)
        this.handleSubmit = this.handleSubmit.bind(this)
        console.log(this.props.location.query)
    }
    handleChange(event){
        this.setState({
            [event.target.name] : event.target.value
        })
        
    }
    handleSubmit(event){    
        event.preventDefault()
        if(this.state.title < 1){
            alert('제목은 1글자 이상입니다.')
            return ;
        }else if(this.state.comment < 1){
            alert('내용은 1글자 이상입니다.')
            return ;
        }

        var formData = new FormData(event.target)
        RequestService.requestPost(ADD_POST_URL,formData)
        .then((response)=>{
            console.log(response)

            if(response.data === -1){
                alert('처리중에 문제가 발생했습니다. 관계자에게 연락바랍니다.')
                return ;
            }else{
                alert('글이 등록되었습니다.')
                this.props.history.push(`/posts/${response.data}`)
            }
        }).catch(err=>{RequestService.handleError(err,this.props)})
    }
    render(){
       return (
        <div className="row justify-content-center">
        <div className="col-md-8">
            <div className="card">
                <header className="card-header">
                    <h4 className="card-title mt-2">{this.state.header}</h4>
                    <Link to='/board' className='float-right btn btn-outline-success mt-1' >게시판</Link>
                </header>
                <div className="card-body">
                    <form onSubmit={this.handleSubmit}>
                        <input type='hidden' name='id'defaultValue={this.state.id}/>
                        <div className="form-group">
                            <label>제목</label>
                            <input type="text" name = "title" className="form-control" value={this.state.title} onChange={this.handleChange}/>
                        </div>
                        <div className="form-group">
                            <label htmlFor='comment'>내용</label>
                            <textarea name='content' className="form-control" rows="5" value={this.state.content} onChange={this.handleChange}/>
                        </div>
                        <input className="btn btn-primary" type="submit" value={this.state.submitBtn}/>
                    </form>
                </div>
            </div>
        </div>
    </div>
       );
    }
}

export default RegisterPost;
