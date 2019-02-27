import { Menu, Icon, Button } from 'antd';
import React from 'react';
import { Tabs, List } from 'antd';
import {ListInterests} from "./ListInterests";
import {ShowRoute} from "./ShowRoute";
import {UserSavedRoutes} from "./UserSavedRoutes";

const TabPane = Tabs.TabPane;

const SubMenu = Menu.SubMenu;


export class LeftExpansionPanel extends React.Component {
    state = {

        collapsed: false,
        pixelPosition: '400px'
    }



    clickLiked = (id) => {
        let data = this.props.data;
        for (let i = 0; i < data.length; i++) {
            if (data[i].location_id === id) {
                if (! data[i].liked) {
                    data[i].liked = "TRUE";
                } else {
                    data[i].liked = data[i].liked === 'FALSE' ? "TRUE" : "FALSE";
                }
            }
        }
        console.log(data);
        this.props.handleLiked(data);

    }

    clickInterest = (location_id) => {
        let data = this.props.data;
        for (let i = 0; i < data.length; i++) {
            if (data[i].location_id === location_id) {
                this.props.handleCenterChange(data[i]);
            }
        }

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
        const favorite = this.props.data.filter((interest) => interest.liked === 'TRUE');
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
                                    data = {this.props.data}
                                    clickLiked = {this.clickLiked}
                                    clickInterest = {this.clickInterest}
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
