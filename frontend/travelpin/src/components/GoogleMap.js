import React, { Component } from 'react';
import { withScriptjs, withGoogleMap, GoogleMap, withProps, lifecycle, DirectionsRenderer} from "react-google-maps";
import {connect} from "react-redux";

const google = window.google
console.log(window)

class NormalGoogleMap extends Component {
    static defaultProps = {
        center: {
            lat: 40.71,
            lng: -74.00
        },
        zoom: 13
    };
    state = {

    }
    // componentDidMount() {
    //     const DirectionsService = new google.maps.DirectionsService();
    //
    //     DirectionsService.route({
    //         origin: new google.maps.LatLng(40.71, -74),
    //         destination: new google.maps.LatLng(41.8525800, -74),
    //         travelMode: google.maps.TravelMode.DRIVING,
    //         waypoints: [{
    //             location:'40.9525800, -74',
    //             stopover: true,
    //
    //         },{
    //             location:'41.2525800, -74',
    //             stopover: true,
    //         }]
    //     }, (result, status) => {
    //         if (status === google.maps.DirectionsStatus.OK) {
    //             this.setState({
    //                 directions: result,
    //             });
    //             console.log("In componentDidMount : " + this.state);
    //         } else {
    //             console.error(`error fetching directions ${result}`);
    //         }
    //     })
    // }

    render() {
        console.log(this.props)
        const directions = this.state.directions;
        return (
            // Important! Always set the container height explicitly
            <div style={{ height: '10px', width: '100%' }}>
                <GoogleMap
                defaultCenter={this.props.center}
                defaultZoom={this.props.zoom}
                >
                    {/*{directions ? <DirectionsRenderer*/}
                            {/*directions={directions}*/}
                            {/*options={{draggable: true}}*/}
                        {/*/> :*/}
                        {/*null*/}
                    {/*}*/}
                </GoogleMap>
            </div>
        );
    }
}
export const TravelPinGoogleMap = withScriptjs(withGoogleMap(NormalGoogleMap));