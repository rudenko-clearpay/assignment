import {LOAD_USERS, UPDATE_USER} from './actionTypes'

export const loadUsers = content => ({
    type: LOAD_USERS,
    payload: content
})
export const updateUser = content => ({
    type: UPDATE_USER,
    payload: content
})
