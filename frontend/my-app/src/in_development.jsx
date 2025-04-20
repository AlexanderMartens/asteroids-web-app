import React from 'react';
import './in_development.css'
import ship from './images/asteroid-logo-bgless.png'

function InDevelopment(props) {
    return (
        <div id='in-dev-container'>
            <div id='in-dev-content'>
                <div id='dev-intro'>
                    The {props.page} page is currently under construction. Enjoy the rest of the Asteroids app and check back in a few days!
                </div>
                <div id='dev-description'>
                    {props.description}
                </div>
                <div className='orbit-container'>
                    <div className='orbit-wrapper'>
                        <img src={ship} id='dev-ship'/>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default InDevelopment;