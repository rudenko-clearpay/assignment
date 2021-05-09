import React from 'react'
import {ButtonGroup, Form, Nav, Navbar} from 'react-bootstrap';
import {useHistory} from "react-router-dom";
import LogInButton from "./LogInButton";
import {useSelector} from "react-redux";


const Header = () => {
    const history = useHistory();
    const isLoggedIn = useSelector((state) => state.login.isLoggedIn);

    function getNavigationHandler(path) {
        return () => history.push(path);
    }

    return (
        <Navbar bg="light" expand="lg">
            <Navbar.Brand href="#home">Clearpay Coin</Navbar.Brand>
            <Navbar.Toggle aria-controls="basic-navbar-nav"/>
            <Navbar.Collapse id="basic-navbar-nav">
                <Nav className="mr-auto">
                    <Nav.Link id="home_nav_link" onClick={getNavigationHandler("/home")}>Home</Nav.Link>
                    <Nav.Link id="users_nav_link" onClick={getNavigationHandler("/users")}>Users</Nav.Link>
                    {isLoggedIn && <Nav.Link id="transfer_nav_link" onClick={getNavigationHandler("/transfer")}>Transfer</Nav.Link>}
                </Nav>
                <Form inline>
                    <ButtonGroup toggle className="mb-2">
                        <LogInButton/>
                    </ButtonGroup>
                </Form>
            </Navbar.Collapse>
        </Navbar>
    )
}

export default Header;