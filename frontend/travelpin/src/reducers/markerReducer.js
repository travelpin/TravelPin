import {FOCUS, UN_FOCUS} from "./actions";

const initialState = {
    isFocus : false,
    focusedId : "xxx"
}

export const findDirectionReducer = (state = initialState, action) =>{
    switch(action.type){
        case FOCUS:
            return {...state,
                isFocus: true,
                focusedId:action.focusedId
            }
        case UN_FOCUS:
            return {
                ...state,
                isFocus: false,
                focusedId:""
            }
        default:
            return state
    }
}
