import './App.css';
import 'bootstrap/dist/css/bootstrap.min.css';
import CocktailGuessingContainer from 'components/CocktailGuessingContainer'
import {useEffect, useState} from 'react'
import UsernameContainer from 'components/UsernameContainer'
import axios from 'axios'

function App() {
    const [username, setUsername] = useState('');
    const [gameStarted, setGameStarted] = useState(false);

    const handleStart = (username) => {
        setUsername(username);
        setGameStarted(true);
        createGame()
    };

    useEffect(() => {

    }, [gameStarted])

    const createGame = async () => {
        const response = await axios.post("http://localhost:8080/api/game", {"username": username})
        setGameSession(response?.data)
    }

    const [gameSession, setGameSession] = useState({});

    const handleGuessSubmit = async (guessedWord) => {
        console.log("Submitted guess:", guessedWord);
        const response = await axios.post(`http://localhost:8080/api/game/${gameSession.gameSessionId}`, {"cocktailName": guessedWord})
        setGameSession(response?.data)
        console.log(response)
    };

  return (
    <div className="App">
      <header className="App-header">
          {!gameStarted ? (
              <UsernameContainer onStart={handleStart} />
          ) : (
              <div>
                  <h2>Hello, {username}!</h2>
                  <h5>You should write only in white boxes. Green boxes are already revealed letters for you.
                      Yellow boxes mean spaces.</h5>
                  <CocktailGuessingContainer
                      gameSession={gameSession}
                      onSubmit={handleGuessSubmit}
                  />
              </div>
          )}
      </header>
    </div>
  );
}

export default App;
