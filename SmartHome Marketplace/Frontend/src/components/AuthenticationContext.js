
import React, { createContext, useContext, useState } from 'react';
import axios from 'axios';

const AuthenticationContext = createContext();

export const AuthenticationProvider = ({ children }) => {
  const [user, setUser] = useState(null);

  const login = async (username, password, userType) => {
    try {
      // Make an API request to authenticate the user
      const response = await axios.post('http://localhost:8081/authenticate', {
        username,
        password,
        userType,
      });

      const loggedInUser = response.data;

      if (loggedInUser) {
        setUser(loggedInUser);
        return true;
      } else {
        
        console.error('Unexpected response during login:', response.data);
        return false;
      }
    } catch (error) {
      
      if (error.response) {
        
        console.error('Login failed with status:', error.response.status);
        return false;
      } else if (error.request) {
        
        console.error('No response received during login');
        return false;
      } else {
        
        console.error('Error during login:', error.message);
        return false;
      }
    }
  };

  const logout = () => {
    setUser(null);
  };

  return (
    <AuthenticationContext.Provider value={{ user, login, logout, setUser }}>
      {children}
    </AuthenticationContext.Provider>
  );
};

export const useAuthentication = () => {
  return useContext(AuthenticationContext);
};
