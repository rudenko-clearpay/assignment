import React from 'react'
import {useSelector} from 'react-redux';
import {Accordion, ListGroup} from "react-bootstrap";
import UserRow from "./UserRow";
import LoadMore from "./LoadMore";

const noUsersStub = <ListGroup.Item>No users found</ListGroup.Item>;

const UsersList = (props) => {
    const {loadUsersFunc} = props;
    const reduxData = useSelector(state => {
        return {
            users: state.users.data || new Map(),
            page: state.users.page || {totalPages: 0, number: 0}
        }
    });

    function getUsers() {
        let usersList = [];
        reduxData.users.forEach((user) => {
            return usersList.push(<UserRow user={user} key={"User_row_" + user.id}/>);
        })
        return usersList.length ? usersList : noUsersStub;
    }

    return (
        <div>
            <h2 id="users_list_header">Showing {reduxData.users.size} of {reduxData.page.totalElements} users</h2>
            <Accordion id={"users_list"}>
                {getUsers()}
                {<LoadMore onClick={loadUsersFunc}/>}
            </Accordion>
        </div>

    )
}

export default UsersList;