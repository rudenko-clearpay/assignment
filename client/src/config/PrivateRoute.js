import React from "react";
import {Redirect, Route} from "react-router-dom";
import {useSelector} from "react-redux";

const PrivateRoute = (props) => {
    const condition = useSelector((state) => state.login.isLoggedIn);

    return condition ? (<Route path={props.path} exact={props.exact} component={props.component}/>) :
        (<Redirect to="/home"/>);
};
export default PrivateRoute