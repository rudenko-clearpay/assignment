import React, { useEffect } from 'react';
import Routes from './config/RoutesConfig';
import { loadUsers } from './store/actions';
import { useDispatch } from 'react-redux';
import Header from "./modules/header/Header";
import Container from 'react-bootstrap/Container';
import Button from 'react-bootstrap/Button';

const App = () => {
  return (
    <Container>
        <Header/>
        <Routes />
        <Button variant="primary">Primary</Button>{' '}
    </Container>
  );
}

export default App;