import React from 'react';
import {Icon, Layout, Menu} from 'antd';
import {SimpleMap} from "./GoogleMap";
import {LeftExpansionPanel} from "./LeftExpansionPanel/LeftExpansionPanel"
import { Link } from 'react-router-dom';
const { Header, Content, Footer } = Layout;
// the container of the left-pop-up window, right-pop-up window, navbar, and the footer
export class Home extends React.Component {
    render() {
        return (
            <Layout className="layout">
                <div>
                    {this.props.isLoggedIn ? <a className="logout" onClick={this.props.handleLogout} >
                        <Icon type="logout"/>{' '}Logout
                    </a> : <div className="before-login">
                        <Link to="/login" className="login-link"><Icon type="login" />{' '}Login</Link>
                        <Link to="/register"><Icon type="user-add" />{' '}Register</Link>
                    </div> }
                </div>
                <Header>
                    <div className="logo" >

                    </div>
                    <Menu
                        theme="dark"
                        mode="horizontal"
                        defaultSelectedKeys={['2']}
                        style={{ lineHeight: '64px' }}
                    >
                        <Menu.Item key="1" className="menu-item">nav 1</Menu.Item>
                        <Menu.Item key="2" className="menu-item">nav 2</Menu.Item>
                        <Menu.Item key="3" className="menu-item">nav 3</Menu.Item>
                    </Menu>
                </Header>
                <LeftExpansionPanel/>
                <div>
                    <Content className={"mapStyle"}>
                        <div style={{ background: '#fff', padding: 1, minHeight: 1000 }}>
                            <SimpleMap/>
                        </div>
                    </Content>
                </div>



                <Footer style={{ textAlign: 'center' }}>

                </Footer>
            </Layout>
        )
    }
}