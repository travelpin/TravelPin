import {Menu, Icon, Button, Spin} from 'antd';
import React from 'react';
import { Tabs, List } from 'antd';
import {ListInterests} from "./ListInterests";
import { Collapse } from 'antd';
import {connect} from "react-redux";
import {Loading} from "../common/Loading";
import {PlanPanel} from "./PlanPanel";

const TabPane = Tabs.TabPane;

const SubMenu = Menu.SubMenu;
const fakeDataUrl = 'https://randomuser.me/api/?results=5&inc=name,gender,email,nat&noinfo';
const DataUrl = "http://localhost:8080/listinterests"

export class LeftExpansionPanel extends React.Component {
    state = {
        data: [],
        collapsed: false,
        pixelPosition: '400px',
        plan:[["New York City Fire Museum","The Public Theater","Angelika Film Center & Café - New York"]
        ,["IPIC New York City","Jane's Carousel"]
        ,["National Museum of the American Indian","9/11 Tribute Museum","Regal Cinemas Battery Park 11","The Public Theater"]]
    }


    componentDidMount() {
        this.loadInterests();
    }

    clickLiked = (id) => {
        let data = this.state.data;
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

    showPlan = () => {
        const {error, isLoadingPlan, plan} = this.state;
        if(error){
            return <div>{error}</div>
        } else if (isLoadingPlan) {
            return <Spin tip="Loading posts..." />;
        } else if (plan && plan.length > 0 ){
            return this.getPlan();
        } else {
            return <div>No Plan.</div>
        }
    }
    getPlan = () => {
        const {plan} = this.state;
        console.log(plan)
        return <PlanPanel
            plan={plan}
            directions={this.props.directions}
        />
    }


    render() {
        // const {directions} = this.props.directions;
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
        console.log(favorite)
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
                            <TabPane tab="ShowPlan" key="3">{this.showPlan()}</TabPane>

                        </Tabs>
                    </Menu>
                </div>

            </div>
        );
    }
}
