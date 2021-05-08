import React from 'react'
import {ButtonGroup, Form, Nav, Navbar} from 'react-bootstrap';
import {useHistory} from "react-router-dom";
import AdminToggleButton from "./AdminToggleButton";


const Header = () => {
    const history = useHistory();

    function getNavigationHandler(path) {
        return () => history.push(path);
    }

    return (
        <Navbar bg="light" expand="lg">
            <Navbar.Brand href="#home">Clearpay Coin</Navbar.Brand>
            <Navbar.Toggle aria-controls="basic-navbar-nav"/>
            <Navbar.Collapse id="basic-navbar-nav">
                <Nav className="mr-auto">
                    <Nav.Link><div  onClick={getNavigationHandler("/home")}>Home</div></Nav.Link>
                    <Nav.Link onClick={getNavigationHandler("/users")}>Users</Nav.Link>
                </Nav>
                <Form inline>
                    <ButtonGroup toggle className="mb-2">
                        <AdminToggleButton/>
                    </ButtonGroup>
                </Form>
            </Navbar.Collapse>
        </Navbar>
    )
}

export default Header;