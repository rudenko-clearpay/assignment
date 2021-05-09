import React, {useEffect} from 'react'
import {useDispatch, useSelector} from 'react-redux';
import {loadUsers as fetchUsers} from "../../api/users";
import {loadUsers} from "../../store/actions";
import UsersList from "../components/UsersList";

const Users = () => {
    const reduxData = useSelector(state => {
        return {
            users: state.users.data,
            page: state.users.page
        }
    });
    const dispatch = useDispatch();

    const loadUsersFromApi = () => {
        const nextPage = reduxData.page ? reduxData.page.number + 1 : 0;
        fetchUsers(nextPage)
            .then(response => dispatch(loadUsers(response)))
            .catch(e => console.log("Failed to load users", e));
    }

    useEffect(() => {
        reduxData.page || loadUsersFromApi();
    }, []);

    return (
        <UsersList loadUsersFunc={loadUsersFromApi}/>
    )
}

export default Users;