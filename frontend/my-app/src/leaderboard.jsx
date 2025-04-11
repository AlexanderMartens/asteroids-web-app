import React from 'react';

function Player() {
    return (
        <div>PLAYER</div>
    );
}

function Leaderboard() {
    return (
        <div id='leaderboard-page'>
            <div>Leaderboards</div>
            <div>
                <button>Score</button>
                <button>Level</button>
            </div>
            <div id='leaderboard-container'>
                <div id='rank-stack'>
                    <div>1</div>
                </div>
                <div id='leaderboard-stack'>
                    <div><Player/></div>
                </div>
                <div id='leaderboard-buffer'></div>
            </div>
        </div>
    );
}

export default Leaderboard;