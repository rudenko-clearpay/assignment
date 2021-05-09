import {SET_IS_ADMIN} from "../actionTypes"

export const adminReducerInitialState = {isAdmin: false}

export const adminReducer = (state = adminReducerInitialState, action) => {
    switch (action.type) {
        case SET_IS_ADMIN: {
            return {
                isAdmin: action.payload
            }
        }
        default:
            return state;
    }
}