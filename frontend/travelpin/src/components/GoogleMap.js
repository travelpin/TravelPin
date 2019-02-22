import React, { Component } from 'react';
import GoogleMapReact from 'google-map-react';
import { withScriptjs, withGoogleMap, GoogleMap } from "react-google-maps";
import {API_KEY} from '../constants';
import {GOOGLE_MAP_API} from "../secureConstants";
const AnyReactComponent = ({ text }) => <div>{text}</div>;

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
                {/*<GoogleMapReact*/}
                    {/*bootstrapURLKeys={{ key: API_KEY + "T34rUkk".substring(0, 5) }}*/}
                    {/*defaultCenter={this.props.center}*/}
                    {/*defaultZoom={this.props.zoom}*/}
                {/*>*/}
                    {/*<AnyReactComponent*/}
                        {/*lat={59.955413}*/}
                        {/*lng={30.337844}*/}
                        {/*text={'Kreyser Avrora'}*/}
                    {/*/>*/}
                {/*</GoogleMapReact>*/}
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