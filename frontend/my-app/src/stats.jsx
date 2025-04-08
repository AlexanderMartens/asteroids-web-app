import React from 'react';
import './stats.css'

function Stats() {
    return (
        <div id='stats-page'>
            <div className='stats-grid'>
                <div className='stats-container' id='highest-score-container'>
                    <div className='stats-internal-left'>
                        <div>Score</div>
                        <div>1 234 567</div>
                    </div>
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
                <div className='stats-container' id='longest-survival-container'>
                    <div className='stats-internal-left'>L</div>
                    <div className='stats-internal-right'>R</div>
                </div>
            </div>
        </div>
    );
}

export default Stats;