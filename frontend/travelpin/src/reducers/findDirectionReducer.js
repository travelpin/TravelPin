import {DIRECTIONS} from "./actions";

const initialState = {
    directions : {}
}

export const findDirectionReducer = (state = initialState, action) =>{
    switch(action.type){
        case DIRECTIONS:
            return {...state,
                directions:{
                    ...state.directions,
                    directions: action.data
                }
            }
        default:
            return state
    }
}
