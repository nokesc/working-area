import React, { useState, useEffect } from 'react';
import AppNavBar from './AppNavBar'

import Container from 'react-bootstrap/esm/Container';
import Row from 'react-bootstrap/esm/Row';
import Col from 'react-bootstrap/esm/Col';

import NetworkPlan from '../components/networking/NetworkPlan.js';
import PrefixLengthTable from '../components/networking/PrefixLengthTable.js';
import PrivateCidrBlocks from '../components/networking/PrivateCidrBlocks.js';

import axios from "axios"

const baseURL = process.env.REACT_APP_infra_service_url + "/network-plans";

const Networks = () => {

  const [networkPlans, setNetworkPlans] = useState([]);

  useEffect(() => {
    console.log("-> useEffect");
    axios.get(baseURL, {withCredentials: true}).then((response) => {
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
          <Col md={6}><PrivateCidrBlocks /></Col>
        </Row>

      </Container>
    </>
  );
};

export default Networks;
