import React, { useState, useEffect } from 'react';
import axios from "axios"
import Table from 'react-bootstrap/esm/Table';

const baseURL = process.env.REACT_APP_infra_service_url + "/ipv4-network-reference-data/private-cidr-blocks";

const PrivateCidrBlocks = () => {
  console.log("baseURL=" + baseURL);
  const [privateCIDRBlocks, setPrivateCIDRBlocks] = useState([]);
  useEffect(() => {
    console.log("-> useEffect");
    axios.get(baseURL).then((response) => {      
      setPrivateCIDRBlocks(response.data);
    });
  }, []);
  return (
    <div>
    <h3>Private CIDR Blocks</h3>
    <Table striped bordered hover size="sm">
      <thead>
      <tr>
        <th>CIDR Block</th>
      </tr>
      </thead>
      <tbody>
    {
      privateCIDRBlocks.map(cidrBlock =>
      <tr key={cidrBlock}>
        <td>{cidrBlock}</td>
      </tr>
      )
    }
      </tbody>
    </Table>
    </div>
  );
};

export default PrivateCidrBlocks;