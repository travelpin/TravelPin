import React from 'react';
import {Icon, Layout, Menu} from 'antd';
import {TravelPinGoogleMap} from "./GoogleMap";
import {LeftExpansionPanel} from "./LeftExpansionPanel/LeftExpansionPanel"
import { Link } from 'react-router-dom';
import {GOOGLE_MAP_API} from "../secureConstants";

const { Header, Content, Footer } = Layout;
// the container of the left-pop-up window, right-pop-up window, navbar, and the footer
export class Home extends React.Component {
    state = {

    }


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
                    </Menu>
                </Header>
                <LeftExpansionPanel/>
                <div>
                    <Content className={"mapStyle"}>
                        <div style={{ background: '#fff', padding: 0, minHeight: 1000 }}>
                            <TravelPinGoogleMap
                                googleMapURL={`https://maps.googleapis.com/maps/api/js?key=${GOOGLE_MAP_API}&v=3.exp&libraries=geometry,drawing,places`}
                                loadingElement={<div style={{ height: `100%` }} />}
                                containerElement={<div style={{ height: `1000px` }} />}
                                mapElement={<div style={{ height: `100%` }} />}
                            />

                        </div>
                    </Content>
                </div>



                <Footer style={{ textAlign: 'center' }}>

                </Footer>
            </Layout>
        )
    }
}