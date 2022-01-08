import Table from 'react-bootstrap/esm/Table';
import {useSelector } from 'react-redux'
import { selectPrivateCidrBlocks } from './refDataSlice';

const PrivateCidrBlocks = () => {
  const networks = useSelector(selectPrivateCidrBlocks)
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