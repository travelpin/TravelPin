const initialState = {
    isLoggedIn: false
}

export const authReducer = (state = initialState, action) =>{
    switch(action.type){
        case 'loginSuccess':
            return {...state,
                isLoggedIn: true
            }
        case 'logout':
            return {
                ...state,
                isLoggedIn: false
            }
        default:
            return state
    }
}
