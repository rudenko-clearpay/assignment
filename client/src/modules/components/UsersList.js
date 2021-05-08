import React, {useEffect, useState} from 'react'
import {useDispatch, useSelector} from 'react-redux';
import {loadUsers as fetchUsers} from "../../api/users";
import {loadUsers} from "../../store/actions";
import {Accordion, ListGroup, Table} from "react-bootstrap";
import UserRow from "./UserRow";

const noUsersStub = <ListGroup.Item>No users found</ListGroup.Item>;

const UsersList = () => {
    const dispatch = useDispatch();

    useEffect(() => {
        reduxData.users.size || fetchUsers(reduxData.page)
            .then(response => dispatch(loadUsers(response)))
            .catch(e => console.log("Failed to load users", e));
    }, [])



    const reduxData = useSelector(state => {
        return {
            users: state.users.data || new Map(),
            page: state.users.page || {totalElements: 0, page: 0}
        }
    });


    function getUsers() {
        let usersList = [];
        reduxData.users.forEach((user) => {
            return usersList.push(<UserRow user={user}/>);
        })
        return usersList.length ? usersList : noUsersStub;
    }

    return (
        <div>
            <h2>Showing {reduxData.users.size} of {reduxData.page.totalElements} users</h2>
            <Accordion>
                {getUsers()}
            </Accordion>
        </div>

    )
}

export default UsersList;