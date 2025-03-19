import { useState } from 'react'; // TODO: remove unused import
import {BrowserRouter, Routes, Route} from 'react-router-dom';
import './App.css';
import Home from './home';
import MainMenu from './main_menu';
import Profiles from './profiles';
import Stats from './stats';
import Leaderboard from './leaderboard';
import Cosmetics from './cosmetics';
import Settings from './settings';
import Play from './play';

function App() {
  return (
    <BrowserRouter>
    {/** Pages to render here */}
      <Routes>
        <Route path='/' element={<Home/>}/>
        <Route path='/main_menu' element={<MainMenu/>}/>
        <Route path='/profiles' element={<Profiles/>}/>
        <Route path='/stats' element={<Stats/>}/>
        <Route path='/leaderboard' element={<Leaderboard/>}/>
        <Route path='/cosmetics' element={<Cosmetics/>}/>
        <Route path='/settings' element={<Settings/>}/>
        <Route path='/play' element={<Play/>}/>
      </Routes>
    </BrowserRouter>
  )
};

export default App;
