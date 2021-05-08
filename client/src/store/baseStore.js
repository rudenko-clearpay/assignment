import {combineReducers, createStore} from "redux";
import {usersReducer} from "./usersReducer";
import {adminReducer} from "./adminReducer";

const store = createStore(
    combineReducers({
            users: usersReducer,
            admin: adminReducer
        }
    )
)

export default store;