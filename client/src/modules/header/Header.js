import React, {useState} from 'react'
import {Navbar, Nav, Form, ToggleButton, ButtonGroup} from 'react-bootstrap';
import {useHistory} from "react-router-dom";


const Header = () => {
    const [isAdmin, setIsAdmin] = useState(false);
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
                        <ToggleButton type="checkbox" variant={isAdmin ? "success" : "secondary"} checked={isAdmin}
                                      onChange={(e) => setIsAdmin(e.currentTarget.checked)} value="1"
                                      className="mr-sm-2">
                            Admin mode
                        </ToggleButton>
                    </ButtonGroup>
                </Form>
            </Navbar.Collapse>
        </Navbar>
    )
}

export default Header;