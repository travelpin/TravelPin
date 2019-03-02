import React from 'react';
import { List, message, Avatar, Spin, Icon} from 'antd';
import InfiniteScroll from 'react-infinite-scroller';
import {connect} from "react-redux";



export class ListInterests extends React.Component {
    state = {
        loading: false,
        hasMore: true,
    }



    handleInfiniteOnLoad = () => {
        let data = this.props.data;
        this.setState({
            loading: true,
        });
        if (data.length > 10) {
            message.warning('Infinite List loaded all');
            this.setState({
                hasMore: false,
                loading: false,
            });
            return;
        }
        // this.loadInterests();
    }

    render() {
        return (
            <div className="interests-list-container">
                <InfiniteScroll
                    initialLoad={false}
                    pageStart={0}
                    loadMore={this.handleInfiniteOnLoad}
                    hasMore={!this.state.loading && this.state.hasMore}
                    useWindow={false}
                >
                    <List
                        dataSource={this.props.data}
                        renderItem={(item, index) => (
                            <List.Item key={index}
                            >
                                <List.Item.Meta
                                    hoverable
                                    avatar={<Avatar src={item.imageUrl}/>}
                                    title={<div style={{ textAlign : "left", width : "200px"}}>
                                        <button
                                            className="liked-button"
                                            onClick = {() => { this.props.clickInterest(item.location_id)}}
                                            style={{width : "100%"}}
                                        >
                                            <a  style={{ textAlign : "left"}}>{item.name}</a>
                                        </button>
                                    </div>}
                                    description={<div style={{marginLeft : 10, width : "200px"}}>
                                        {item.formattedAddress}
                                    </div>}
                                />

                                <div>
                                    <button
                                        className="liked-button"
                                        onClick = {() => { this.props.clickLiked(item.location_id)}}
                                    >
                                        {item.liked === 'TRUE' ? <Icon type="like" theme="filled" /> : <Icon type="like"/>}
                                    </button>
                                </div>

                            </List.Item>
                        )}
                    >
                        {this.state.loading && this.state.hasMore && (
                            <div className="interests-loading-container">
                                <Spin />
                            </div>
                        )}
                    </List>
                </InfiniteScroll>
            </div>
        );
    }
}

const mapStateToProps = state => {
    return {
        isFocus: state.directions,
        focusedId:state.location_id
    };
};

const mapDispathchToProps = dispatch => {
    return {
        focusClick : (directions) => dispatch({type:"Directions", data:directions})
    };
}

export default connect(mapStateToProps, mapDispathchToProps)(ListInterests);

