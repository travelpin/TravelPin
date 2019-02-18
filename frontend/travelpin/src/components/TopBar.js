import React from 'react';
import map from "../asserts/images/map.svg";

export class TopBar extends React.Component {
    render() {
        return(
            <div className="logo-name">
                <img src={map} className="top-logo" alt="logo" />
                <span className="logo-name">TravelPin</span>
            </div>
        );
    }
}