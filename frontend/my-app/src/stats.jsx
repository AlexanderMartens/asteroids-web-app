import React from 'react';
import './stats.css'
import { Link } from 'react-router-dom';
import TrophyIcon from './images/trophy-icon.svg?react'
import StarIcon from './images/star-icon.svg?react'

function Stats() {
    return (
        <div id='stats-page'>
            <Link id='back-container' to='/main_menu'>
                <button id='stats-back-button'>{'< '}Back</button>
            </Link>
            <div className='stats-grid'>
                <div className='stats-container' id='highest-score-container'>
                    <div className='stats-internal-top'>
                        <div className='stat-name'>Highest Score</div>
                        <div className='stats-top-right'><TrophyIcon/></div>
                    </div>
                    <div className='stat-value'>1 234 567</div>
                </div>
                <div className='stats-container' id='highest-level-container'>
                    <div className='stats-internal-top'>
                        <div className='stat-name'>Highest Level</div>
                        <div className='stats-top-right'><StarIcon/></div>
                    </div>
                    <div className='stat-value'>46</div>
                </div>
                <div className='stats-container' id='longest-survival-container'>
                    <div className='stats-internal-top'>
                        <div className='stat-name'>Longest Survival</div>
                        <div className='stats-top-right'></div>
                    </div>
                    <div className='stat-value'>15:32</div>
                </div>
                <div className='stats-container' id='longest-survival-container'>
                    <div className='stats-internal-top'>
                        <div className='stat-name'>Games Played</div>
                        <div className='stats-top-right'></div>
                    </div>
                    <div className='stat-value'>74</div>
                </div>
            </div>
        </div>
    );
}

export default Stats;