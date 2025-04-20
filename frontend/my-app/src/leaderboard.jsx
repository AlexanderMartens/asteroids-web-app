import React, {useState} from 'react';
import './leaderboard.css';
import { Link } from 'react-router-dom';

function Leaderboard() {

    const [tab, setTab] = useState('scores');
    let leaders = [];
    const mockScoreData = [
        {'favorite_ship': '/asteroid-logo-bgless.png', 'user_name': '2234567890223456', 'score': '2 234 567'}, 
        {'favorite_ship': '/asteroid-logo-bgless.png', 'user_name': 'User2', 'score': '654321'},
        {'favorite_ship': '/asteroid-logo-bgless.png', 'user_name': 'User1', 'score': '12 345'}, 
        {'favorite_ship': '/asteroid-logo-bgless.png', 'user_name': 'User1', 'score': '123456'},
        {'favorite_ship': '/asteroid-logo-bgless.png', 'user_name': 'User1', 'score': '123456'}, 
        {'favorite_ship': '/asteroid-logo-bgless.png', 'user_name': 'User1', 'score': '123456'}, 
        {'favorite_ship': '/asteroid-logo-bgless.png', 'user_name': 'User1', 'score': '123456'}, 
        {'favorite_ship': '/asteroid-logo-bgless.png', 'user_name': 'User1', 'score': '123456'},
        {'favorite_ship': '/asteroid-logo-bgless.png', 'user_name': 'User1', 'score': '123456'},
        {'favorite_ship': '/asteroid-logo-bgless.png', 'user_name': 'User1', 'score': '123456'}
    ];

    const mockLevelData = [
        {'favorite_ship': '/asteroid-logo-bgless.png', 'user_name': '2234567890223456', 'score': '75'}, 
        {'favorite_ship': '/asteroid-logo-bgless.png', 'user_name': 'User2', 'score': '63'},
        {'favorite_ship': '/asteroid-logo-bgless.png', 'user_name': 'User1', 'score': '58'}, 
        {'favorite_ship': '/asteroid-logo-bgless.png', 'user_name': 'User1', 'score': '49'},
        {'favorite_ship': '/asteroid-logo-bgless.png', 'user_name': 'User1', 'score': '48'}, 
        {'favorite_ship': '/asteroid-logo-bgless.png', 'user_name': 'User1', 'score': '47'}, 
        {'favorite_ship': '/asteroid-logo-bgless.png', 'user_name': 'User1', 'score': '41'}, 
        {'favorite_ship': '/asteroid-logo-bgless.png', 'user_name': 'User1', 'score': '39'},
        {'favorite_ship': '/asteroid-logo-bgless.png', 'user_name': 'User1', 'score': '38'},
        {'favorite_ship': '/asteroid-logo-bgless.png', 'user_name': 'User1', 'score': '37'}
    ];

    function populateLeaders() {

        // Temporary mock data for layout design
        if (tab === 'scores'){
            leaders = mockScoreData;
        }
        else {
            leaders = mockLevelData;
        }
    }



    populateLeaders();

    return (
        <div id='leaderboards-page'>
            <div id='leaderboards-content'>
                <Link to='/main_menu' id='leaderboard-back-wrapper'>
                    <button className='button-2' id='leaderboard-back'>{'< '}Back</button>
                </Link>
                <div id='leaderboards-title'>Leaderboards</div>
                <div className='tabs'>
                    <button className={tab === 'scores' ? 'button-2 active' : 'button-2'} id='scores-button' onClick = {() => {setTab('scores')}}>Score</button>
                    <button className={tab === 'levels' ? 'button-2 active' : 'button-2'} id='levels-button' onClick = {() => {setTab('levels')}}>Level</button>
                </div>
                <div id='leaderboard-container'>
                    <div id='leaderboard-stack'>
                        {leaders.map((data, index) => (
                            <div className='leaderboard-item'>
                                <div className='rank'>{index+1}</div>
                                <div className='player' key={index}>
                                    <img src={data.favorite_ship} alt='ship' className='favorite-ship'/>
                                    <div className='leaderboard-username'>{data.user_name}</div>
                                    <div className='leaderboard-score'>{data.score}</div>
                                </div>
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