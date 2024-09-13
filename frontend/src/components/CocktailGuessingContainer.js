import React, {useEffect, useState} from 'react'
import {Button, Col, Container, Form, Image, Row} from 'react-bootstrap'

const CocktailGuessingContainer = ({gameSession, onSubmit}) => {
    const [guessedLetters, setGuessedLetters] = useState([])

    useEffect(() => {
        const initialGuessedLetters = Array.from({length: gameSession.letterCount}, (_, i) =>
            gameSession.spaceIndexes.includes(i) ? ' ' : gameSession.revealedLetters[i] || ''
        )
        setGuessedLetters(initialGuessedLetters)
    }, [gameSession])

    const handleInputChange = (index, event) => {
        const value = event.target.value.toUpperCase()
        if (value.length <= 1) {
            setGuessedLetters(prev => {
                const newGuessedLetters = [...prev]
                newGuessedLetters[index] = value
                return newGuessedLetters
            })
        }
    }

    const handleSubmit = () => {
        console.log(guessedLetters)
        const guessedWord = guessedLetters.map(letter => letter === '' ? ' ' : letter).join('')
        onSubmit(guessedWord)
    }

    const boxStyle = {
        width: '50px',
        height: '50px',
        margin: '5px 2px',
        textAlign: 'center',
        fontSize: '24px',
    }

    const spaceStyle = {
        ...boxStyle,
        backgroundColor: '#ffee18',
        pointerEvents: 'none',
    }

    const revealedStyle = {
        ...boxStyle,
        backgroundColor: '#0eff46',
        pointerEvents: 'none',
    }

    return (
        <Container className="my-3">
            <Row>
                <Col>
                    Guesses left: {gameSession.attemptsLeft}
                </Col>
                <Col>
                    Total score: {gameSession.score}
                </Col>
            </Row>
            <Row style={{fontSize: '16px', justifyContent: 'center'}}>
                {gameSession.instructions}
            </Row>
            <Row>
                {Array.from({length: gameSession.letterCount}).map((_, index) => (
                    <Col key={index} className="text-center">
                        <Form.Control
                            type="text"
                            maxLength="1"
                            value={guessedLetters[index] || ''} // Ensure input is controlled
                            onChange={(e) => handleInputChange(index, e)}
                            className="text-center"
                            readOnly={gameSession.revealedLetters[index] !== undefined || gameSession.spaceIndexes.includes(index)}
                            style={
                                gameSession.spaceIndexes.includes(index)
                                    ? spaceStyle
                                    : gameSession.revealedLetters[index] !== undefined
                                        ? revealedStyle
                                        : boxStyle
                            }
                        />
                    </Col>
                ))}
            </Row>
            <Row className="mt-3">
                <Col className="text-center">
                    <Button onClick={handleSubmit} variant="primary">Submit Guess</Button>
                </Col>
            </Row>

            <Row style={{justifyContent: 'center', marginTop: "50px"}}>
                Hints:
            </Row>
            {gameSession?.revealedInfo?.glass &&
                <Row style={{justifyContent: 'center'}}>
                    Glass: {gameSession.revealedInfo.glass}
                </Row>}
            {gameSession?.revealedInfo?.category &&
                <Row style={{justifyContent: 'center'}}>
                    Category: {gameSession.revealedInfo.category}
                </Row>}
            {gameSession?.revealedInfo?.picture &&
                <Row style={{justifyContent: 'center'}}>
                    <Image style={{maxWidth: "300px"}} src={gameSession.revealedInfo.picture}/>
                </Row>}
            {gameSession?.revealedInfo?.ingredients &&
                <Row style={{justifyContent: 'center', flexDirection: 'column', alignItems: 'center'}}>
                    <Col>
                        Ingredients:
                    </Col>
                    {gameSession.revealedInfo.ingredients.map((ingredient, index) => (
                        <Col style={{fontSize: "20px"}} key={index}>
                            {ingredient}
                        </Col>
                    ))}
                </Row>}
        </Container>
    )
}

export default CocktailGuessingContainer
