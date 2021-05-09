import {SET_IS_LOGGED_IN} from "../actionTypes"

export const loginReducerInitialState = {isLoggedIn: false}

export const loginReducer = (state = loginReducerInitialState, action) => {
    switch (action.type) {
        case SET_IS_LOGGED_IN: {
            return {
                isLoggedIn: action.payload
            }
        }
        default:
            return state;
    }
}