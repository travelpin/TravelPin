import React from 'react'
import {Button, Collapse, List} from 'antd';
import {connect} from "react-redux";
const Panel = Collapse.Panel;
const google = window.google
class Arrangement extends React.Component{
    state = {
        origin: "",
        destination:"",
        waypoints: [],
    }
    componentDidMount() {
        console.log(this.props)
    }

    showRouteOnMap = () => {
        let interests = this.props.interests
        if (interests.length == 1){
            this.showMarker()
        }else{
            // let origin = interests[0]
            let origin = new google.maps.LatLng(40.71, -74)
            // let destination = interests[interests.length-1]
            let destination = new google.maps.LatLng(41.8525800, -74)
            // let waypoints = interests.slice(1,interests.length-1)
            let waypoints = [{
                            location:'40.9525800, -74',
                            stopover: true,

                        },{
                            location:'41.2525800, -74',
                            stopover: true,
                        }]
            this.getDirections(origin, destination, waypoints)
            console.log("showRouteOnMap with interests > 1" + interests)
            this.setState({
                origin: "xxxxxxx",
                destination: destination,
                waypoints: waypoints
            })
            console.log(this.state)
        }


    }
    showMarker = () =>{
        console.log("just show the location")
    }


    getDirections = (origin, destination, waypoints) => {
        const DirectionsService = new google.maps.DirectionsService();

        DirectionsService.route({
            origin: origin,
            destination: destination,
            travelMode: google.maps.TravelMode.DRIVING,
            waypoints: waypoints,
        }, (result, status) => {
            if (status === google.maps.DirectionsStatus.OK) {
                // this.setState({
                //     directions: result,
                // });
                console.log(this.props.onShowDirections(result));
                console.log(result);
            } else {
                console.error(`error fetching directions ${result}`);
            }
        })
    }
    showInterests = () => {

    }
    render() {
       return(
           <div className={"arrangement"}>
           <Button type={"primary"} onClick={this.showRouteOnMap}>Show Route</Button>
           <List
            itemLayout="horizontal"
            dataSource={this.props.interests}
            renderItem={item => (
                    <Panel header="Day 1" key="1">
                        {item}
                    </Panel>
            )}
        />
           </div>
       );
    }
}

const mapStateToProps = state => {
    return {
        directions: state.directions
    };
};

const mapDispathchToProps = dispatch => {
    return {
        onShowDirections : (directions) => dispatch({type:"Directions", data:directions})
    };
}

export default connect(mapStateToProps, mapDispathchToProps)(Arrangement);