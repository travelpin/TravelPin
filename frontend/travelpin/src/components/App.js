import React, { Component } from 'react';
import '../styles/App.css';
import { Main } from "./Main.js";
import { BrowserRouter } from 'react-router-dom';
import { TopBar } from "./TopBar";
import { TOKEN_KEY } from "../constants";

class App extends Component {
    state = {
        isLoggedIn: Boolean(localStorage.getItem(TOKEN_KEY)),
    }

    handleSuccessfulLogin = (token) => {
        localStorage.setItem(TOKEN_KEY, token);
        this.setState({isLoggedIn : true});
    }

    handleLogout = () => {
        localStorage.removeItem(TOKEN_KEY);
        this.setState({isLoggedIn: false});
    }

    render() {
        return (
            <BrowserRouter>
                <div className="App">
                    <TopBar handleLogout={this.handleLogout} isLoggedIn={this.state.isLoggedIn}/>
                    <Main handleSuccessfulLogin={this.handleSuccessfulLogin} isLoggedIn={this.state.isLoggedIn}/>
                </div>
            </BrowserRouter>
        );
    }
}

export default App;




