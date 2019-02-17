import { List } from 'antd';
import React from 'react';



export class ListInterests extends React.Component{


    render(){
        const data = [
            'Racing car sprays burning fuel into crowd.',
            'Japanese princess to wed commoner.',
            'Australian walks 100km after outback crash.',
            'Man charged over missing wedding girl.',
            'Los Angeles battles huge wildfires.',
        ];
        return(
            <div>
                <h3 style={{ marginBottom: 16 }}>Default Size</h3>
                <List
                    header={<div>Header</div>}
                    footer={<div>Footer</div>}
                    bordered
                    dataSource={data}
                    renderItem={item => (<List.Item>{item}</List.Item>)}
                    className={"ListInterests"}
                />
            </div>
        );
    }
}





