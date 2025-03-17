import { useState } from 'react'; // TODO: remove unused import
import {BrowserRouter, Routes, Route} from 'react-router-dom';
import './App.css';
import Home from './home';
import MainMenu from './main_menu';

function App() {
  return (
    <BrowserRouter>
    {/** Pages to render here */}
      <Routes>
        <Route path='/' element={<Home/>}/>
        <Route path='/main_menu' element={<MainMenu/>}/>
      </Routes>
    </BrowserRouter>
  )
};

export default App;
