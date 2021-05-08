import {SET_IS_ADMIN} from "./actionTypes"

const initialState = {isAdmin: false}

export const adminReducer = (state = initialState, action) => {
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