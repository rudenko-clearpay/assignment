import React from 'react'
import {Image} from "react-bootstrap";
import logo from './Clearpay_Logo.jpg';

const Home = () => {
    return (
        <header className="App-header">
            <h1>Welcome to the ClearPay Coin!</h1>
            <Image src={logo} fluid alt="logo"/>
        </header>
    )
}

export default Home;