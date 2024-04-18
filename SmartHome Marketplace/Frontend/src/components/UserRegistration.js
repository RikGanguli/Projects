import React, { useState } from 'react';
import '../style.css';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';


const UserRegistration = () => {
  const [userData, setUserData] = useState({
    username: '',
    password: '',
    confirmPassword: '',
    userType: 'customer', // Default to Customer
  });

  const navigate = useNavigate();

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setUserData((prevData) => ({
      ...prevData,
      [name]: value,
    }));
  };

  const handleSubmit = (e) => {
    e.preventDefault();

    // Validate passwords
    if (userData.password !== userData.confirmPassword) {
      alert("Passwords don't match!");
      return;
    }
  
    axios.post('http://localhost:8081/user-registration', userData)
    .then(response => {
      
      console.log('Registration successful:', response.data);
      alert('Registration successful!');
      navigate('/login');
    })
    .catch(error => {
      
      console.error('Registration failed:', error.message);
      alert('Registration failed. Please try again later.');
    });

  };

  return (
    <div id = 'content'>
    <div id = 'user-registration'>
      <h2>User Registration</h2>
      <form onSubmit={handleSubmit}>
        <label>
          Username:
          <input
            type="text"
            name="username"
            value={userData.username}
            onChange={handleInputChange}
            required
          />
        </label>
        <br />
        <label>
          Password:
          <input
            type="password"
            name="password"
            value={userData.password}
            onChange={handleInputChange}
            required
          />
        </label>
        <br />
        <label>
          Confirm Password:
          <input
            type="password"
            name="confirmPassword"
            value={userData.confirmPassword}
            onChange={handleInputChange}
            required
          />
        </label>
        <br />
        <label>
          User Type:
          <select
            name="userType"
            value={userData.userType}
            onChange={handleInputChange}
          >
            <option value="customer">Customer</option>
            <option value="storeManager">Store Manager</option>
            <option value="salesManager">Sales Manager</option>
          </select>
        </label>
        <br />
        <button type="submit">Register</button>
      </form>
    </div>
    </div>
  );
};

export default UserRegistration;
