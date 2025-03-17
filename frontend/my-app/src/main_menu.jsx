// Importing required CSS and assets

// Lowest specificity stylesheet
import './main_menu.css';
// Backgroundless png of logo
import logo from './images/asteroid-logo-bgless.png';
import React, {useState} from 'react';

/**
 * MainMenu component renders the main menu of the Asteroids game after logging in.
 * It includes the game title, logo, play button, and navigation icons
 * for Stats, Leaderboard, Cosmetics, and Settings.
 *
 * @returns {JSX.Element} The JSX structure for the main menu.
 */
function MainMenu() {

    return (
        // Main container for the menu
        <div className='main-menu-container'>

            {/* Exit button section */}
            <div className='exit-set'>
                <div className='placeholder-exit-icon'/> {/* Placeholder for exit icon */}
                <div className='exit-label'>Exit</div> {/* Label for the exit button */}
            </div>

            {/* Main content of the menu */}
            <div className='main-menu-content'>

                {/* Title section */}
                <div className='title-container'>
                    <div className='title-padding'/> {/* Padding for spacing */}
                    <div className='title'>Asteroids</div> {/* Game title */}
                </div>

                {/* Logo image */}
                <img src={logo} className='main-menu-logo' alt='Asteroids logo'/>

                {/* Play button */}
                <button className='play-button'>Play</button>

                {/* Icon section for additional menu options */}
                <div className='icon-container'>

                    {/* Individual icon sets */}
                    <div className='icon-set'>
                        {/* Placeholder for Stats icon.
                        * All of these icons will be converted
                        * into Route components that direct
                        * the user to its appropriate page. */}
                        <div className='placeholder-menu-icon'/>
                        {/* Label for Stats */}
                        <div className='icon-label'>Stats</div>
                    </div>

                    <div className='icon-set'>
                        {/* Placeholder for Leaderboard icon */}
                        <div className='placeholder-menu-icon'/>
                        {/* Label for Leaderboard */}
                        <div className='icon-label'>Leaderboard</div>
                    </div>

                    <div className='icon-set'>
                        {/* Placeholder for Cosmetics icon */}
                        <div className='placeholder-menu-icon'/>
                        {/* Label for Cosmetics */}
                        <div className='icon-label'>Cosmetics</div>
                    </div>

                    <div className='icon-set'>
                        {/* Placeholder for Settings icon */}
                        <div className='placeholder-menu-icon'/>
                        {/* Label for Settings */}
                        <div className='icon-label'>Settings</div>
                    </div>

                </div>
            </div>

            {/* Bottom section for spacing or additional content */}
            <div className='bottom'></div>
        </div>
    )
}

// Exporting the MainMenu component, so that
// it can be imported and used as a component in
// a separate file
export default MainMenu;