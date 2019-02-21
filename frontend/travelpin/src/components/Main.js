import React from 'react';
import { Register } from './Register';
import { Login } from './Login';
import {Switch, Route, Redirect} from 'react-router-dom';
import { Home } from './Home.js';

export class Main extends React.Component {

    getLogin = () => {
        return this.props.isLoggedIn ? <Redirect to="/home"/> : <Login handleSuccessfulLogin={this.props.handleSuccessfulLogin}/>;
    }


    getHome = () => {
     return <Home isLoggedIn={this.props.isLoggedIn} handleLogout={this.props.handleLogout}/>
    }

    render() {
        return (
            <div className="main">
                <Switch>
                    <Route exact path="/" render={this.getHome}/>
                    <Route path="/login" render={this.getLogin}/>
                    <Route path="/register" component={Register}/>
                    <Route path="/home" render={this.getHome}/>
                    <Route render={this.getHome}/>
                </Switch>
            </div>
        );
    }
}