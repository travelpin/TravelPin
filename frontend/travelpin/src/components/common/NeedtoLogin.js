import React from 'react'
import {Button, Link} from 'antd'

export class NeedtoLogin extends React.Component{
    state={

    }
    render(){
        return (
            <div className={"NeedtoLogin"}>
                <Button type={"primary"}>Login</Button>
                <p>Don't have account? <a href={"/register"}>Register</a></p>
            </div>
        );
    }
}