import {useDispatch, useSelector} from "react-redux";
import React from 'react'
import {ToggleButton} from 'react-bootstrap';
import {setIsAdmin} from "../../store/actions";


const AdminToggleButton = () => {
    const isAdmin = useSelector(state => {
        return state.admin.isAdmin
    });
    const dispatch = useDispatch();

    function handleChange(e) {
        dispatch(setIsAdmin(e.currentTarget.checked));
    }

    return (
        <ToggleButton type="checkbox" variant={isAdmin ? "success" : "secondary"} checked={isAdmin}
                      onChange={handleChange} value="1"
                      className="mr-sm-2">
            Admin mode
        </ToggleButton>
    )
}

export default AdminToggleButton;