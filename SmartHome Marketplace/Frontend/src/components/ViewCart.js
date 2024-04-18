import React, { useState } from 'react';
import { useCart } from './CartContext';
import { useNavigate } from 'react-router-dom';

const ViewCart = () => {
  const { cartItems, removeFromCart, clearCart } = useCart();
  const [checkoutData, setCheckoutData] = useState({
    fullName: '',
    address: '',
    creditCard: '',
  });

  const navigate = useNavigate();
  

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setCheckoutData((prevData) => ({
      ...prevData,
      [name]: value,
    }));
  };

  const handleCheckout = () => {
    
    console.log('Checkout Data:', checkoutData);
    clearCart(); 
    navigate('/order-confirmation');
  };

  return (
    <div id='content'>
    <div id="cart">
      <h2>Your Shopping Cart</h2>
      {cartItems.length === 0 ? (
        <p className="empty-cart">Your cart is empty.</p>
      ) : (
        <ul>
          {cartItems.map((item) => (
            <li key={item.id}>
              {item.name} - ${item.price} 
              <button onClick={() => removeFromCart(item.id)}>Remove</button>
            </li>
          ))}
        </ul>
      )}
      {cartItems.length > 0 && (
        <div>
          <button onClick={clearCart}>Clear Cart</button>
          <p>Total: ${cartItems.reduce((total, item) => total + item.price, 0).toFixed(2)}</p>
          {/* Checkout Form */}
          <form>
            <label>
              Full Name:
              <input
                type="text"
                name="fullName"
                value={checkoutData.fullName}
                onChange={handleInputChange}
              />
            </label>
            <label>
              Street:
              <input
                type="text"
                name="street"
                value={checkoutData.street}
                onChange={handleInputChange}
              />
            </label>
            <label>
              City:
              <input
                type="text"
                name="city"
                value={checkoutData.city}
                onChange={handleInputChange}
              />
            </label>
            <label>
              State:
              <input
                type="text"
                name="state"
                value={checkoutData.state}
                onChange={handleInputChange}
              />
            </label>
            <label>
              Zip-Code:
              <input
                type="text"
                name="zip"
                value={checkoutData.zip}
                onChange={handleInputChange}
              />
            </label>
            <label>
              Credit Card:
              <input
                type="text"
                name="creditCard"
                value={checkoutData.creditCard}
                onChange={handleInputChange}
              />
            </label>
            <label>
        Delivery Option:
        <input
          type="radio"
          id="delivery"
          name="deliveryOption"
          value="delivery"
          checked={checkoutData.deliveryOption === 'delivery'}
          onChange={handleInputChange}
        />
        <label htmlFor="delivery">Delivery</label>

        <input
          type="radio"
          id="inStore"
          name="deliveryOption"
          value="inStore"
          checked={checkoutData.deliveryOption === 'inStore'}
          onChange={handleInputChange}
        />
        <label htmlFor="inStore">In-Store Pickup</label>
      </label>

      <label>
        Select Store Location:
        <select
          name="selectedLocation"
          value={checkoutData.selectedLocation}
          onChange={handleInputChange}
        >
          <option value="">Select a location</option>
          <option value="Location1">Chicago 60616</option>
          <option value="Location2">Texas 10155</option>
          <option value="Location2">New York 70771</option>
          <option value="Location2">Mexico 5453</option>
          <option value="Location2">San Diego 200012</option>
          <option value="Location2">Seoul 51001</option>
          <option value="Location2">Manilla 80081</option>
          <option value="Location2">Copenhagen 30333</option>
          <option value="Location2">Denmark 41114</option>
          <option value="Location2">Cairo 66666</option>
        </select>
      </label>
            <button type="button" onClick={handleCheckout}>
              Checkout
            </button>
          </form>
        </div>
      )}
    </div>
    </div>
  );
};

export default ViewCart;
