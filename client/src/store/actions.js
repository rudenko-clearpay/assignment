import {LOAD_USERS, SET_IS_ADMIN, UPDATE_USERS} from './actionTypes'

export const loadUsers = content => ({
    type: LOAD_USERS,
    payload: content
})
export const updateUsers = content => ({
    type: UPDATE_USERS,
    payload: content
})


export const setIsAdmin = content => ({
    type: SET_IS_ADMIN,
    payload: content
})
