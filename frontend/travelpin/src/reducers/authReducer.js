import {LOGIN_SUCCESS, LOGOUT} from "./actions";

const initialState = {
    isLoggedIn: false
}

export const authReducer = (state = initialState, action) =>{
    switch(action.type){
        case LOGIN_SUCCESS:
            return {...state,
                isLoggedIn: true
            }
        case LOGOUT:
            return {
                ...state,
                isLoggedIn: false
            }
        default:
            return state
    }
}
