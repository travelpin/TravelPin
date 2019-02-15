import { Menu, Icon, Button } from 'antd';
import React from 'react';
import { Tabs, List } from 'antd';
import {ListInterests} from "./ListInterests";

const TabPane = Tabs.TabPane;

const SubMenu = Menu.SubMenu;

export class LeftExpansionPanel extends React.Component {
    state = {
        collapsed: false,
        pixelPosition: '400px'
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
                            <TabPane tab="Interest" key="1"><ListInterests/></TabPane>
                            <TabPane tab="Route" key="2">Tab 2</TabPane>
                            <TabPane tab="SaveRoute" key="3">Tab 3</TabPane>
                        </Tabs>
                    </Menu>
                </div>

            </div>
        );
    }
}
