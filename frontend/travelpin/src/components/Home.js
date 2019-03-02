import React from 'react';
import {Icon, Layout, Menu} from 'antd';
import TravelPinGoogleMap from "./GoogleMap";
import {LeftExpansionPanel} from "./LeftExpansionPanel/LeftExpansionPanel"
import { Link } from 'react-router-dom';
import {GOOGLE_MAP_API} from "../secureConstants";

const { Header, Content, Footer } = Layout;
// the container of the left-pop-up window, right-pop-up window, navbar, and the footer


const fakeDataUrl = 'https://randomuser.me/api/?results=5&inc=name,gender,email,nat&noinfo';
const DataUrl = "http://localhost:8080/listinterests"
export class Home extends React.Component {
    state = {
        selected : {
            lat: 40.71,
            lng: -74.00
        },
        zoom : 13,
        data : [],
        directions: {},
        height_view : document.body.clientHeight - 40
    }

    componentDidMount() {
        this.loadInterests();
    }

    handleCenterChange = (selected) => {
        console.log(selected);

        this.setState({
            selected,
            zoom : 16,
        });
    }

    handleLiked = (data) => {
        this.setState({data});
    }

    loadInterests = () => {
        fetch(DataUrl, {
            method: 'GET',
        }).then((response) => {
            if (response.ok) {
                return response.json();
            }
            throw new Error('Failed to load posts.');
        }).then((data) => {
            console.log(data);


            const fakeLikedInterest =  {
                name : 'NYC',
                liked : 'TRUE',
                description : 'This is the place I want to go',
                lat: 40.9525800,
                lng: -74,
            };

            const fakeunLikedInterest =  {
                name : 'Liberty',
                liked : 'FALSE',
                description : 'This is a the place I do not like',
                lat: 41.2525800,
                lng: -74
            };

            let fakeData = [];

            for (let i = 0; i < 2; i++) {
                fakeData[i] =  i % 2 === 0 ? {
                    ...fakeLikedInterest,
                    location_id : i,
                } : {
                    ...fakeunLikedInterest,
                    location_id : i,
                }
            }

            this.setState({
                data : data.length > 0 ? data : fakeData,
            });
        }).catch((e) => {
            console.log(e.message);
        });
    }

    getBrowserHeight = (val) => {
        this.setState({
            height_view: document.body.clientHeight - 40,
        })
    }

    render() {
        let {directions} = this.state.directions;

        const {lat, lng} = this.state.selected;
        const {zoom} = this.state;




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

                <LeftExpansionPanel
                    data={this.state.data}
                    selected={this.state.selected}
                    handleCenterChange={this.handleCenterChange}
                    handleLiked={this.handleLiked}
                />
                <div>
                    <Content className={"mapStyle"}>
                        <div style={{ background: '#fff', padding: 0, minHeight: 1000 }}>
                            <TravelPinGoogleMap
                                data={this.state.data}
                                googleMapURL={`https://maps.googleapis.com/maps/api/js?key=${GOOGLE_MAP_API}&v=3.exp&libraries=geometry,drawing,places`}
                                loadingElement={<div style={{ height: `100%` }} />}
                                directions={directions}
                                containerElement={<div style={{ position: 'fixed', top: 60, bottom: 0, left: 0, right:0 }} />}
                                mapElement={<div style={{ height: `100%` }} />}
                                center={{lat,lng}}
                                zoom={zoom}
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