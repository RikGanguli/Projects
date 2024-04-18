
import React, { useState } from 'react';
import axios from 'axios';

const OrderHistory = () => {
  const [orderId, setOrderId] = useState('');
  const [order, setOrder] = useState(null);
  const [error, setError] = useState(null);

  const handleSearch = () => {
    
    axios.get(`http://localhost:8081/orders/${orderId}`)
      .then(response => {
        setOrder(response.data);
        setError(null);
      })
      .catch(error => {
        setOrder(null);
        setError(error.message);
      });
  };

  return (
    <div>
      <h2>Order Details</h2>
      <div>
        <label>Search by Order ID:</label>
        <input
          type="text"
          value={orderId}
          onChange={(e) => setOrderId(e.target.value)}
        />
        <button onClick={handleSearch}>Search</button>
      </div>
      {error && <p>Error fetching order: {error}</p>}
      {order && (
        <div>
          <h3>Order {order.orderId}</h3>
          <p>Order Name: {order.orderName}</p>
          <p>Order Price: ${order.orderPrice}</p>
        </div>
      )}
    </div>
  );
};

export default OrderHistory;
