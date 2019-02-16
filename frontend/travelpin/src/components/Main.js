import React from 'react';
import {Layout, Menu} from 'antd';
import {SimpleMap} from "./GoogleMap";
import {List} from "antd/lib/list";
import {RightPopUp} from "./RightPopUp";
import {LeftExpansionPanel} from "./LeftExpansionPanel/LeftExpansionPanel"
const { Header, Content, Footer } = Layout;

// the container of the left-pop-up window, right-pop-up window, navbar, and the footer
export class Main extends React.Component {
    render() {
        return (
            <Layout className="layout">

                <RightPopUp/>

                <Header>
                    <div className="logo" >

                    </div>
                    {/*<div>Icons made by <a href="https://www.flaticon.com/authors/smashicons" title="Smashicons">Smashicons</a> from <a href="https://www.flaticon.com/" 			    title="Flaticon">www.flaticon.com</a> is licensed by <a href="http://creativecommons.org/licenses/by/3.0/" 			    title="Creative Commons BY 3.0" target="_blank">CC 3.0 BY</a></div>*/}
                    <Menu
                        theme="dark"
                        mode="horizontal"
                        defaultSelectedKeys={['2']}
                        style={{ lineHeight: '64px' }}
                    >
                        <Menu.Item key="1" className="menu-item">nav 1</Menu.Item>
                        <Menu.Item key="2" className="menu-item">nav 2</Menu.Item>
                        <Menu.Item key="3" className="menu-item">nav 3</Menu.Item>
                    </Menu>
                </Header>
                <LeftExpansionPanel/>
                <div>
                    <Content className={"mapStyle"}>
                        <div style={{ background: '#fff', padding: 1, minHeight: 1000 }}>
                            <SimpleMap/>
                        </div>
                    </Content>
                </div>



                <Footer style={{ textAlign: 'center' }}>

                </Footer>
            </Layout>

        )
    }
}