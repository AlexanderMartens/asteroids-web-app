import React from 'react';
import './stats.css'

function Stats() {
    return (
        <div id='stats-page'>
            <div id='stats-bests-row' className='stats-row'>
                <div className='stats-container' id='highest-score-container'>
                    <div className='stats-internal-left'>L</div>
                    <div className='stats-internal-right'>R</div>
                </div>
                <div className='stats-container' id='highest-level-container'>
                    <div className='stats-internal-left'>L</div>
                    <div className='stats-internal-right'>R</div>
                </div>
                <div className='stats-container' id='longest-survival-container'>
                    <div className='stats-internal-left'>L</div>
                    <div className='stats-internal-right'>R</div>
                </div>
            </div>
        </div>
    );
}

export default Stats;