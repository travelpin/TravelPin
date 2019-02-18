import React from 'react';
import { Icon } from 'antd';

export class TopBar extends React.Component {
    render() {
        return(
            <div className="top-bar">
                {this.props.isLoggedIn ? <a className="logout" onClick={this.props.handleLogout} >
                    <Icon type="logout"/>{' '}Logout
                </a> : null}
            </div>
        )
    }
}