import React, { Component } from 'react';
import '../styles/App.css';
import { Main } from "./Main.js";
import { BrowserRouter } from 'react-router-dom';
import { TOKEN_KEY } from "../constants";
import { TopBar } from "./TopBar.js";



class App extends Component {
    state = {
        isLoggedIn: Boolean(localStorage.getItem(TOKEN_KEY)),
    }

    handleSuccessfulLogin = (token) => {
        localStorage.setItem(TOKEN_KEY, token);
        this.setState({
            isLoggedIn : true,
        });
    }

    handleLogout = () => {
        localStorage.removeItem(TOKEN_KEY);
        this.setState({isLoggedIn: false});
    }

    render() {
        return (
            <BrowserRouter>
                <div className="App">
                    <TopBar/>
                    <Main handleLogout={this.handleLogout} handleSuccessfulLogin={this.handleSuccessfulLogin} isLoggedIn={this.state.isLoggedIn}/>
                </div>
            </BrowserRouter>
        );
    }
}

export default App;




