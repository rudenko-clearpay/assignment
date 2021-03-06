import React from 'react'
import {Accordion, Card} from "react-bootstrap";
import Wallet from "./wallet/Wallets";


const UserRow = (props) => {
    const {user} = props;
    return (<Card key={"card_" + user.id}>
        <Accordion.Toggle as={Card.Header} variant="link" key={"toggle_i_" + user.id} eventKey={user.id}>
            <Card.Title key={"toggle_title_" + user.id}>{user.fullName}</Card.Title>
        </Accordion.Toggle>
        <Accordion.Collapse eventKey={user.id} key={"toggle_Collapse_" + user.id}>
            <Wallet user={user}/>
        </Accordion.Collapse>
    </Card>);
}

export default UserRow;