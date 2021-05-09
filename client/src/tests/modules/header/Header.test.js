import {render, unmountComponentAtNode} from "react-dom";
import {act} from 'react-dom/test-utils';
import {MemoryRouter} from "react-router-dom";
import App from "../../../App";
import configureMockStore from 'redux-mock-store';
import {Provider} from "react-redux";
import React from "react";

const mockStore = configureMockStore();

let store;
let container = null;
beforeEach(() => {
    container = document.createElement("div");
    document.body.appendChild(container);
    store = mockStore({users: {}, login: {}});

});

afterEach(() => {
    // подчищаем после завершения
    unmountComponentAtNode(container);
    container.remove();
    container = null;
});

it("Header links change pages", () => {
    act(() => {
        render(
            <Provider store={store}>
                <MemoryRouter initialEntries={['/']}>
                    <App/>
                </MemoryRouter>
            </Provider>, container);
    });
    expect(container.textContent).toContain("Welcome to the ClearPay Coin!");


    act(() => {
        const usersPageLink = document.querySelector('#users_nav_link');
        usersPageLink.dispatchEvent(new MouseEvent("click", {bubbles: true}));
    });

    expect(document.body.textContent).toContain('Showing 0 of 0 users');

    act(() => {
        const usersPageLink = document.querySelector('#home_nav_link');
        usersPageLink.dispatchEvent(new MouseEvent("click", {bubbles: true}));
    });

    expect(document.body.textContent).toContain('Content');
});

it("Login btn click dispatches event", () => {
    act(() => {
        render(
            <Provider store={store}>
                <MemoryRouter initialEntries={['/']}>
                    <App/>
                </MemoryRouter>
            </Provider>, container);
    });
    expect(container.textContent).toContain("Welcome to the ClearPay Coin!");


    act(() => {
        const usersPageLink = document.querySelector('#users_nav_link');
        usersPageLink.dispatchEvent(new MouseEvent("click", {bubbles: true}));
    });


    expect(document.body.textContent).toContain('Showing 0 of 0 users');

    act(() => {
        const usersPageLink = document.querySelector('#home_nav_link');
        usersPageLink.dispatchEvent(new MouseEvent("click", {bubbles: true}));
    });

    expect(document.body.textContent).toContain('Content');
});