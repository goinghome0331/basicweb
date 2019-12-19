import React,{Component} from 'react';
import 'bootstrap/dist/css/bootstrap.css';
import 'bootstrap/dist/js/bootstrap.js';
import { Switch, Route, BrowserRouter as Router } from "react-router-dom";
import './App.css';
import Header from './components/templates/Header';
import Intro from './components/Intro';
import Signin from './components/Signin';
import Signup from './components/Signup';
import MyInfo from './components/MyInfo';
import Board from './components/Board';
import Error from './components/Error';
import RegisterPost from './components/RegisterPost';
import Post from './components/Post';
import Footer from './components/templates/Footer';
import AuthRoute from './components/AuthRoute';

class App extends Component {
  constructor(props){
    super(props);
    this.title = 'Basic Web';
    // this.changePage = this.changePage.bind(this);
  }

  componentDidMount(){
    document.title= this.title;
  }

  render(){
    
    return (
      <Router>
        <Header title={this.title}></Header>
        <Switch>
          <Route exact path='/' component={Intro}></Route>
          <Route path='/signin' component={Signin}></Route>
          <Route path='/signup' component={Signup}></Route>
          <AuthRoute path='/myinfo' component={MyInfo}></AuthRoute>
          <AuthRoute path='/board' component={Board}></AuthRoute>
          <AuthRoute path='/regpost' component={RegisterPost}></AuthRoute>
          <AuthRoute path='/posts/:id' component={Post}></AuthRoute>
          <Route path='/error' component={Error}></Route>
          <Route component={Error}></Route>
        </Switch>
        <Footer></Footer>
      </Router>
    );
  }
}

export default App;
