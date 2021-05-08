import React from 'react'
import {Nav} from "react-bootstrap";


const Wallets = (props) => {
    const {wallets, activeWallet, handleActiveWalletChange} = props;

    function walletChangeHandler(wallet) {
        return () => handleActiveWalletChange(wallet);
    }

    return (<Nav variant="tabs" activeKey={activeWallet.id}>
        {wallets.map(w => <Nav.Item key={"wallet_card_name_item_" + w.id}>
            <Nav.Link eventKey={w.id} onClick={walletChangeHandler(w)} key={"wallet_card_name_item_link_" + w.id}>
                {w.id}
            </Nav.Link>
        </Nav.Item>)}
    </Nav>);
}

export default Wallets;