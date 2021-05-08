import {LOAD_USERS, UPDATE_USERS} from "./actionTypes"

const initialState = {data: new Map(), page: null}

function flattenUsersArray(state, users) {
    let flattened = state.data;

    if (users && users.length) {
        flattened = new Map([...state.data]);
        users.forEach(user => flattened.set(user.id, user));
    }
    return flattened;
}

export const usersReducer = (state = initialState, action) => {
    switch (action.type) {
        case LOAD_USERS: {
            const users = action.payload.data;
            const flattened = flattenUsersArray(state, users);
            return {
                ...state,
                data: flattened,
                page: action.payload.page
            }
        }
        case UPDATE_USERS: {
            const users = action.payload.data;
            debugger
            const flattened = flattenUsersArray(state, users);
            return {
                ...state,
                data: flattened
            }
        }
        default:
            return state;
    }
}