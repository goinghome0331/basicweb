import React from 'react';
import { Route, Redirect } from 'react-router-dom';
import AuthService from '../api/AuthService';
function AuthRoute({ component: Component, render, ...rest }) {
    return (
      <Route
        {...rest}
        render={props =>
          AuthService.isUserLoggedIn() ? (
            render ? render(props) : <Component {...props} />
          ) : (
            <Redirect
              to={{ pathname: '/signin', state: { from: props.location } }}
            />
          )
        }
      />
    );
  }
  
  export default AuthRoute;