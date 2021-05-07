import {combineReducers, createStore} from "redux";
import {usersReducer} from "./usersReducer";

const store = createStore(
    combineReducers({
            users: usersReducer
        }
    )
)

export default store;