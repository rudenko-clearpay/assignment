import React, {useEffect} from 'react'
import {useDispatch, useSelector} from 'react-redux';
import {loadUsers as fetchUsers} from "../../api/users";
import {loadUsers} from "../../store/actions";
import UsersList from "../components/UsersList";
import {Col, Row} from "react-bootstrap";

const Users = () => {
    const reduxData = useSelector(state => {
        return {
            users: state.users.data,
            page: state.users.page
        }
    });
    const dispatch = useDispatch();

    useEffect(() => {
        reduxData.users || fetchUsers(reduxData.page)
            .then(response => dispatch(loadUsers(response)))
            .catch(e => console.log("Failed to load users", e));
    }, []);

    return (
        <UsersList/>
    )
}

export default Users;