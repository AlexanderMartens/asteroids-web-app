import React from 'react';
import './leaderboard.css';

function Leaderboard() {

    let leaders = [];
    const mockLeaderData = [
        {'favorite_ship': '/asteroid-logo-bgless.png', 'user_name': 'User1', 'score': '123456'}, 
        {'favorite_ship': '/asteroid-logo-bgless.png', 'user_name': 'User2', 'score': '654321'}
    ];

    function populateLeaders() {

        // Temporary mock data for layout design
        leaders = mockLeaderData;
    }

    populateLeaders();

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
                        {leaders.map((data, index) => (
                            <div className='player' key={index}>
                                <img src={data.favorite_ship} alt='ship' className='favorite-ship'/>
                                <div>{data.user_name}</div>
                                <div>{data.score}</div>
                            </div>
                        ))}
                    </div>
                    <div id='leaderboard-buffer'></div>
                </div>
            </div>
        </div>
    );
}

export default Leaderboard;