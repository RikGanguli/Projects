import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';

const DeleteProductPage = () => {
  const [productData, setProductData] = useState({
    productName: '',
  });

  const navigate = useNavigate();

  const handleBack = () => {
    navigate('/store-manager-dashboard');
  };

  const handleChange = (e) => {
    const { name, value } = e.target;
    setProductData((prevData) => ({
      ...prevData,
      [name]: value,
    }));
  };

  const handleDeleteProduct = () => {
    
    axios.delete(`http://localhost:8081/delete-product/${productData.productName}`)
      .then(response => {
        alert(response.data.message);
      })
      .catch(error => {
        console.error('Error deleting product:', error.message);
        alert('Error deleting product');
      });
  };

  return (
    <div id='content'>
      <div>
        <h2>Delete Product</h2>
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
        </form>
        <button type="button" onClick={handleDeleteProduct}>
          Delete Product
        </button>
      </div>
    </div>
  );
};

export default DeleteProductPage;
