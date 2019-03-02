const initialState = {
    isFocus : false,
    focusedId : "xxx"
}

export const findDirectionReducer = (state = initialState, action) =>{
    switch(action.type){
        case 'Focus':
            return {...state,
                isFocus: true,
                focusedId:action.focusedId
            }
        case 'unFocus':
            return {
                ...state,
                isFocus: false,
                focusedId:""
            }
        default:
            return state
    }
}
