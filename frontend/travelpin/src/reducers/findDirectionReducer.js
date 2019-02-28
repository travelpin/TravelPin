const initialState = {
    directions : {}
}

export function findDirectionReducer(state = initialState, action){
    switch(action.type){
        case 'Directions':
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