import {LOAD_USERS} from "./actionTypes"

const initialState = {data: new Map(), page: null}

export const usersReducer = (state = initialState, action) => {
    switch (action.type) {
        case LOAD_USERS: {
            const users = action.payload.data;
            let flattened = state.data;

            if (users && users.length) {
                flattened = new Map([...state.data]);
                users.forEach(user => flattened.set(user.id, user));
            }
            return {
                ...state,
                data: flattened,
                page: action.payload.page
            }
        }
        default:
            return state;
    }
}