import React, { useState } from 'react';
import '../style.css';
import { useNavigate } from 'react-router-dom';

const UpdateCustomerOrder = () => {
  // State to store form data
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

  // Function to handle changes in form fields
  const handleChange = (e) => {
    const { name, value } = e.target;
    setProductData((prevData) => ({
      ...prevData,
      [name]: value,
    }));
  };

  // Function to handle the "Add Product" button click
  const handleUpdateOrder = () => {
    
    alert(`Order Updated Successfully!`);
  };

  return (
    <div id = 'content'>
    <div>
      <h2>Update Order</h2>
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
      <button type="button" onClick={handleUpdateOrder}>
        Update Order
      </button>
    </div>
    </div>
  );
};

export default UpdateCustomerOrder;
