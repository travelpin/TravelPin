import React from 'react'
import {Button, Collapse, List} from 'antd';
import Arrangement from "./Arrangement";

const Panel = Collapse.Panel;

const text = (
    <p style={{ paddingLeft: 24 }}>
        A dog is a type of domesticated animal.
        Known for its loyalty and faithfulness,
        it can be found as a welcome guest in many households across the world.
    </p>
);

export class PlanPanel extends React.Component {



    render() {
        return (<List
            itemLayout="horizontal"
            dataSource={this.props.plan}
            renderItem={item => (
                <Collapse bordered={false} defaultActiveKey={['1']}>
                    <Panel header="This is panel header 1" key="1">

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

