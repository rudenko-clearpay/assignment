import React, {useState} from 'react'
import {Button, Card} from "react-bootstrap";
import TransferSection from "../transfer/TransferSection";
import WalletHeader from "./WalletHeader";
import {useSelector} from "react-redux";
import Clipboard from "../../../utils/Clipboard";


const Wallets = (props) => {
    const {user} = props;
    const wallets = user.wallets;

    const [activeWalletId, setActiveWalletId] = useState(wallets ? wallets[0].id : undefined);
    const activeWallet = wallets.find(w => w.id === activeWalletId) || {};

    const isLoggedIn = useSelector(state => state.login.isLoggedIn);

    function walletChangeHandler(wallet) {
        setActiveWalletId(wallet.id);
    }

    async function copyActiveWalletId() {
        await Clipboard.copyTextToClipboard(activeWalletId);
    }

    return (
        <Card key={"wallet_card_" + user.id}>
            <Card.Header key={"wallet_card_h_" + user.id}>
                <WalletHeader wallets={wallets} activeWallet={activeWallet}
                              handleActiveWalletChange={walletChangeHandler}/>
            </Card.Header>
            <Card.Body key={"wallet_card_body_" + user.id}>
                <Card.Title key={"wallet_card_body_title_" + user.id} className="justify-content-between">
                    <span>Current balance is: {activeWallet.balance} </span>
                    <Button variant="outline-info" onClick={copyActiveWalletId}>Copy wallet ID</Button></Card.Title>
                {isLoggedIn && <div key={"wallet_card_body_text_" + user.id}>
                    <TransferSection originWallet={activeWallet}/>
                </div>}
            </Card.Body>
        </Card>);
}

export default Wallets;