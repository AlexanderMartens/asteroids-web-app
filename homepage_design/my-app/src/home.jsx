import React from 'react'
import './home.css'
import logo from './asteroid-logo-bgless.png'

function Home() {
    return (
        <div className='home-container'>
            <div className='home-content'>
                <div className='title-container'>
                    <div className='title-padding'/>
                    <div className='title'>Asteroids</div>
                </div>
                <img src={logo} className="login-logo" alt="Asteroid logo" />
            </div>
        </div>
    );
}

export default Home;