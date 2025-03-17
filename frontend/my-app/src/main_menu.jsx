import './main_menu.css'
import logo from './images/asteroid-logo-bgless.png'

function MainMenu() {
    return (
        <div className='main-menu-container'>
            <div className='main-menu-content'>
                <div className='title-container'>
                        <div className='title-padding'/>
                        <div className='title'>Asteroids</div>
                </div>
            </div>
            <img src={logo} className='main-menu-logo' alt='Asteroids logo'/>
            <button className='play-button'>Play</button>
        </div>
    )
}

export default MainMenu;