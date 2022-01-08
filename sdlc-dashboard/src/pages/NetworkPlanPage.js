// import React, { useState, useEffect } from 'react';
import AppNavBar from './AppNavBar'

import Container from 'react-bootstrap/esm/Container';
import Row from 'react-bootstrap/esm/Row';
import Col from 'react-bootstrap/esm/Col';

import NetworkPlan from '../components/networking/NetworkPlan.js';

// import axios from "axios"

// const baseURL = process.env.REACT_APP_infra_service_url + "/network-plans";

const NetworkPlanPage = (props) => {

  // const [networkPlan, setNetworkPlan] = useState([]);

  return (
    <>
      <AppNavBar/>
      <header className="App-header">
        Networks
      </header>
      <Container fluid>
        <Row>
          <Col md={6} key={props.match.params.id}><NetworkPlan id={props.match.params.id} /></Col>
        </Row>
      </Container>
    </>
  );
};

export default NetworkPlanPage;
