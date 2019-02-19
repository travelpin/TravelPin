import React from 'react';
import mapIcon from "../asserts/images/mapIcon.svg";

export class TopBar extends React.Component {
    render() {
        return(
            <div className="logo-name">
                <img src={mapIcon} className="top-logo" alt="logo" />
                <span className="logo-name">TravelPin</span>
            </div>
        );
    }
}