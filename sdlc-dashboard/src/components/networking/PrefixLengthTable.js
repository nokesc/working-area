import React, { useState, useEffect } from 'react';
import { ipv4NetworkReferenceData_prefixLengths } from './InfraServiceAPI';
import Table from 'react-bootstrap/esm/Table';

const PrefixLengthTable = () => {
  const [prefixLengths, setPrefixLengths] = useState([]);
  useEffect(() => {
    console.log("-> useEffect");
    ipv4NetworkReferenceData_prefixLengths().then((response) => {
      console.log("-> useEffect response.data: " + JSON.stringify(response.data));
      console.log("-> useEffect response.data2: " + JSON.stringify(response.data));
      console.log("-> prefixLengths before: " + JSON.stringify(prefixLengths));
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