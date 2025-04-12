import React from 'react';
import './leaderboard.css'

function Player() {
    return (
        <div className='player'>
            <div>Icon</div>
            <div>Username</div>
            <div>Score</div>
        </div>
    );
}

function Leaderboard() {
    return (
        <div id='leaderboards-page'>
            <div id='leaderboards-content'>
                <h1 id='leaderboards-title'>Leaderboards</h1>
                <div className='tabs'>
                    <button>Score</button>
                    <button>Level</button>
                </div>
                <div id='leaderboard-container'>
                    <div id='rank-stack'>
                        <div className='rank'>1</div>
                        <div className='rank'>2</div>
                    </div>
                    <div id='leaderboard-stack'>
                        <Player/>
                        <Player/>
                    </div>
                    <div id='leaderboard-buffer'></div>
                </div>
            </div>
        </div>
    );
}

export default Leaderboard;