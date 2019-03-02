import React , {Component} from "react";
import { Marker, InfoWindow } from 'react-google-maps';
import bluePin from "../asserts/images/blue_pin.png";
import pinkPin from "../asserts/images/pink_pin.png";
import greenPin from "../asserts/images/green_pin.png";
import {connect} from "react-redux";

export class InterestMarker extends Component {
    state = {
        isOpen : false,
    }

    toggleOpen = () => {
        this.setState((prevState) => {
            return {
                isOpen: !prevState.isOpen
            }
        });
    }

    render () {
        const {lat, lng, liked, name, imageUrl, rating, price, location_id } = this.props.data;
        const isLiked = liked === 'TRUE';
        const focusPin = this.props.isFocus && this.props.focusedId === location_id ?{
            url : greenPin,
            scaledSize: new window.google.maps.Size(26, 41),
        }:{
            url : bluePin,
            scaledSize: new window.google.maps.Size(26, 41),
        }
        const icon = isLiked ? {
            url : pinkPin,
            scaledSize: new window.google.maps.Size(26, 41),
        } : focusPin;

        return (
            <Marker
                position = {{lat,lng}}
                icon={icon}
                onMouseOver={this.toggleOpen}
                onMouseOut={this.toggleOpen}
               // onClick={this.toggleOpen}
            >

                {this.state.isOpen ? (
                    <InfoWindow>
                        <div>
                            <img src={imageUrl} alt={name} className="infoWindow-image"/>
                            <div className="info">
                                <p className="interest-name">{name}</p>
                                <div className="rating">{`Rating: ${rating}, Price: ${price}`}</div>
                                <div className="open-hours">{`OPEN TODAY: 08:30 - 17:00`}</div>
                            </div>
                        </div>
                    </InfoWindow>
                ) : null}
            </Marker>
        )
    }
}

const mapStateToProps = state => {
    return {
        isFocus: state.directions,
        focusedId:state.location_id
    };
};

export default connect(mapStateToProps)(InterestMarker);