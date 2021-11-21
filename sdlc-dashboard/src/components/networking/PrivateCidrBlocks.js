import axios from "axios";
import React, { useEffect, useState } from 'react';
import Table from 'react-bootstrap/esm/Table';

const baseURL = process.env.REACT_APP_infra_service_url + "/ipv4-network-reference-data/private-cidr-blocks";

const PrivateCidrBlocks = () => {
  console.log("baseURL=" + baseURL);
  const [networks, setNetworks] = useState([]);
  useEffect(() => {
    console.log("-> useEffect");
    axios.get(baseURL, {withCredentials: true}).then((response) => {
      console.log("respone.data=" + response.data);
      setNetworks(response.data);
    });
  }, []);
  return (
    <div>
      <h3>Private CIDR Blocks 3</h3>
      <Table striped bordered hover size="sm">
        <thead>
          <tr>
            <th>CIDR Block</th>
            <th>Hosts</th>
          </tr>
        </thead>
        <tbody>
          {
            networks.map(network =>
              <tr key={network.cidrBlock}>
                <td>{network.cidrBlock}</td>
                <td>{network.hosts}</td>
              </tr>
            )
          }
        </tbody>
      </Table>
    </div>
  );
};

export default PrivateCidrBlocks;