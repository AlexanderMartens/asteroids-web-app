import React from 'react';

function Stats() {
    return (
        <div id='stats-page'>Stats
            <div id='stats-bests-row'>
                <div className='stats-container' id='highest-score-container'>
                    <div className='stats-internal-left'></div>
                    <div className='stats-internal-right'></div>
                </div>
                <div className='stats-container' id='highest-level-container'>
                    <div className='stats-internal-left'></div>
                    <div className='stats-internal-right'></div>
                </div>
                <div className='stats-container' id='longest-survival-container'>
                    <div className='stats-internal-left'></div>
                    <div className='stats-internal-right'></div>
                </div>
            </div>
            <div id='stats-recents-row'>
                <div className='stats-container' id='recent-games-container'>
                    <h2>Recent Games</h2>
                </div>
                <div className='stats-container' id='recent-achievements-container'>
                    <h2>Recent Achievements</h2>
                </div>
            </div>
        </div>
    );
}

export default Stats;