import {Route, Switch} from "react-router-dom";
import UsersPage from "../modules/pages/Users";
import HomePage from "../modules/pages/Home";
import TransferPage from "../modules/pages/TransferPage";
import PrivateRoute from "./PrivateRoute";

const Routes = () => {
    return (
        <Switch>
            <Route exact path="/">
                <HomePage />
            </Route>
            <Route path="/home">
                <HomePage />
            </Route>
            <Route path="/users">
                <UsersPage />
            </Route>
            <PrivateRoute path="/transfer" component={TransferPage}/>
            <Route path="*">
                <div>No match found</div>
            </Route>
        </Switch>
    )
}

export default Routes;