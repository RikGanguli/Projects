
import React from 'react';
import { useCart } from './CartContext';
import '../style.css'

const OrderConfirmation = () => {
  const { cartItems } = useCart();

  
  return (
    <div id = 'content'>
    <div className="order-confirmation">
      <h2>Order Confirmation</h2>
      <p>Thank you for your order!</p>
      <div className="order-details">
        <h3>Order Details:</h3>
        <ul>
          {cartItems.map((item) => (
            <li key={item.id}>
              {item.name} - ${item.price}
            </li>
          ))}
        </ul>
        <p className="total">Your order number is: 12</p>
      </div>
    </div>
    </div>
  );
};

export default OrderConfirmation;
