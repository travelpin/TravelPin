import {Spin} from 'antd'
import React from 'react'


export class Loading extends React.Component{
    constructor(props){
        super(props)
        this.loadingSize = props.loadingSize;
    }

    state={
        confirmLoading:false,

    }

    render(){


        return (
            <Spin size={this.loadingSize}/>
        );
    }
}