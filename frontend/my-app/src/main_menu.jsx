import './main_menu.css'
import logo from './images/asteroid-logo-bgless.png'

function MainMenu() {

    return (

        <div className='main-menu-container'>

            <div className='exit-set'>
                <div className='placeholder-exit-icon'/>
                <div className='exit-label'>Exit</div>
            </div>

            <div className='main-menu-content'>

                <div className='title-container'>
                        <div className='title-padding'/>
                        <div className='title'>Asteroids</div>
                </div>

                <img src={logo} className='main-menu-logo' alt='Asteroids logo'/>

                <button className='play-button'>Play</button>

                <div className='icon-container'>

                    <div className='icon-set'>
                        <div className='placeholder-menu-icon'/>
                        <div className='icon-label'>Stats</div>
                    </div>

                    <div className='icon-set'>
                        <div className='placeholder-menu-icon'/>
                        <div className='icon-label'>Leaderboard</div>
                    </div>

                    <div className='icon-set'>
                        <div className='placeholder-menu-icon'/>
                        <div className='icon-label'>Cosmetics</div>
                    </div>

                    <div className='icon-set'>
                        <div className='placeholder-menu-icon'/>
                        <div className='icon-label'>Settings</div>
                    </div>

                </div>
            </div>
            <div className='bottom'></div>
        </div>
            
    )
}

export default MainMenu;