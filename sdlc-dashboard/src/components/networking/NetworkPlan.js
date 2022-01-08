import React, { useState, useEffect } from 'react';
import axios from "axios"
import Table from 'react-bootstrap/esm/Table';
import Card from 'react-bootstrap/esm/Card';

const baseURL = process.env.REACT_APP_infra_service_url + "/network-plans";

const NetworkPlan = (props) => {
  
  // console.log("baseURL=" + baseURL);
  const [networkPlan, setNetworkPlan] = useState({});
  const [loading, setLoading] = useState(true);
  useEffect(() => {
    // TODO Move to InfraServiceAPI
    axios.get(baseURL + "/" + props.id,  {withCredentials: true}).then((response) => {      
      setNetworkPlan(response.data);
      setLoading(false);
    });
  }, [props.id]);
  if(loading) {
    return <div className="App">Loading...</div>;
  }
  return (
    <>
    <Card>      
      <Card.Body>
        <Card.Title>Network Plan {networkPlan.network.cidrBlock} <small>({networkPlan.name})</small></Card.Title>
        {networkPlan.description}
        <b>Subnets</b>
        <Table striped bordered hover size="sm">
          <thead>
            <tr>
              <th>CIDR Block</th>
              <th>Name</th>
              <th>Owner</th>
            </tr>
          </thead>
          <tbody>{ networkPlan.subnets.map(subnet =>
            <tr key={subnet.cidrBlock}>
              <td>{subnet.cidrBlock}</td>
              <td>{subnet.name}</td>
              <td>{subnet.owner}</td>
            </tr>
          )}</tbody>
        </Table>
      </Card.Body>
    </Card>
    </>
  );
};

export default NetworkPlan;