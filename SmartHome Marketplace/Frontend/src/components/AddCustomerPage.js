import React, { useState } from 'react';
import '../style.css';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';

const AddCustomerPage = () => {
  const [userData, setUserData] = useState({
    userName: '',
    password: '',
  });

  const navigate = useNavigate();

  const handleBack = () => {
    navigate('/sales-manager-dashboard');
  };

  
  const handleChange = (e) => {
    const { name, value } = e.target;
    setUserData((prevData) => ({
      ...prevData,
      [name]: value,
    }));
  };

  
  const handleAddCustomer = () => {
    
    axios.post('http://localhost:8081/user-add', userData)
      .then(response => {
        alert('Customer Added Successfully!');
        
      })
      .catch(error => {
        console.error('Error adding customer:', error);
        alert('Error adding customer. Please try again.');
      });
  };

  return (
    <div id = 'content'>
    <div>
      <h2>Add Customer</h2>
      <button onClick={handleBack}>Back</button>
      <form>
        <div>
          <label htmlFor="userName">Customer Name:</label>
          <input
            type="text"
            id="userName"
            name="userName"
            value={userData.userName}
            onChange={handleChange}
          />
        </div>
        <div>
          <label htmlFor="password">Password:</label>
          <input
            type="password"
            id="password"
            name="password"
            value={userData.password}
            onChange={handleChange}
          />
        </div>
      </form>
      <button type="button" onClick={handleAddCustomer}>
        Add Customer
      </button>
    </div>
    </div>
  );
};

export default AddCustomerPage;
