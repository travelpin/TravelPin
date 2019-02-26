import React from 'react'
import {Collapse, List} from 'antd';

const Panel = Collapse.Panel;
const google = window.google
const text = (
    <p style={{ paddingLeft: 24 }}>
        A dog is a type of domesticated animal.
        Known for its loyalty and faithfulness,
        it can be found as a welcome guest in many households across the world.
    </p>
);

export class PlanPanel extends React.Component {

    showRouteOnMap = () => {

    }

    getDirections = (origin, destination, waypoints) => {
        const DirectionsService = new google.maps.DirectionsService();

        DirectionsService.route({
            origin: origin,     //new google.maps.LatLng(40.71, -74),
            destination: destination,   //new google.maps.LatLng(41.8525800, -74),
            travelMode: google.maps.TravelMode.DRIVING,
            waypoints: waypoints,
        }, (result, status) => {
            if (status === google.maps.DirectionsStatus.OK) {
                this.setState({
                    directions: result,
                });
                console.log("In componentDidMount : " + this.state);
            } else {
                console.error(`error fetching directions ${result}`);
            }
        })
    }

    render() {
        return (<List
            itemLayout="horizontal"
            dataSource={this.props.plan}
            renderItem={item => (
                <Collapse bordered={false} defaultActiveKey={['1']}>
                    <Panel header="This is panel header 1" key="1">
                        {item}
                    </Panel>
                </Collapse>
            )}
        />)

    }
}

