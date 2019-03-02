import {Menu, Icon, Button, Spin} from 'antd';
import React from 'react';
import { Tabs, List, Slider, InputNumber, Row, Col } from 'antd';
import {ListInterests} from "./ListInterests";
import { Collapse } from 'antd';
import {connect} from "react-redux";
import {Loading} from "../common/Loading";
import {PlanPanel} from "./PlanPanel";
import {API_ROOT} from "../../secureConstants"

const TabPane = Tabs.TabPane;

const SubMenu = Menu.SubMenu;
const fakeDataUrl = 'https://randomuser.me/api/?results=5&inc=name,gender,email,nat&noinfo';
const DataUrl = "http://localhost:8080/listinterests"


export class LeftExpansionPanel extends React.Component {
    state = {
        days: 1,
        collapsed: false,
        pixelPosition: '400px',
        plan: null
    }


    /**
     *
     * @param location_i {string}
     * @return data change liked field
     */
    clickLiked = (location_id) => {
        let data = this.props.data;
        for (let i = 0; i < data.length; i++) {
            if (data[i].location_id === location_id) {
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


    /**
     *
     * @param location_i {string}
     * @return map change center to the clickedInterest
     */
    clickInterest = (location_id) => {
        let data = this.props.data;
        for (let i = 0; i < data.length; i++) {
            if (data[i].location_id === location_id) {
                this.props.handleCenterChange(data[i]);

            }
        }

    }

    handleSubmit = (e) => {
        e.preventDefault();
        const favLocIds = this.props.data.filter((interest) => interest.liked === 'TRUE')
            .map((fav) => fav.location_id);
        const days = this.state.days
        console.log(favLocIds);

        this.fetchRoute(favLocIds, days);
    }

    /**
     *
     * @param favLocIds {String}
     * @param days {String}
     * @return plans {array} at the same time change the state of plan
     */
    fetchRoute = (favLocIds, days) => {
        fetch(`${API_ROOT}/optimizeroute`, {
                method : 'POST',
                body :JSON.stringify ({
                    days : days,
                    pinnedInterests : favLocIds,
                })
        }).then((response) => {
            console.log(response);
            if (response.ok) {
                return response.json();
            }

            throw new Error(response.statusText);
        }).then((plan) => {
            console.log(plan);
            this.setState({plan});
        }).catch((e) => {
            console.log(e.message);
        })
    }

    toggleCollapsed = () => {
        this.setState({
            collapsed: !this.state.collapsed,
        });
    }

    onChange = (value) => {
        this.setState({
            days: value,
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
        return <PlanPanel
            plan={plan}
            directions={this.props.directions}
        />
    }


    render() {
        // const {directions} = this.props.directions;
        const {days} = this.state;
        const MenuStyle = {
            width:this.state.collapsed?'0px':this.state.pixelPosition,
            height:this.state.collapsed?'0px':this.state.pixelPosition,
        }
        const ButtonStyle = {
            marginBottom: 8,
            position:"fixed",
            marginLeft:this.state.collapsed?'0px':'400px',

        }
        const favorite = this.props.data.filter((interest) => interest.liked === 'TRUE');
        // console.log(favorite)
        return (

            <div  className="leftExpansionPanel">
                <div>
                    <Button
                        type="primary"
                        onClick={this.toggleCollapsed}
                        style={ButtonStyle}
                        className="leftExpansionPanel primary-btn"
                    >
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
                        <Tabs defaultActiveKey="1" style={{width : "100%", overflow: "hidden"}}>

                            <TabPane tab="Interest" key="1">
                                <ListInterests
                                    data = {this.props.data}
                                    clickLiked = {this.clickLiked}
                                    clickInterest = {this.clickInterest}
                                />
                            </TabPane>
                            <TabPane tab="Route" key="2">
                                <div className="days-control-container">
                                            <div className="days-control-container-slider">
                                                <Slider
                                                    min={1}
                                                    max={15}
                                                    onChange={this.onChange}
                                                    value={typeof days === 'number' ? days : 1}
                                                    className="days-control-slider"
                                                />
                                            </div>

                                            <div className="days-control-container-input">
                                                <InputNumber
                                                    min={1}
                                                    max={15}
                                                    style={{ marginLeft: 16 }}
                                                    value={days}
                                                    onChange={this.onChange}
                                                    className="days-control-input"
                                                />

                                            </div>


                                            <div className="days-control-container-btn">
                                                <Button
                                                    className="days-control-btn"
                                                    onClick={this.handleSubmit}
                                                >
                                                    Pin!
                                                </Button>
                                            </div>
                                </div>
                                <ListInterests
                                    data = {favorite}
                                    clickLiked = {this.clickLiked}
                                    clickInterest = {this.clickInterest}
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
