import React, { Component } from 'react';
import { withScriptjs, withGoogleMap, GoogleMap } from "react-google-maps";
import {GOOGLE_MAP_API} from "../secureConstants";

class NormalGoogleMap extends Component {
    static defaultProps = {
        center: {
            lat: 40.71,
            lng: -74.00
        },
        zoom: 13
    };

    render() {
        return (
            // Important! Always set the container height explicitly
            <div style={{ height: '100vh', width: '100%' }}>
                <GoogleMap
                defaultCenter={this.props.center}
                defaultZoom={this.props.zoom}
                >
                </GoogleMap>
            </div>
        );
    }
}

export const TravelPinGoogleMap = withScriptjs(withGoogleMap(NormalGoogleMap));