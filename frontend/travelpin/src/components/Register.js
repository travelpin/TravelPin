import {Form, Input, Button, message, Layout, Menu} from 'antd';
import React from 'react';
import { API_ROOT } from '../secureConstants';
import { Link } from 'react-router-dom';

const { Header, Content, Footer } = Layout;
class RegistrationForm extends React.Component {
    state = {
        confirmDirty: false,
        autoCompleteResult: [],
    };

    handleSubmit = (e) => {
        e.preventDefault();
        this.props.form.validateFieldsAndScroll((err, values) => {
            if (!err) {
                console.log('Received values of form: ', values);
                // send request
                fetch(`${API_ROOT}/signup`, {
                    method: 'POST',
                    body: JSON.stringify({
                        username: values.username,
                        password: values.password,
                    }),
                }).then((response) => {
                    if (response.ok) {
                        return response.text();
                    }
                    throw new Error(response.statusText);
                })
                    .then((data) => {
                        console.log(data);
                        message.success('Registration Succeed!');
                        this.props.history.push('/login');
                    })
                    .catch((e) => {
                        console.log(e);
                        message.error('Registration Failed.');
                    });
            }
        });
    }


    handleConfirmBlur = (e) => {
        const value = e.target.value;
        this.setState({ confirmDirty: this.state.confirmDirty || !!value });
    }

    compareToFirstPassword = (rule, value, callback) => {
        const form = this.props.form;
        if (value && value !== form.getFieldValue('password')) {
            callback('Two passwords that you enter is inconsistent!');
        } else {
            callback();
        }
    }

    validateToNextPassword = (rule, value, callback) => {
        const form = this.props.form;
        if (value && this.state.confirmDirty) {
            form.validateFields(['confirm'], { force: true });
        }
        callback();
    }



    render() {
        const { getFieldDecorator } = this.props.form;

        const formItemLayout = {
            labelCol: {
                xs: { span: 24 },
                sm: { span: 8 },
            },
            wrapperCol: {
                xs: { span: 24 },
                sm: { span: 16 },
            },
        };
        const tailFormItemLayout = {
            wrapperCol: {
                xs: {
                    span: 24,
                    offset: 0,
                },
                sm: {
                    span: 16,
                    offset: 8,
                },
            },
        };


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
                            <Form onSubmit={this.handleSubmit} className="register">
                                <Form.Item
                                    {...formItemLayout}
                                    label="Username"
                                >
                                    {getFieldDecorator('username', {
                                        rules: [{ required: true, message: 'Please input your nickname!', whitespace: true }],
                                    })(
                                        <Input />
                                    )}
                                </Form.Item>
                                <Form.Item
                                    {...formItemLayout}
                                    label="Password"
                                >
                                    {getFieldDecorator('password', {
                                        rules: [{
                                            required: true, message: 'Please input your password!',
                                        }, {
                                            validator: this.validateToNextPassword,
                                        }],
                                    })(
                                        <Input type="password" />
                                    )}
                                </Form.Item>
                                <Form.Item
                                    {...formItemLayout}
                                    label="Confirm Password"
                                >
                                    {getFieldDecorator('confirm', {
                                        rules: [{
                                            required: true, message: 'Please confirm your password!',
                                        }, {
                                            validator: this.compareToFirstPassword,
                                        }],
                                    })(
                                        <Input type="password" onBlur={this.handleConfirmBlur} />
                                    )}
                                </Form.Item>

                                <Form.Item {...tailFormItemLayout}>
                                    <Button type="primary" htmlType="submit">Register</Button>
                                    <p>I already have an account, go back to <Link to="/login">Login</Link></p>
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

export const Register = Form.create({ name: 'register' })(RegistrationForm);