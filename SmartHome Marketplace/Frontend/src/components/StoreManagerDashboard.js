// StoreManagerDashboard.js
import React from 'react';
//import { useProduct } from './ProductContext';
import { useNavigate } from 'react-router-dom';

const StoreManagerDashboard = () => {
    const navigate = useNavigate();

    const handleAdd = () => {
        navigate('/addproduct');
    }

    const handleUpdate = () => {
        navigate('/updateproduct');
    }

    const handleDelete = () => {
        navigate('/deleteproduct');
    }

  return (
    <div id = 'content'>
    <div>
      <h2>Store Manager Dashboard</h2>
      <button onClick={handleAdd}>Add Product</button>
      <button onClick={handleUpdate}>Update Product</button>
      <button onClick={handleDelete}>Delete Product</button>
    </div>
    </div>
  );
};

export default StoreManagerDashboard;
