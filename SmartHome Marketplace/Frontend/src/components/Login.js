import React, { useState } from 'react';
import {useAuthentication } from './AuthenticationContext';
import { useNavigate } from 'react-router-dom'; 

const Login = () => {
  const { login, isAuthenticated, user } = useAuthentication();
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [userType, setUserType] = useState('Customer'); 
  const navigate = useNavigate();

  const handleLogin = async () => {
    if (await login(username, password, userType)) {
      
      console.log('Login successful!');
      localStorage.setItem("CurrentUser", JSON.stringify({ username, userType }));
      switch (userType) {
        case 'Customer':
          navigate('/doorbell');
          break;
        case 'SalesManager':
          navigate('/sales-manager-dashboard');
          break;
        case 'StoreManager':
          navigate('/store-manager-dashboard');
          break;
        default:

          navigate('/doorbell');
      }
    } else {
      
      alert('Invalid Credentials');
    }
  };

  return (
    <div id='content'>
      {isAuthenticated ? (
        <div>
          <p>Welcome, {user.username}!</p>
          
        </div>
      ) : (
        <div>
          <h2>Login</h2>
          <label>
            Username:
            <input type="text" value={username} onChange={(e) => setUsername(e.target.value)} />
          </label>
          <br />
          <label>
            Password:
            <input type="password" value={password} onChange={(e) => setPassword(e.target.value)} />
          </label>
          <br />
          <label>
            User Type:
            <select value={userType} onChange={(e) => setUserType(e.target.value)}>
              <option value="Customer">Customer</option>
              <option value="SalesManager">Sales Manager</option>
              <option value="StoreManager">Store Manager</option>
            </select>
          </label>
          <br />
          <button onClick={handleLogin}>Login</button>
        </div>
      )}
    </div>
  );
};

export default Login;
