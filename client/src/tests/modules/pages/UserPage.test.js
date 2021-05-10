import {render, unmountComponentAtNode} from "react-dom";
import {act} from 'react-dom/test-utils';
import {MemoryRouter} from "react-router-dom";
import configureMockStore from 'redux-mock-store';
import * as redux from "react-redux";
import {Provider} from "react-redux";
import React from "react";
import {userReducerInitialState} from "../../../store/reducers/usersReducer";
import {loginReducerInitialState} from "../../../store/reducers/loginReducer";
import Users from "../../../modules/pages/Users";
import {loadUsers} from "../../../store/actions";

const mockStore = configureMockStore();

let store;
let container = null;
beforeEach(() => {
    container = document.createElement("div");
    document.body.appendChild(container);
    store = mockStore({users: userReducerInitialState, login: loginReducerInitialState});
});

afterEach(() => {
    unmountComponentAtNode(container);
    container.remove();
    container = null;
});


it("User page loads users on mount", async () => {
    const testUser = {id: "someId", fullName: "Mark", wallets: [{id: "wallet1", balance: "123"}]};
    const testPage = {
        "size": 20,
        "totalElements": 2,
        "totalPages": 1,
        "number": 0
    };

    jest.spyOn(global, "fetch").mockImplementation(() =>
        Promise.resolve({
            json: () => Promise.resolve({
                "_embedded": {
                    "users": [testUser]
                },
                page: testPage
            })
        })
    );

    await act(async () => {
        render(
            <Provider store={store}>
                <MemoryRouter initialEntries={['/users']}>
                    <Users/>
                </MemoryRouter>
            </Provider>, container);
    });

    let actions = store.getActions();
    expect(actions).toStrictEqual([loadUsers({
        data: [testUser],
        page: testPage
    })]);
});

it("User page loads users on mount", () => {
    const testUser = {id: "someId", fullName: "Mark", wallets: [{id: "wallet1", balance: "123"}]};
    const testUser2 = {id: "someId2", fullName: "Mark2", wallets: [{id: "wallet2", balance: "123"}]};
    const testUsersMap = new Map();
    testUsersMap.set(testUser.id, testUser);
    testUsersMap.set(testUser2.id, testUser2);

    const testPage = {
        "size": 20,
        "totalElements": 2,
        "totalPages": 1,
        "number": 0
    };

    const useSelectorMock = jest.spyOn(redux, 'useSelector')
    useSelectorMock.mockReturnValue({
            users: testUsersMap,
            page: testPage
        });

    act(() => {
        render(
            <Provider store={store}>
                <MemoryRouter initialEntries={['/users']}>
                    <Users/>
                </MemoryRouter>
            </Provider>, container);
    });
    let usersListHeader = container.querySelector('#users_list_header');
    let usersList = container.querySelector('#users_list');

    expect(usersList.children.length).toBe(3);
    expect(usersListHeader.textContent).toBe("Showing 2 of 2 users");
});