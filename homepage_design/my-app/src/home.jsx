import React, {useState} from 'react'
import './home.css'
import logo from './asteroid-logo-bgless.png'

function Home() {

    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');

    // TODO: Check if login in database, access game if valid
    const handleLogin = () => {
        console.log("Log in clicked");
    }

    //TODO: Check if username in database, add to database and access game if it doesn't exist
    const handleRegister = () => {
        console.log("Register clicked");
    }

    return (
        <div className='home-container'>
            <div className='home-content'>
                <div className='title-container'>
                    <div className='title-padding'/>
                    <div className='title'>Asteroids</div>
                </div>
                <img src={logo} className="login-logo" alt="Asteroid logo" />
                <div className='login-pass-container'>
                    <div className='input-container'>
                        <label>Username:</label>
                        <input type='text' value={username} onChange={(e) => setUsername(e.target.value)}/>
                    </div>
                    <div className='input-container'>
                        <label>Password:</label>
                        <input type='password' value={password} onChange={(e) => setPassword(e.target.value)}/>
                    </div>
                </div>
                <div className='login-register-container'>
                    <button className='login-register' onClick={handleLogin}>Log In</button>
                    <button className='login-register' onClick={handleRegister}>Register</button>
                </div>
            </div>
        </div>
    );
}

export default Home;