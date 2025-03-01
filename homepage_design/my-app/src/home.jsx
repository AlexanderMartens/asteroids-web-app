/** IMPORTANT
 * As of current implementation, you need to run the following commands in the my-app directory:
 * npm install
 * npm run dev
 */

import React, {useState} from 'react'
import './home.css'
import logo from './images/asteroid-logo-bgless.png'

/**
 * This function designs the frontend of the home/login page.
 * It returns HTML content to be rendered to the page.
 * The page allows the user to login and upon success, directs 
 * the user to the game.
 * 
 * @returns HTML - Page design to be rendered
 */
function Home() {

    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');

    // TODO: Javadoc comments for handleLogin
    // TODO: Have backend check if login in database, access game if valid
    /**
     * 
     */
    const handleLogin = () => {
        console.log(`Log in clicked. Username: ${username} Password: ${password}`);
    }

    // TODO: Javadoc comments for handleRegister
    //TODO: Have backend if username in database, add to database and access game if it doesn't exist
    /**
     * 
     */
    const handleRegister = () => {
        console.log(`Register clicked. Username: ${username} Password: ${password}`);
    }

    return (

        // Overarching page container
        <div className='home-container'>

            {/** Main content of the page */}
            <div className='home-content'>

                {/** Font moves the letters to the left,
                 * so custom padding combats this */}
                <div className='title-container'>
                    <div className='title-padding'/>
                    <div className='title'>Asteroids</div>
                </div>

                <img src={logo} className="login-logo" alt="Asteroid logo" />

                <div className='login-pass-container'>

                    <div className='input-container'>
                        {/** Allows user to input text.
                         * This text is saved in value, then stored in global username. */}
                        <label>Username:</label>
                        <input type='text' value={username} onChange={(e) => setUsername(e.target.value)}/>
                    </div>

                    <div className='input-container'>
                        {/** Allows user to input a password.
                         * Type password just hides the user's typed input
                         * unless the user clicks the eye icon that is
                         * automatically placed at the end of the input box. */}
                        <label>Password:</label>
                        <input type='password' value={password} onChange={(e) => setPassword(e.target.value)}/>
                    </div>

                </div>

                {/** Buttons to log in or register.
                 * When clicked, handleLogin or handleRegister is executed. */}
                <div className='login-register-container'>
                    <button className='login-register' onClick={handleLogin}>Log In</button>
                    <button className='login-register' onClick={handleRegister}>Register</button>
                </div>

            </div>

        </div>
    );
}

export default Home;