import {
    Drawer, List, Avatar, Divider, Col, Row,
} from 'antd';
import React from 'react';
import {WrappedNormalLoginForm} from './Login.js'
import {WrappedRegistrationForm} from './Register.js';

const pStyle = {
    fontSize: 16,
    color: 'rgba(0,0,0,0.85)',
    lineHeight: '24px',
    display: 'block',
    marginBottom: 16,
};

const DescriptionItem = ({ title, content }) => (
    <div
        style={{
            fontSize: 14,
            lineHeight: '22px',
            marginBottom: 7,
            color: 'rgba(0,0,0,0.65)',
        }}
    >
        <p
            style={{
                marginRight: 8,
                display: 'inline-block',
                color: 'rgba(0,0,0,0.85)',
            }}
        >
            {title}:
        </p>
        {content}
    </div>
);

// This part is for user to login, log out, register and view the recommendation history.
export class RightPopUp extends React.Component {
    constructor(props) {
        super(props);
        this.state = { visible: false , Login : false, Register : false}
    }
    showLogin = () => {
        this.setState({
            visible: true,
            Login: true,
            Register: false,
        });
    }

    showRegister = () => {
        this.setState({
            visible: true,
            Login: false,
            Register: true,
        });
    }

    onClose = () => {
        this.setState({
            visible: false,
        });
    };

    render() {
        if(this.state.Login == true) {
            return (
                <div>
                    <div className="right-form">
                        <List
                            dataSource={[
                                {
                                    name: 'Who are u?',
                                }
                            ]}
                            bordered
                            renderItem={item => (
                                <List.Item key={item.id} actions={[<a onClick={this.showLogin}>Login</a>,<a onClick={this.showRegister}>Register</a>]}>
                                    <List.Item.Meta
                                        avatar={
                                            <Avatar src="https://gw.alipayobjects.com/zos/rmsportal/BiazfanxmamNRoxxVxka.png" />
                                        }
                                        title={<a href="https://ant.design/index-cn">{item.name}</a>}

                                    />
                                </List.Item>
                            )}
                        />
                    </div>
                    <Drawer
                        width={450}
                        placement="right"
                        closable={false}
                        onClose={this.onClose}
                        visible={this.state.visible}
                    >
                        <WrappedNormalLoginForm/>
                    </Drawer>
                </div>
            );
        } else {
            return(
                <div>
                    <div className="right-form">
                        <List
                            dataSource={[
                                {
                                    name: 'Who are u?',
                                }
                            ]}
                            bordered
                            renderItem={item => (
                                <List.Item key={item.id} actions={[<a onClick={this.showLogin}>Login</a>,<a onClick={this.showRegister}>Register</a>]}>
                                    <List.Item.Meta
                                        avatar={
                                            <Avatar src="https://gw.alipayobjects.com/zos/rmsportal/BiazfanxmamNRoxxVxka.png" />
                                        }
                                        title={<a href="https://ant.design/index-cn">{item.name}</a>}

                                    />
                                </List.Item>
                            )}
                        />
                    </div>
                    <Drawer
                        width={450}
                        placement="right"
                        closable={false}
                        onClose={this.onClose}
                        visible={this.state.visible}
                    >
                        <WrappedRegistrationForm/>
                    </Drawer>
                </div>
            );
        }



        }

}