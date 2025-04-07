import React from 'react';
import './stats.css'

function Stats() {
    return (
        <div id='stats-page'>
            <div id='stats-bests-row' className='stats-row'>
                <div className='stats-container bests-container' id='highest-score-container'>
                    <div className='stats-internal-left'>L</div>
                    <div className='stats-internal-right'>R</div>
                </div>
                <div className='stats-container bests-container' id='highest-level-container'>
                    <div className='stats-internal-left'>L</div>
                    <div className='stats-internal-right'>R</div>
                </div>
                <div className='stats-container bests-container' id='longest-survival-container'>
                    <div className='stats-internal-left'>L</div>
                    <div className='stats-internal-right'>R</div>
                </div>
            </div>
            <div id='stats-recents-row' className='stats-row'>
                <div className='stats-container recent-container' id='recent-games-container'>
                    <h2>Recent Games</h2>
                    <div className='recent-game'>Recent 1</div>
                    <div className='recent-game'>Recent 2</div>
                    <div className='recent-game'>Recent 3</div>
                </div>
                <div className='stats-container recent-container' id='recent-achievements-container'>
                    <h2>Recent Achievements</h2>
                    <div className='recent-achievement'>Recent 1</div>
                    <div className='recent-achievement'>Recent 2</div>
                    <div className='recent-achievement'>Recent 3</div>
                </div>
            </div>
        </div>
    );
}

export default Stats;