import React from 'react';
import {Form, Icon, Input, Button, message, Layout, Menu} from 'antd';
import { API_ROOT } from '../secureConstants';
import { Link } from 'react-router-dom';

const { Header, Content, Footer } = Layout;
class NormalLoginForm extends React.Component {
    handleSubmit = (e) => {
        e.preventDefault();
        this.props.form.validateFields((err, values) => {
            if (!err) {
                console.log('Received values of form: ', values);
                fetch(`${API_ROOT}/login`, {
                    method: 'POST',
                    body: JSON.stringify({
                        username: values.username,
                        password: values.password,
                    }),
                }).then((response) => {
                    console.log("response" + response);
                    if (response.ok) {
                        return response.text();
                    }
                    throw new Error(response.statusText);
                }).then((data) => {
                    message.success('Login Success!');
                    this.props.handleSuccessfulLogin(data);
                    console.log(data);
                }).catch((e) => {
                    console.log(e);
                    message.error('Login Failed.');
                });
            }
        });
    }

    render() {
        const { getFieldDecorator } = this.props.form;
        return (
            <Layout className="layout">
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
                <div>
                    <Content className={"mapStyle"}>
                        <div style={{ background: '#fff', padding: 1, minHeight: 1000 }}>
                            <Form onSubmit={this.handleSubmit} className="login-form">
                                <Form.Item>
                                    {getFieldDecorator('username', {
                                        rules: [{ required: true, message: 'Please input your username!' }],
                                    })(
                                        <Input prefix={<Icon type="user" style={{ color: 'rgba(0,0,0,.25)' }} />} placeholder="Username" />
                                    )}
                                </Form.Item>
                                <Form.Item>
                                    {getFieldDecorator('password', {
                                        rules: [{ required: true, message: 'Please input your Password!' }],
                                    })(
                                        <Input prefix={<Icon type="lock" style={{ color: 'rgba(0,0,0,.25)' }} />} type="password" placeholder="Password" />
                                    )}
                                </Form.Item>
                                <Form.Item>
                                    <Button type="primary" htmlType="submit" className="login-form-button">
                                        Log in
                                    </Button>
                                    Or <Link to="/register">register now!</Link>
                                </Form.Item>
                            </Form>
                        </div>
                    </Content>
                </div>

                <Footer style={{ textAlign: 'center' }}>
                </Footer>
            </Layout>
        );
    }
}

export const Login = Form.create({ name: 'normal_login' })(NormalLoginForm);