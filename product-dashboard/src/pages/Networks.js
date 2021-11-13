import React, { useState, useEffect } from 'react';
import AppNavBar from '../components/AppNavBar'

import Container from 'react-bootstrap/esm/Container';
import Row from 'react-bootstrap/esm/Row';
import Col from 'react-bootstrap/esm/Col';

import NetworkPlan from '../components/networking/NetworkPlan.js';
import PrefixLengthTable from '../components/networking/PrefixLengthTable.js';

import axios from "axios"

const baseURL = process.env.REACT_APP_infra_service_url + "/network-plans";

const Networks = () => {

  const [networkPlans, setNetworkPlans] = useState([]);

  useEffect(() => {
    console.log("-> useEffect");
    axios.get(baseURL).then((response) => {
      setNetworkPlans(response.data.slice(0, 2));
    });
  }, []);

  return (
    <>
      <AppNavBar/>
      <header className="App-header">
        Networks
      </header>
      <Container fluid>
        <Row>{networkPlans.map(networkPlan =>
          <Col md={6} key={networkPlan.id}><NetworkPlan id={networkPlan.id} /></Col>
        )}</Row>
      </Container>
      <Container fluid>
        <Row>
          <Col md={6}><PrefixLengthTable /></Col>
        </Row>
      </Container>
    </>
  );
};

export default Networks;
