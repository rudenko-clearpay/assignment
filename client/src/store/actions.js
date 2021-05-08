import {LOAD_USERS, UPDATE_USERS} from './actionTypes'

export const loadUsers = content => ({
    type: LOAD_USERS,
    payload: content
})
export const updateUsers = content => ({
    type: UPDATE_USERS,
    payload: content
})
