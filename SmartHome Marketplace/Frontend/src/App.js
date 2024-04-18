import './App.css';
import './style.css';
import { BrowserRouter, Link, Route, Routes } from 'react-router-dom';
import Doorbell from './components/Doorbell';
import Doorlock from './components/Doorlock';
import Lighting from './components/Lighting';
import Speaker from './components/Speaker';
import Thermostat from './components/Thermostat';
import Home from './components/Home';
import ProductDetails from './components/ProductDetails';
import { CartProvider } from './components/CartContext';
import ViewCart from './components/ViewCart';
import OrderConfirmation from './components/OrderConfirmation';
import OrderHistory from './components/OrderHistory';
import UserRegistration from './components/UserRegistration';
import { AuthenticationProvider } from './components/AuthenticationContext';
import Login from './components/Login';
import StoreManagerDashboard from './components/StoreManagerDashboard';
import SalesManagerDashboard from './components/SalesManagerDashboard';
import AddProduct from './components/AddProduct';
import UpdateProduct from './components/UpdateProduct';
import DeleteProduct from './components/DeleteProduct';
import AddCustomerPage from './components/AddCustomerPage';
import AddCustomerOrder from './components/AddCustomerOrder';
import UpdateCustomerOrder from './components/UpdateCustomerOrder';
import DeleteCustomerOrder from './components/DeleteCustomerOrder';
import SearchAutoComplete from './components/SearchAutoComplete';

function App() {
  return (
    <AuthenticationProvider>
    <CartProvider>
    <div className='wrapper'>
      <h1>Smart Homes</h1>
      <BrowserRouter>

        <div id="search-bar">
                <SearchAutoComplete />
        </div>

        <div id="header">
		      <div id="menu">
            <nav>
              <ul>
                <li><Link to="/doorbell">Doorbell</Link></li>
                <li><Link to="/doorlock">Doorlock</Link></li>
                <li><Link to="/lighting">Lighting</Link></li>
                <li><Link to="/speaker">Speaker</Link></li>
                <li><Link to="/thermostat">Thermostat</Link></li>
                <li><Link to="/viewcart">ViewCart</Link></li>
                <li><Link to="/orderhistory">View Orders</Link></li>
                <li><Link to="/user-registration">Register</Link></li>
                <li><Link to="/login">Log In</Link></li>
                <li><Link to="/logout">Log Out</Link></li>
              </ul>
            </nav>
          </div>

          <div id = 'content'>
          <Routes>
              <Route path ="/doorbell" element = {<Doorbell />}>
                <Route path=":manuf" element = {<Doorbell />}></Route>
              </Route>

              <Route path="/doorlock" element = {<Doorlock />}>
                <Route path=":manuf" element = {<Doorlock />}></Route>
              </Route>

              <Route path="/lighting" element = {<Lighting />}>
                <Route path=":manuf" element = {<Lighting />}></Route>
              </Route>

              <Route path="/speaker" element = {<Speaker />}>
                <Route path=":manuf" element = {<Speaker />}></Route>
              </Route>

              <Route path="/thermostat" element = {<Thermostat />}>
                <Route path=":manuf" element = {<Thermostat />}></Route>
              </Route>

              <Route path="/productDetails" element = {<ProductDetails />}>
                <Route path=":piD" element = {<ProductDetails />}></Route>
              </Route>

              <Route path="/viewCart" element = {<ViewCart />}></Route>

              <Route path="/order-confirmation" element={<OrderConfirmation />} />

              <Route path="/orderhistory" element={<OrderHistory />} />

              <Route path="/user-registration" element={<UserRegistration />} />

              <Route path="/login" element={<Login />} />

              <Route path="/home" element={<Home />} />

              <Route path="/store-manager-dashboard" element={<StoreManagerDashboard />} />

              <Route path="/sales-manager-dashboard" element={<SalesManagerDashboard />} />

              <Route path="/addproduct" element={<AddProduct />} />
              <Route path="/updateproduct" element={<UpdateProduct />} />
              <Route path="/deleteproduct" element={<DeleteProduct />} />

              <Route path="/addcustomerpage" element={<AddCustomerPage />} />
              <Route path="/addorder" element={<AddCustomerOrder />} />
              <Route path="/updateorder" element={<UpdateCustomerOrder />} />
              <Route path="/deleteorder" element={<DeleteCustomerOrder />} />

              <Route path="/logout" element={<Login/>} />

            </Routes>
            </div>
        </div>
        <Home />
      </BrowserRouter>
    </div>
    </CartProvider>
    </AuthenticationProvider>
  );
}

export default App;
