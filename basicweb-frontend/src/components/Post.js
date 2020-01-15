import React,{Component} from 'react';
import {Link} from "react-router-dom"
import RequestService from '../api/RequestService'
import {POST_URL,ADD_COMMENT_URL,DELETE_COMMENT_URL,DELETE_POST_URL,GET_COMMENTS_URL} from '../URL'
import AuthService from '../api/AuthService'
import alt_user from '../alt_user.png'
import { faTimes,faArrowCircleDown,faTrashAlt,faEdit,faList,faEye,faUser,faComment,faClock } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
class Post extends Component {
	constructor(props){
		super(props)
		
		this.state={
			comment_index : 1,
			post : {
				id : '',
				title : '',
				content : '',
				hit : 0,
				date : '',
				username : ''
			},
			total : 0,
			init_comments : false,
			acc_comments : [],
			comment : '',
		}


		this.handleSubmit = this.handleSubmit.bind(this);
		this.handleChange = this.handleChange.bind(this);
		this.handleClick = this.handleClick.bind(this);
	}
	
	handleChange(event){
		this.setState({
			[event.target.name] : event.target.value
		})
	}
	handleSubmit(event){
		event.preventDefault()
		if(this.state.comment.length < 4){
			alert('4자 이상 입력해주세요.');
			return ;
		}
		var formData = new FormData(event.target)

		RequestService.request(POST_URL + `/${this.state.post.id}` + ADD_COMMENT_URL,formData,async (data)=>{
			alert('댓글이 등록되었습니다.')
			
			this.setState({
				comment : '',
				init_comments : false,
				acc_comments : [],
				comment_index : 1
			});
		},'post');
	}
	handleClick(event,id,post){
		event.preventDefault()
		var url;
		if(post){
			url = POST_URL+`/${id}`+ DELETE_POST_URL;
		}else{
			url = POST_URL+`/comments/${id}`+DELETE_COMMENT_URL
		}
		if(window.confirm('정말 삭제하시겠습니까?')){
			RequestService.request(url, { username: AuthService.getLoggedInUser()},(data)=>{
				if (data === 0) {
					alert('잘못된 사용자가 삭제를 시도했습니다.')
					return;
				} else {
					alert('삭제가 완료되었습니다.')
					if (post) {
						this.props.history.push('/board')
					} else {
						this.setState({
							comment : '',
							init_comments : false,
							acc_comments : [],
							comment_index : 1
						});
					}
				}
			});
		}
	}
	loadPost(){
		RequestService.request(POST_URL+`/${this.props.match.params.id}`,{},(data)=>{

			this.setState({
				post : data,
				acc_comments : data.commentSet.comments,
				total : data.commentSet.total,
				comment_index : 2,
				init_comments : true
			});
		});
	}
	loadComments(comment_index){
		RequestService.request(GET_COMMENTS_URL+`/${this.state.post.id}`,{index:comment_index},(data)=>{
			console.log(data);
			if(data.comments.length === 0){
				alert('댓글이 더이상 없습니다.');
			}else{
				let added_comments = this.state.acc_comments.concat(data.comments);
				this.setState({
					acc_comments : added_comments,
					init_comments : true,
					total : data.total,
					comment_index : this.state.comment_index
				})
			}
		});
	}

	componentDidMount(){
		this.loadPost();
	}
	componentDidUpdate(){
		if(!this.state.init_comments){
			this.loadComments(1);
		}
	}
    render(){

		var comments = [];
		if(this.state.acc_comments.length === 0){
			comments.push(<div key={0} className="media border p-3">댓글이 없습니다.</div>)
		}else{
			
			comments = this.state.acc_comments.map((comment)=>{
				var deleteCommentBtn = ''
				if(AuthService.getLoggedInUser() === comment.username){
					deleteCommentBtn = <a href='/' className='float-right btn btn-outline-danger mt-1' onClick={(e)=>{this.handleClick(e,comment.id)}}><FontAwesomeIcon icon={faTimes} /></a>
				}
				var img;
				if(comment.imageData === null){
					img = <img src={alt_user} alt="no img" className="mr-3 mt-3 rounded-circle" style={{width:'60px'}} />
				}else{
					img = <img src={"data:image/jpg;base64,"+comment.imageData} alt="no img" className="mr-3 mt-3 rounded-circle" style={{width:'60px'}} />
				}
				return (
					<div key={comment.id} className="media border p-3">
							{img}
  						    <div className="media-body">
								{deleteCommentBtn}
    						    <p><FontAwesomeIcon icon={faUser}/>{comment.username}&nbsp;<FontAwesomeIcon icon={faClock}/>{comment.regDate}</p>
							    <p>{comment.content}</p>
  						    </div>
					</div>
				);
			})
		}
		var deletePostBtn = '';
		var editPostBtn = '';
		if(AuthService.getLoggedInUser() === this.state.post.username){
			deletePostBtn = <a href='/' className='float-right btn btn-outline-danger mt-1' onClick={(e)=>{this.handleClick(e,this.state.post.id,true)}}><FontAwesomeIcon icon={faTrashAlt} /></a>
			editPostBtn = <Link to={{
				pathname: '/regPost',
				query: {
					post : {
						id : this.state.post.id,
						title : this.state.post.title,
						content : this.state.post.content,
					}
				}
			}} className='float-right btn btn-outline-success mt-1'><FontAwesomeIcon icon={faEdit} /></Link>
		}
		
        return (
            <div className="row justify-content-center">
	            <div className="col-md-8">
		            <div className="card">
			            <h4 className="card-title mt-2">{this.state.post.title}{deletePostBtn}{editPostBtn}<Link to='/board' className='float-right btn btn-outline-primary mt-1' ><FontAwesomeIcon icon={faList}/></Link></h4>
			            <header className="card-header">
							<FontAwesomeIcon icon={faUser}/>{this.state.post.username}&nbsp;&nbsp;<FontAwesomeIcon icon={faComment}/>{this.state.total}&nbsp;&nbsp;<FontAwesomeIcon icon={faEye}/>{this.state.post.hit}
			                <p className="float-right"><FontAwesomeIcon icon={faClock}/>{this.state.post.date}</p>
		                </header>
		                <div className="card-body">
                            {this.state.post.content}
			            </div>
			            <div className="card-footer">
				            <form onSubmit={this.handleSubmit}className="form-horizontal" > 
                                <div className="form-group row">
                                    <label className="col-sm-2 control-label">댓글 작성</label>
                                    <div className="col-sm-10">
                                        <textarea className="form-control" name="comment" value={this.state.comment} onChange={this.handleChange}rows="3"></textarea>
                                    </div>
                                </div>
                                <div className="form-group">
                                    <div className="float-right">                    
                                        <button className="btn btn-success btn-circle text-uppercase" type="submit" id="submitComment">저장</button>
                                    </div>
                                </div>            
                            </form>
			            </div>
			            <div className="card-body">
				            <h6 className="card-title mt-2"><i className="far fa-comment"></i>{this.state.total} Comments</h6>
							{comments}
							<div className="card-footer">
                            	<button onClick={function(e){
									this.loadComments(this.state.comment_index+1);
                            	}.bind(this)}className="btn btn-primary btn-block"><FontAwesomeIcon icon={faArrowCircleDown} />더보기</button>
                        </div>
			            </div>
		            </div>
	            </div>
            </div>
        );
    }
}

export default Post;
