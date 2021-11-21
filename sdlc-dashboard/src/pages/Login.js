import React, { useState } from 'react';
import Form from 'react-bootstrap/Form';
import Button from 'react-bootstrap/Button';
import Container from 'react-bootstrap/esm/Container';
import Row from 'react-bootstrap/esm/Row';
import Col from 'react-bootstrap/esm/Col';
import Card from 'react-bootstrap/esm/Card';



const Login = () => {
  const [userName, setUserName] = useState('')
  const [password, setPassword] = useState('')

  const handleUserNameChange = event => {
    setUserName(event.target.value)
  };

  const handlePasswordChange = event => {
    setPassword(event.target.value)
  };

  const handleSubmit = event => {
    event.preventDefault();
    const url = 'http://localhost:9200/perform_login'

    const requestOptions = {
      method: 'POST',
      body: new URLSearchParams({
        'username': userName,
        'password': password
      }),
      credentials: "include",
      mode: "cors",
      redirect: "manual"
    };
    fetch(url, requestOptions)
      .then(response => console.log('Submitted successfully'))
      .catch(error => console.log('Form submit error', error))
  };

  return (
    <Container fluid>
      <Row className="row align-items-center">
        <Col md={{ span: 4, offset: 4 }}>
          <Card>
            <Card.Body>
              <Card.Title>
                App Dashboard Login
              </Card.Title>
              <Form onSubmit={handleSubmit}>
                <Form.Group className="mb-3" controlId="formBasicName">
                  <Form.Label>User Name</Form.Label>
                  <Form.Control type="name" placeholder="Enter name" onChange={handleUserNameChange} />
                </Form.Group>

                <Form.Group className="mb-3" controlId="formBasicPassword">
                  <Form.Label>Password</Form.Label>
                  <Form.Control type="password" placeholder="Password" onChange={handlePasswordChange} />
                </Form.Group>
                <Button variant="primary" type="submit">
                  Login
                </Button>
              </Form>
            </Card.Body>
          </Card>
        </Col>
      </Row>
    </Container>
  );
};

export default Login;
