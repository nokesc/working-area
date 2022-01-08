import React from 'react';
import Table from 'react-bootstrap/esm/Table';
import { useSelector } from 'react-redux';
import { selectPrefixLengths } from './refDataSlice';

const PrefixLengthTable = () => {
  const prefixLengths = useSelector(selectPrefixLengths)
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