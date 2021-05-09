import {useDispatch, useSelector} from "react-redux";
import React from 'react'
import {ToggleButton} from 'react-bootstrap';
import {setIsLoggedIn} from "../../store/actions";


const LogInButton = () => {
    const isLoggedIn = useSelector(state => {
        return state.login.isLoggedIn
    });
    const dispatch = useDispatch();

    function handleChange(e) {
        dispatch(setIsLoggedIn(e.currentTarget.checked));
    }

    return (
        <ToggleButton type="checkbox" variant={isLoggedIn ? "success" : "secondary"} checked={isLoggedIn}
                      onChange={handleChange} value="1"
                      className="mr-sm-2"
                      id="login_toggle_btn">
            {isLoggedIn ? "Logged in" : "Logged out"}
        </ToggleButton>
    )
};

export default LogInButton;