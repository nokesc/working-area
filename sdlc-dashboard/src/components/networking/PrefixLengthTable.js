import React, { useState, useEffect } from 'react';
import axios from "axios"
import Table from 'react-bootstrap/esm/Table';

const baseURL = process.env.REACT_APP_infra_service_url + "/ipv4-network-reference-data/prefix-lengths";

const PrefixLengthTable = () => {
  console.log("baseURL=" + baseURL);
  const [prefixLengths, setPrefixLengths] = useState([]);
  useEffect(() => {
    console.log("-> useEffect");
    axios.get(baseURL, {withCredentials: true}).then((response) => {      
      setPrefixLengths(response.data);
    });
  }, []);
  return (
    <div>
    <h3>Prefix Lengths</h3>
    <Table striped bordered hover size="sm">
      <thead>
      <tr>
        <th>Prefix Length</th>
        <th>Netmask</th>
        <th>Hosts</th>
        <th>Usable Hosts</th>
      </tr>
      </thead>
      <tbody>
    {
      prefixLengths.map(prefixLength =>
      <tr key={prefixLength.value}>
        <td>/{prefixLength.value}</td>
        <td>{prefixLength.netmask}</td>
        <td>{prefixLength.hosts}</td>
        <td>{prefixLength.usableHosts}</td>
      </tr>
      )
    }
      </tbody>
    </Table>
    </div>
  );
};


export default PrefixLengthTable;