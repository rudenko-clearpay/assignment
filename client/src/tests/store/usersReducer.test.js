import {loadUsers, updateUsers} from "../../store/actions";
import {usersReducer} from "../../store/reducers/usersReducer";

describe('User reducer test', () => {
    const initialState = {data: new Map(), page: null};
    const testUser = {id: "someId", fullName: "Mark"};
    const testPage = {some: "data"};
    const usersDataMap = new Map(Object.entries({someId: testUser}));

    it('test initial state', () => {
        expect(usersReducer(undefined, {})).toStrictEqual(initialState);
    })

    it('loadUsers action changes isAdmin value', () => {
        const newState = usersReducer(undefined, loadUsers({data: [testUser], page: testPage}));

        expect(newState.data).toStrictEqual(usersDataMap);
        expect(newState.page).toStrictEqual(testPage);
    })

    it('updateUsers action overwrites existing users', () => {
        const updatedUser = {id: "someId", fullName: "Tomas"};
        const newState = usersReducer({data: usersDataMap, page: testPage}, updateUsers({data: [updatedUser]}));

        const updatedMap = new Map(Object.entries({someId: updatedUser}));
        expect(newState.data).toStrictEqual(updatedMap);
        expect(newState.page).toBe(testPage);
    })
});

