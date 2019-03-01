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

    showRouteOnMap = (interests) => {

        if (interests.length == 1){
            this.showMarker()
        }else{
            // let origin = interests[0]
            let origin = new google.maps.LatLng(interests[0].lat, interests[0].lng)
            // let destination = interests[interests.length-1]
            let destination = interests.length > 2 ? new google.maps.LatLng(interests[2].lat, interests[2].lng):new google.maps.LatLng(interests[1].lat, interests[1].lng)
            let waypoints = interests.length > 2 ?[{
                location:`${interests[1].lat},${interests[1].lng}`,
                stopover:true,
            }]:[];//interests.slice(1,interests.length-1).
            // let waypoints = [{
            //                 location:'40.9525800, -74',
            //                 stopover: true,
            //
            //             },{
            //                 location:'41.2525800, -74',
            //                 stopover: true,
            //             }]
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
        const interests = this.props.interests;
       return(
           <div className={"arrangement"}>
           <Button type={"primary"} onClick={()=>{this.showRouteOnMap(interests)}}>Show Route</Button>
           <List
            itemLayout="horizontal"
            dataSource={interests}
            renderItem={(item,index) => (
                    <Panel header={`${item.name}`} key={`${index}`}>
                        {item.name}
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