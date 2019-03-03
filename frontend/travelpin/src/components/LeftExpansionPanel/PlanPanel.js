import React from 'react'
import {Button, Collapse, List} from 'antd';
import Arrangement from "./Arrangement";

const Panel = Collapse.Panel;

export class PlanPanel extends React.Component {

    render() {
        return (<List
            itemLayout="horizontal"
            dataSource={this.props.plan}
            renderItem={(item,index) => (
                <Collapse bordered={false} defaultActiveKey={['1']}>
                    <Panel header={`Plan of Day ${index+1}`} key={`${index}`}>
                        <Arrangement
                            interests={item}
                            directions={this.props.directions}
                        />
                    </Panel>
                </Collapse>
            )}
        />)

    }
}

