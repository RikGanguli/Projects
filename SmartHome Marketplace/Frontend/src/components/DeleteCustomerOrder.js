import React, { useState } from 'react';
import '../style.css';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';

const DeleteCustomerOrder = () => {
  
  const [orderData, setOrderData] = useState({
    username: '',
    id: '',
  });

  const navigate = useNavigate();

  const handleBack = () => {
    navigate('/sales-manager-dashboard');
  };

  
  const handleChange = (e) => {
    const { name, value } = e.target;
    setOrderData((prevData) => ({
      ...prevData,
      [name]: value,
    }));
  };

  
  const handleDeleteOrder = () => {
    
    axios.delete(`http://localhost:8081/delete-order/${orderData.id}`)
      .then(response => {
        alert(`Order Deleted Successfully!`);
      })
      .catch(error => {
        console.error('Error deleting order:', error);
        alert('Error deleting order');
      });
  };

  return (
    <div id = 'content'>
    <div>
      <h2>Delete Order</h2>
      <button onClick={handleBack}>Back</button>
      <form>
        <div>
          <label htmlFor="id">Order ID:</label>
          <textarea
            id="id"
            name="id"
            value={orderData.id}
            onChange={handleChange}
          />
        </div>
      </form>
      <button type="button" onClick={handleDeleteOrder}>
        Delete Order
      </button>
    </div>
    </div>
  );
};

export default DeleteCustomerOrder;
