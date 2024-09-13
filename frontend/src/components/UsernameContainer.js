import React, { useState } from 'react';
import { Container, Row, Col, Form, Button } from 'react-bootstrap';

const UsernameContainer = ({ onStart }) => {
    const [username, setUsername] = useState('');

    const handleInputChange = (event) => {
        setUsername(event.target.value);
    };

    const handleStart = () => {
        if (username.trim() !== '') {
            onStart(username.trim());
        } else {
            alert('Please enter a username.');
        }
    };

    return (
        <Container className="my-5">
            <Row className="justify-content-center">
                <Col xs={12} md={6}>
                    <Form.Group controlId="usernameInput">
                        <Form.Label>Enter your username:</Form.Label>
                        <Form.Control
                            type="text"
                            value={username}
                            onChange={handleInputChange}
                            placeholder="Username"
                        />
                    </Form.Group>
                </Col>
            </Row>
            <Row className="mt-3 justify-content-center">
                <Col xs="auto">
                    <Button onClick={handleStart} variant="primary" style={{minWidth: "200px"}}>
                        Start / Resume
                    </Button>
                </Col>
            </Row>
        </Container>
    );
};

export default UsernameContainer;
