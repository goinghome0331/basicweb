import React,{Component} from 'react';
import {Link} from "react-router-dom";
import './intro.css';

class Intro extends Component {
    render(){
        return (
            <div>
            	<div id="homeCarousel" className="carousel slide" data-ride="carousel">
		        	<ol className="carousel-indicators">
			        	<li data-target="#homeCarousel" data-slide-to="0" className="active"></li>
			        	<li data-target="#homeCarousel" data-slide-to="1"></li>
		        	</ol>
		        	<div className="carousel-inner">
			        	<div className="carousel-item active">
				        	<div className="bg-1 full"></div>
				            	<div className="carousel-caption">
					            	<h3>Spring boot</h3>
					            	<p>스프링 부트 프레임 워크로 구현!</p>
				            	</div>
			            	</div>
                        	<div className="carousel-item">
				            	<div className="bg-2 full"></div>
				            	<div className="carousel-caption">
					            	<h3>Bootstrap</h3>
					            	<p>UI는 부트스트랩으로</p>
				            	</div>
			            	</div>
		            	</div>
		            	<a className="carousel-control-prev" href="#homeCarousel" data-slide="prev"> <span className="carousel-control-prev-icon"></span>
			            	<span className="sr-only">Previous</span>
		            	</a>
                    	<a className="carousel-control-next" href="#homeCarousel" data-slide="next"> <span className="carousel-control-next-icon"></span>
			            	<span className="sr-only">Next</span>
		            	</a>
	            	</div>

	        	<div className="container">
		        	<div className="row">
			        	<div className="col-lg-4 offset-lg-4 text-center">
				        	<h1>What is this?</h1>
				        	<Link to={"/"} className="btn btn-primary">확인하기</Link>
			        	</div>
		        	</div>
	        	</div>
			</div>
        );
    }
}

export default Intro;