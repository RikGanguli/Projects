
import React from 'react';

import { useNavigate } from 'react-router-dom';

const StoreManagerDashboard = () => {
    const navigate = useNavigate();

    const handleAddCustomer = () => {
        navigate('/addcustomerpage');
    }

    const handleAddOrder = () => {
        navigate('/addorder');
    }

    const handleUpdateOrder = () => {
        navigate('/updateorder');
    }

    const handleDeleteOrder = () => {
        navigate('/deleteorder');
    }

  return (
    <div id = 'content'>
    <div>
      <h2>Sales Manager Dashboard</h2>
      <button onClick={handleAddCustomer}>Add Customer Account</button>
      <button onClick={handleAddOrder}>Add Customer Order</button>
      <button onClick={handleUpdateOrder}>Update Customer Order</button>
      <button onClick={handleDeleteOrder}>Delete Customer Order</button>
    </div>
    </div>
  );
};

export default StoreManagerDashboard;
