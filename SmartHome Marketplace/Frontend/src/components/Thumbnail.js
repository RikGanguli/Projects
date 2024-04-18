import { Link } from "react-router-dom";
import { useCart } from './CartContext';

function Thumbnail({ piD, key, name, price, description, image, pd }) {
	const { addToCart } = useCart();
  
	return (
	  <td>
		<div id="shop_item">
		  <h3>{name}</h3>
		  <strong>{price}</strong>
		  <ul>
			<li id="item"><img src={require("../images/"+pd+image)} alt="" /></li>
		  </ul>
		  {/* <h4>{description}</h4> */}
		  <li>
			<button onClick={() => addToCart({ id: piD, name, price })}>
			  Buy
			</button>
		  </li>
		  <li><Link to={`/productDetails/${piD}`}>Details</Link></li>
		  <li>
			<button onClick={() => addToCart({ id: piD, name, price })}>
			  Write Review
			</button>
		  </li>
		  <li>
			<button onClick={() => addToCart({ id: piD, name, price })}>
			  Read Reviews
			</button>
		  </li>
		</div>
	  </td>
	);
  }
  
  export default Thumbnail;
  
