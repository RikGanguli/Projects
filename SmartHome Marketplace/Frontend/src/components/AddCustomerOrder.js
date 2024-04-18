import React, { useState } from 'react';
import '../style.css';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';

const AddCustomerOrder = () => {
  
  const [productData, setProductData] = useState({
    productName: '',
    price: '',
    manufacturer: '',
    description: '',
  });

  const navigate = useNavigate();

  const handleBack = () => {
    navigate('/sales-manager-dashboard');
  };

  
  const handleChange = (e) => {
    const { name, value } = e.target;
    setProductData((prevData) => ({
      ...prevData,
      [name]: value,
    }));
  };

  const handleAddOrder = () => {
    axios.post('http://localhost:8081/add-order', productData)
        .then(response => {
            alert(`Order Added Successfully!`);
        })
        .catch(error => {
            console.error('Error adding order:', error);
            alert('Error adding order');
        });
  };

  return (
    <div id = 'content'>
    <div>
      <h2>Add Order</h2>
      <button onClick={handleBack}>Back</button>
      <form>
      <div>
          <label htmlFor="username">User Name:</label>
          <textarea
            id="username"
            name="username"
            value={productData.username}
            onChange={handleChange}
          />
        </div>
        <div>
          <label htmlFor="id">Order ID:</label>
          <textarea
            id="id"
            name="id"
            value={productData.id}
            onChange={handleChange}
          />
        </div>
        <div>
          <label htmlFor="productName">Product Name:</label>
          <input
            type="text"
            id="productName"
            name="productName"
            value={productData.productName}
            onChange={handleChange}
          />
        </div>
        <div>
          <label htmlFor="price">Price:</label>
          <input
            type="text"
            id="price"
            name="price"
            value={productData.price}
            onChange={handleChange}
          />
        </div>
        <div>
          <label htmlFor="manufacturer">Manufacturer:</label>
          <input
            type="text"
            id="manufacturer"
            name="manufacturer"
            value={productData.manufacturer}
            onChange={handleChange}
          />
        </div>
        <div>
          <label htmlFor="description">Description:</label>
          <textarea
            id="description"
            name="description"
            value={productData.description}
            onChange={handleChange}
          />
        </div>
      </form>
      <button type="button" onClick={handleAddOrder}>
        Add Order
      </button>
    </div>
    </div>
  );
};

export default AddCustomerOrder;
