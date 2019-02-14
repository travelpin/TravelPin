import React from "react";
import {Popover, Button,Breadcrumb, Alert, List, Collapse, Tabs} from "antd";
import {ListInterests} from "./ListInterests";
import {SetDays} from "./SetDays";

const TabPane = Tabs.TabPane;
const panel = Collapse.Panel;


export class LeftPopover extends React.Component{
    content = (
        <Tabs defaultActiveKey="1" onChange={this.callback}>
            <TabPane tab="Tab 1" key="1">Content of Tab Pane 1</TabPane>
            <TabPane tab="Tab 2" key="2">Content of Tab Pane 2</TabPane>
            <TabPane tab="Tab 3" key="3">Content of Tab Pane 3</TabPane>
        </Tabs>
    );
    callback = (key) => {
        console.log(key)
    }


    render(){
        return (
            <Popover className="leftpanel" content={this.content} trigger="click" placement="bottom">
                <Button>Find Your Interests</Button>
            </Popover>
        )
    }
}

