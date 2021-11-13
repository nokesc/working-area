import Button from 'react-bootstrap/esm/Button';
import Card from 'react-bootstrap/esm/Card';
import Table from 'react-bootstrap/esm/Table';

const Network = () => {
  return (
    <Card>
      <Card.Header as="h5">10.80.0.0</Card.Header>
      <Card.Body>
        <Card.Title>Finance Team</Card.Title>
        <Table striped bordered hover size="sm">
          <tbody>
          <tr>
            <th>Environment</th>
            <td>Dev</td>
          </tr>
          <tr>
            <th>Cloud Provider</th>
            <td>AWS</td>
          </tr>
          <tr>
            <th>Region</th>
            <td>us-east-2</td>
          </tr>
          </tbody>
        </Table>
        <Button variant="primary">Go somewhere</Button>
      </Card.Body>
    </Card>
  );
};

export default Network;
