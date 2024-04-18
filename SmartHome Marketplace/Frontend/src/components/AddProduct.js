import React, { useState } from 'react';
import '../style.css';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';

const AddProductPage = () => {
  // State to store form data
  const [productData, setProductData] = useState({
    productType: '',
    productName: '',
    price: '',
    manufacturer: '',
    description: '',
    image: '',
    condition: '',
    discount: '',
  });

  const navigate = useNavigate();

  // Function to handle changes in form fields
  const handleChange = (e) => {
    const { name, value } = e.target;
    setProductData((prevData) => ({
      ...prevData,
      [name]: value,
    }));
  };

  const handleBack = () => {
    navigate('/store-manager-dashboard');
  };


  // Function to handle the "Add Product" button click
  const handleAddProduct = () => {
    // Send the product data to the server
    axios.post('http://localhost:8081/add-product', productData)
      .then(response => {
        
        alert(response.data.message);
      })
      .catch(error => {
        // Handle errors
        console.error('Error adding product:', error.message);
      });
  };

  return (
    <div id = 'content'>
    <div>
      <h2>Add Product</h2>
      <button onClick={handleBack}>Back</button>
      <form>
      <div>
          <label htmlFor="producType">Product Type:</label>
          <input
            type="text"
            id="producType"
            name="producType"
            value={productData.producType}
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
        <div>
          <label htmlFor="image">Image:</label>
          <textarea
            id="image"
            name="image"
            value={productData.image}
            onChange={handleChange}
          />
        </div>
        <div>
          <label htmlFor="condition">Condition:</label>
          <textarea
            id="condition"
            name="condition"
            value={productData.condition}
            onChange={handleChange}
          />
        </div>
        <div>
          <label htmlFor="discount">Discount:</label>
          <textarea
            id="discount"
            name="discount"
            value={productData.discount}
            onChange={handleChange}
          />
        </div>
      </form>
      <button type="button" onClick={handleAddProduct}>
        Add Product
      </button>
    </div>
    </div>
  );
};

export default AddProductPage;
