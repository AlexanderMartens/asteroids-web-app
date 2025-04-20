import React, { useState, useEffect } from 'react';
import './stats.css'
import { Link } from 'react-router-dom';
import TrophyIcon from './images/trophy-icon.svg?react';
import StarIcon from './images/star-icon.svg?react';
import ClockIcon from './images/clock-icon.svg?react';
import ControllerIcon from './images/controller-icon.svg?react';
import { useAuth } from './auth_context';

function Stats() {

    const { user } = useAuth();
    const [highestScore, setHighestScore] = useState('0');
    const [highestLevel, setHighestLevel] = useState('0');
    const [longestSurvival, setLongestSurvival] = useState('00:00');
    const [gamesPlayed, setGamesPlayed] = useState('0');
    const username = user?.username ?? 'guest';
    const profile_name = username;

    function form10(value){
        if (value < 10){
            value = '0' + value;
        }
        return value;
    }

    function sToMin(seconds){
        let min = Math.floor(seconds / 60);
        let rem = seconds % 60;
        return {'min': min, 'rem': rem};
    }

    function sToHr(seconds) {
        let hr = Math.floor(seconds / 3600);
        let rem = seconds % 3600;
        return {'hr': hr, 'rem': rem};
    }

    function formatTime(seconds) {
        seconds = Number(seconds);
        if (seconds < 60){
            return '00:' + form10(seconds);
        }
        else if (seconds < 3600){
            let minRem = sToMin(seconds);
            return form10(minRem.min) + ':' + form10(minRem.rem);
        }
        else {
            let hrRem = sToHr(seconds);
            let minRem = sToMin(hrRem.rem);
            return formatLongNumber(hrRem.hr) + ':' + form10(minRem.min) + ':' + form10(minRem.rem);
        }
    }

    function formatLongNumber(num) {
        num = num.toString();
        return num.replace(/\B(?=(\d{3})+(?!\d))/g, " ");
    }

    useEffect(() => {
        fetch(`http://localhost:8080/api/getStats?username=${encodeURIComponent(username)}&profile_name=${encodeURIComponent(profile_name)}`)
            .then((response) => response.json())
            .then((data) => {
                console.log(`Success: ${data.success}, Error: ${data.error}`)
                const stats = data.stats;
                setHighestScore(formatLongNumber(stats.highest_score));
                setHighestLevel(formatLongNumber(stats.highest_level));
                setLongestSurvival(formatTime(stats.longest_duration));
                setGamesPlayed(formatLongNumber(stats.total_games));
                console.log(`Success: ${data.success}, Error: ${data.error}`)
            })
            .catch((err) => {
                console.error('Failed to fetch data: ', err);
            });
    });

    return (
        <div id='stats-page'>
            <div id='back-container'>
                <Link to='/main_menu'>
                    <button className = 'button-2' id='stats-back-button'>{'< '}Back</button>
                </Link>
            </div>
            <div id='stats-title'>Stats</div>
            <div className='stats-grid'>
                <div className='stats-container' id='highest-score-container'>
                    <div className='stats-internal-top'>
                        <div className='stat-name'>Highest Score</div>
                        <div className='stats-top-right'><TrophyIcon/></div>
                    </div>
                    <div className='stat-value'>{highestScore}</div>
                </div>
                <div className='stats-container' id='highest-level-container'>
                    <div className='stats-internal-top'>
                        <div className='stat-name'>Highest Level</div>
                        <div className='stats-top-right'><StarIcon/></div>
                    </div>
                    <div className='stat-value'>{highestLevel}</div>
                </div>
                <div className='stats-container' id='longest-survival-container'>
                    <div className='stats-internal-top'>
                        <div className='stat-name'>Longest Survival</div>
                        <div className='stats-top-right'><ClockIcon/></div>
                    </div>
                    <div className='stat-value'>{longestSurvival}</div>
                </div>
                <div className='stats-container' id='longest-survival-container'>
                    <div className='stats-internal-top'>
                        <div className='stat-name'>Games Played</div>
                        <div className='stats-top-right'><ControllerIcon/></div>
                    </div>
                    <div className='stat-value'>{gamesPlayed}</div>
                </div>
            </div>
        </div>
    );
}

export default Stats;