import {LOAD_USERS, SET_IS_LOGGED_IN, UPDATE_USERS} from './actionTypes'

export const loadUsers = content => ({
    type: LOAD_USERS,
    payload: content
})
export const updateUsers = content => ({
    type: UPDATE_USERS,
    payload: content
})
export const setIsLoggedIn = content => ({
    type: SET_IS_LOGGED_IN,
    payload: content
})
