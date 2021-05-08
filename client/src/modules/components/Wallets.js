import React, {useEffect, useState} from 'react'
import {Card, Nav} from "react-bootstrap";
import TransferSection from "./transfer/TransferSection";


export default (props) => {
    const {user} = props;
    const wallets = user.wallets;
    const [activeWalletId, setActiveWalletId] = useState(wallets.length ? wallets[0].id : undefined);
    const [activeWallet, setActiveWallet] = useState(wallets.length ? wallets[0].id : undefined);

    function walletChangeHandler(walletId) {
        return () => setActiveWalletId(walletId);
    }

    function getWalletHeaders() {
        return wallets.map(w => <Nav.Item key={"wallet_card_name_item_" + w.id}>
            <Nav.Link eventKey={w.id} onClick={walletChangeHandler(w.id)} key={"wallet_card_name_item_link_" + w.id}>
                {w.id}
            </Nav.Link>
        </Nav.Item>);
    }

    useEffect(() => {
        setActiveWallet(wallets.filter(w => w.id === activeWalletId)[0] || {});
    })

    return (
        <Card key={"wallet_card_" + user.id}>
            <Card.Header key={"wallet_card_h_" + user.id}>
                <Nav variant="tabs" activeKey={activeWallet.id} key={"wallet_card_name_" + activeWallet.id}>
                    {getWalletHeaders()}
                </Nav>
            </Card.Header>
            <Card.Body key={"wallet_card_body_" + user.id}>
                <Card.Title key={"wallet_card_body_title_" + user.id}>Current balance
                    is: {activeWallet.balance}</Card.Title>
                <Card.Text key={"wallet_card_body_text_" + user.id}>
                    <TransferSection user={user} originWallet={activeWallet}/>
                </Card.Text>
            </Card.Body>
        </Card>);
}