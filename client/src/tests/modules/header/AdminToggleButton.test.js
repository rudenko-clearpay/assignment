import {render, unmountComponentAtNode} from "react-dom";
import {act} from 'react-dom/test-utils';
import {MemoryRouter} from "react-router-dom";
import configureMockStore from 'redux-mock-store';
import {Provider} from "react-redux";
import React from "react";
import LogInButton from "../../../modules/header/LogInButton";
import {setIsLoggedIn} from "../../../store/actions";

const mockStore = configureMockStore();

let store;
let container = null;
beforeEach(() => {
    container = document.createElement("div");
    document.body.appendChild(container);
    store = mockStore({users: {}, login: {}});

});

afterEach(() => {
    unmountComponentAtNode(container);
    container.remove();
    container = null;
});


it("Login btn click dispatches event", () => {
    act(() => {
        render(
            <Provider store={store}>
                <MemoryRouter initialEntries={['/']}>
                    <LogInButton/>
                </MemoryRouter>
            </Provider>, container);
    });
    expect(container.textContent).toContain("Logged out");

    act(() => {
        const usersPageLink = document.querySelector('#login_toggle_btn');
        usersPageLink.dispatchEvent(new MouseEvent("click", {bubbles: true}));
    });

    let actions = store.getActions();
    expect(actions).toStrictEqual([setIsLoggedIn(true)]);
});