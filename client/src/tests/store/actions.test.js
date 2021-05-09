import {loadUsers, setIsLoggedIn, updateUsers} from "../../store/actions";
import {LOAD_USERS, SET_IS_LOGGED_IN, UPDATE_USERS} from "../../store/actionTypes";

describe('Actions test', () => {
    const testUser = {id: "someId", fullName: "Mark"};
    const testPage = {some: "data"};

    it('test loadUsers returned value', () => {
        const content = {data: [testUser], page: testPage};
        expect(loadUsers(content)).toStrictEqual({type: LOAD_USERS, payload: content});
    })
    it('test updateUsers returned value', () => {
        const content = [testUser];
        expect(updateUsers(content)).toStrictEqual({type: UPDATE_USERS, payload: content});
    })

    it('test setLoggedIn returned value', () => {
        const content = true;
        expect(setIsLoggedIn(content)).toStrictEqual({type: SET_IS_LOGGED_IN, payload: content});
    })

});

