import { Menu, Icon, Button } from 'antd';
import React from 'react';
import { Tabs, List } from 'antd';
import {ListInterests} from "./ListInterests";

const TabPane = Tabs.TabPane;

const SubMenu = Menu.SubMenu;

export class LeftExpansionPanel extends React.Component {
    state = {
        collapsed: false,
    }

    toggleCollapsed = () => {
        this.setState({
            collapsed: !this.state.collapsed,
        });
    }

    render() {
        return (

            <div  className={"leftExpansionPanel"}>
                <div>
                    <Menu
                        defaultSelectedKeys={['1']}
                        defaultOpenKeys={['sub1']}
                        mode="inline"
                        theme="white"
                        inlineCollapsed={this.state.collapsed}
                    >
                        <Tabs defaultActiveKey="1">
                            <TabPane tab="Tab 1" key="1"><ListInterests/></TabPane>
                            <TabPane tab="Tab 2" key="2">Tab 2</TabPane>
                            <TabPane tab="Tab 3" key="3">Tab 3</TabPane>
                        </Tabs>
                    </Menu>
                </div>
               <div>
                   <Button type="primary" onClick={this.toggleCollapsed} style={{ marginBottom: 8}}>
                       <Icon type={this.state.collapsed ? 'menu-unfold' : 'menu-fold'} />
                   </Button>
               </div>
            </div>
        );
    }
}
