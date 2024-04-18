import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';

const UpdateProductPage = () => {
  // State to store form data
  const [productData, setProductData] = useState({
    productName: '',
    price: '',
    manufacturer: '',
    description: '',
    image: '',
    condition: '',
    discount: '',
  });

  const navigate = useNavigate();

  const handleBack = () => {
    navigate('/store-manager-dashboard');
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
  const handleUpdateProduct = () => {
    // Prepare the data to be sent to the server
    const updatedProduct = { ...productData };

    // Make an HTTP request to update the product
    axios.post('http://localhost:8081/update-product', updatedProduct)
      .then(response => {
        
        alert('Product Updated Successfully!');
      })
      .catch(error => {
        // Handle errors
        console.error('Error updating product:', error.message);
        alert('Error updating product. Please try again.');
      });
  };

  return (
    <div id = 'content'>
    <div>
      <h2>Update Product</h2>
      <button onClick={handleBack}>Back</button>
      <form>
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
      <button type="button" onClick={handleUpdateProduct}>
        Update Product
      </button>
    </div>
    </div>
  );
};

export default UpdateProductPage;
