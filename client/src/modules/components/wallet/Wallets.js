import React, {useState} from 'react'
import {Card} from "react-bootstrap";
import TransferSection from "../transfer/TransferSection";
import WalletHeader from "./WalletHeader";
import {useSelector} from "react-redux";


const Wallets = (props) => {
    const {user} = props;
    const wallets = user.wallets;

    const [activeWalletId, setActiveWalletId] = useState(wallets ? wallets[0].id : undefined);
    const activeWallet = wallets.find(w => w.id === activeWalletId) || {};

    const isAdmin = useSelector(state => state.admin.isAdmin);

    function walletChangeHandler(wallet) {
        setActiveWalletId(wallet.id);
    }

    return (
        <Card key={"wallet_card_" + user.id}>
            <Card.Header key={"wallet_card_h_" + user.id}>
                <WalletHeader wallets={wallets} activeWallet={activeWallet}
                              handleActiveWalletChange={walletChangeHandler}/>
            </Card.Header>
            <Card.Body key={"wallet_card_body_" + user.id}>
                <Card.Title key={"wallet_card_body_title_" + user.id}>Current balance
                    is: {activeWallet.balance}</Card.Title>
                {isAdmin && <div key={"wallet_card_body_text_" + user.id}>
                    <TransferSection user={user} originWallet={activeWallet}/>
                </div>}
            </Card.Body>
        </Card>);
}

export default Wallets;