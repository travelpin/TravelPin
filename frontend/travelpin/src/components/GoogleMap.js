import React, { Component } from 'react';
import { withScriptjs, withGoogleMap, GoogleMap, withProps, lifecycle, Marker, DirectionsRenderer} from "react-google-maps";
import {connect} from "react-redux";
import {InterestMarker} from "./InterestMarker";

const google = window.google
console.log(window)

class NormalGoogleMap extends Component {
    static defaultProps = {
        defaultZoom: 13
    };
    state = {

    }
    componentWillUpdate(nextProps, nextState, nextContext) {
        console.log("GoogleMap componentWillUpdate called.")
    }
    componentWillReceiveProps(nextProps) {
        console.log("GoogleMap componentWillReceiveProps called.")
    }

    getMapRef = (instance) => {
        this.map = instance;
    }
    render() {

        const directions = this.props.directions;
        console.log(directions)
        return (
            // Important! Always set the container height explicitly
            <div style={{ height: '10px', width: '100%' }}>
                <GoogleMap

                center={this.props.center}
                defaultZoom={this.props.defaultZoom}
                zoom={this.props.zoom}
                ref={this.getMapRef}
                >
                    {directions.directions ? <DirectionsRenderer
                            directions={directions.directions}
                            options={{draggable: true}}
                        /> :
                        null
                    }
                    {
                        this.props.data.map((interest)=> <InterestMarker data={interest} key={interest.location_id}/>)
                    }
                </GoogleMap>
            </div>
        );
    }
}
const mapStateToProps = state => {
    return {
        directions: state.directions
    };
};

export default connect(mapStateToProps)(withScriptjs(withGoogleMap(NormalGoogleMap)));
