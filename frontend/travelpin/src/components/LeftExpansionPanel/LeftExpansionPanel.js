import { Menu, Icon, Button } from 'antd';
import React from 'react';
import { Tabs, List } from 'antd';
import {ListInterests} from "./ListInterests";
import {ShowRoute} from "./ShowRoute";
import {UserSavedRoutes} from "./UserSavedRoutes";

const TabPane = Tabs.TabPane;

const SubMenu = Menu.SubMenu;


const fakeDataUrl = 'https://randomuser.me/api/?results=5&inc=name,gender,email,nat&noinfo';
const DataUrl = "http://localhost:8080/listinterests"
export class LeftExpansionPanel extends React.Component {
    state = {
        data: [],
        collapsed: false,
        pixelPosition: '400px'
    }


    componentDidMount() {
        this.loadInterests();
    }

    clickLiked = (id) => {
        let data = this.state.data;
        for (let i = 0; i < data.length; i++) {
            if (data[i].id === id) {
                data[i].liked = data[i].liked === 'TRUE' ? "FALSE" : "TRUE";
            }
        }
        console.log(data);
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
                id : 1,
                name : 'NYC',
                liked : 'TRUE',
                description : 'This is the place I want to go',
            };
            const fakeunLikedInterest =  {
                id : 2,
                name : 'Liberty',
                liked : 'FALSE',
                description : 'This is a the place I do not like',
            };
            let fakeData = [];

            for (let i = 0; i < 20; i++) {
                fakeData[i] =  i % 2 == 0 ? {
                    ...fakeLikedInterest,
                    id : i,
                } : {
                    ...fakeunLikedInterest,
                    id : i,
                }
            }

            this.setState({
                data : data.length>0 ? data : fakeData,
            });
        }).catch((e) => {
            console.log(e.message);
        });
    }

    toggleCollapsed = () => {
        this.setState({
            collapsed: !this.state.collapsed,
        });
    }

    render() {
        const MenuStyle = {
            width:this.state.collapsed?'0px':this.state.pixelPosition,
            height:this.state.collapsed?'0px':this.state.pixelPosition,
        }
        const ButtonStyle = {
            marginBottom: 8,
            position:"fixed",
            left:this.state.collapsed?'0px':this.state.pixelPosition,
        }
        const favorite = this.state.data.filter((interest) => interest.liked === 'TRUE');
        return (

            <div  className={"leftExpansionPanel"}>
                <div>
                    <Button type="primary" onClick={this.toggleCollapsed} style={ButtonStyle}>
                        <Icon type={this.state.collapsed ? 'menu-unfold' : 'menu-fold'} />
                    </Button>
                </div>
                <div>
                    <Menu
                        defaultSelectedKeys={['1']}
                        defaultOpenKeys={['sub1']}
                        mode="inline"
                        theme="white"
                        inlineCollapsed={this.state.collapsed}
                        style={MenuStyle}
                    >
                        <Tabs defaultActiveKey="1">

                            <TabPane tab="Interest" key="1">
                                <ListInterests
                                    data = {this.state.data}
                                    clickLiked = {this.clickLiked}
                                />
                            </TabPane>
                            <TabPane tab="Route" key="2">
                                <ListInterests
                                    data = {favorite}
                                    clickLiked = {this.clickLiked}
                                />
                            </TabPane>
                            <TabPane tab="SaveRoute" key="3">Tab 3</TabPane>

                        </Tabs>
                    </Menu>
                </div>

            </div>
        );
    }
}
