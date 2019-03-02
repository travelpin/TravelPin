import React from 'react';
import blank from '../asserts/images/blank_star.png';
import half from '../asserts/images/half_filled_star.png';
import full from '../asserts/images/full_star.png';


export class Star extends React.Component {
    state = {
        num: this.props.rating,
        arr: [1,2,3,4,5]
    }

    render () {
        return (
            <span className="star">
                {
                    this.state.arr.map((ele, index) => {
                        return(
                            <span key={index}>
                                {
                                    ele > this.state.num ? ( ele - this.state.num <= 0.5 ? <span className="star"><img src={half} alt="half star" height={15} width={15}/></span> :
                                                                                            <span className="star"><img src={blank} alt="blank star" height={15} width={15}/></span>
                                        ) : <span className="star"><img src={full} alt="full star" height={15} width={15}/></span>
                                }
                            </span>
                        )
                    })
                }
            </span>
        );
    }
}