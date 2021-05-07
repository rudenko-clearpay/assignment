import React from 'react'
import {Accordion, Card} from "react-bootstrap";


export default (props) => {
    const {user} = props;
    return (<Card key={"card_" + user.id}>
        <Accordion.Toggle as={Card.Header} variant="link" key={"toggle_i_" + user.id} eventKey={user.id}>
            {user.fullName}
        </Accordion.Toggle>
        <Accordion.Collapse eventKey={user.id} key={"toggle_Collapse_" + user.id}>
            <Card.Body key={"toggle_Collapse_Body" + user.id}>Hello! I'm the body</Card.Body>
        </Accordion.Collapse>
    </Card>);
}