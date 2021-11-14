import React from 'react';
import AppNavBar from './AppNavBar'
import logo from './logo.svg';
import './App.css';

const Landing = () => {
  return (
    <>
      <AppNavBar />
      <header className="App-header">
        <img src={logo} className="App-logo" alt="logo" />
        <p>
          Edit <code>src/App.js</code> and save to reload.  Cool 3
        </p>
        <a
          className="App-link"
          href="https://reactjs.org"
          target="_blank"
          rel="noopener noreferrer"
        >
          Learn React
        </a>
      </header>
    </>
  );
};

export default Landing;
