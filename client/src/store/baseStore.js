import {combineReducers, createStore} from "redux";
import {usersReducer} from "./reducers/usersReducer";
import {loginReducer} from "./reducers/loginReducer";

const store = createStore(
    combineReducers({
            users: usersReducer,
            login: loginReducer
        }
    )
)

export default store;