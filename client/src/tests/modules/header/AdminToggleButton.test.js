import {render, unmountComponentAtNode} from "react-dom";
import {act} from 'react-dom/test-utils';
import {MemoryRouter} from "react-router-dom";
import configureMockStore from 'redux-mock-store';
import {Provider} from "react-redux";
import React from "react";
import AdminToggleButton from "../../../modules/header/AdminToggleButton";
import {setIsAdmin} from "../../../store/actions";

const mockStore = configureMockStore();

let store;
let container = null;
beforeEach(() => {
    container = document.createElement("div");
    document.body.appendChild(container);
    store = mockStore({users: {}, admin: {}});

});

afterEach(() => {
    unmountComponentAtNode(container);
    container.remove();
    container = null;
});


it("Admin btn click dispatches event", () => {
    act(() => {
        render(
            <Provider store={store}>
                <MemoryRouter initialEntries={['/']}>
                    <AdminToggleButton/>
                </MemoryRouter>
            </Provider>, container);
    });
    expect(container.textContent).toContain("Admin mode");

    act(() => {
        const usersPageLink = document.querySelector('#admin_toggle_btn');
        usersPageLink.dispatchEvent(new MouseEvent("click", {bubbles: true}));
    });

    let actions = store.getActions();
    expect(actions).toStrictEqual([setIsAdmin(true)]);
});