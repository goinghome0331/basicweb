import React,{Component} from 'react'
import {Link} from "react-router-dom"
import RequestService from '../api/RequestService'
import {GET_POSTS_URL} from '../URL'
import { faArrowCircleDown } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
class Board extends Component {
    constructor(props){
        super(props)
        this.state = {
            index : 1,
            posts : []
        }
    }
    loadPosts(_index){
        console.log(_index);
        RequestService.request(GET_POSTS_URL,{index:_index},(data)=>{
            console.log(data);
            if(_index !== 1 && data.length===0){
                alert('더 이상 게시물이 없습니다.');
            }else if(data.length !== 0){
                var _post = this.state.posts.concat(data)
                 this.setState({
                    posts : _post,
                    index : _index
                })
            }
        });
    }
    componentDidMount(){
        this.loadPosts(1);
    }
    render(){
        var toc = [];
        if(this.state.posts.length === 0){
            toc.push(<tr key={1}><td colSpan="4">게시 글이 없습니다.</td></tr>)
        }else{
            toc = this.state.posts.map((post,index)=>{
                return (
                    <tr key={post.id}>
					    <td><Link to={'/posts/'+post.id}>{post.title}</Link></td>
					    <td>{post.date}</td>
					    <td>{post.username}</td>
                        <td>{post.hit}</td>
				    </tr>
                );
            })
        }
        
        return (
            <div className="row justify-content-center">
                <div className="col-md-8">
                    <div className="card ">
                        <header className="card-header">
                            <Link to='/regpost' className='float-right btn btn-outline-success mt-1' >글쓰기</Link>
                            <Link to='/board' className='card-title mt-2' >게시판</Link>
                        </header>
                        <article className="card-body">
                            <div className="table-responsive">
	                            <table className="table table-striped table-sm">
		                            <thead>
			                            <tr>
				                            <td>제목</td>
				                            <td>작성일</td>
				                            <td>작성자</td>
                                            <td>조회수</td>
			                            </tr>
		                            </thead>
                                    <tbody>
                                        {toc}                      
                                    </tbody>		
	                            </table>
                            </div>
                        </article>
                        <div className="card-footer">
                            <button onClick={function(e){
                            this.loadPosts(this.state.index+1)
                            }.bind(this)}className="btn btn-primary btn-block"><FontAwesomeIcon icon={faArrowCircleDown} />더보기</button>
                        </div>
                    </div>
                </div>
            </div>
        );
    }
}

export default Board;