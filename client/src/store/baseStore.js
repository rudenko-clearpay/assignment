import {combineReducers, createStore} from "redux";
import {usersReducer} from "./reducers/usersReducer";
import {adminReducer} from "./reducers/adminReducer";

const store = createStore(
    combineReducers({
            users: usersReducer,
            admin: adminReducer
        }
    )
)

export default store;