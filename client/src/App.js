import React from 'react';
import Routes from './config/RoutesConfig';
import Header from "./modules/header/Header";
import Container from 'react-bootstrap/Container';

const App = () => {
  return (
    <Container>
        <Header/>
        <Routes />
    </Container>
  );
}

export default App;