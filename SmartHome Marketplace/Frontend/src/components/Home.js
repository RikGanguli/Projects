//import PropTypes from 'prop-types';
//import Thumbnail from './Thumbnail';
//import { itemImages } from '../items';
//import './Home.css';
//import ItemType from '../types/item';

import { Link } from "react-router-dom";

function Home({ items }) {
  return (
    <div id="sidebar">
        <ul>
            <li>
                <h2>Doorbells</h2>
                <ul>
                    <li id="first"><Link to="/doorbell/eken">Eken</Link>
                    </li>
                    <li><Link to="/doorbell/ring">Ring</Link></li>
                    <li><Link to="/doorbell/blink">Blink</Link></li>
                </ul>
            </li>
            <li>
                <h2>Doorlocks</h2>
                <ul>
                    <li id="first"><Link to="/doorlock/schlage">Schlage</Link>
                    </li>
                    <li><Link to="/doorlock/teeho">TEEHO</Link></li>
                    <li><Link to="/doorlock/veise">Veise</Link></li>
                </ul>
            </li>
            <li>
                <h2>Speakers</h2>
                <ul>
                    <li id="first"><Link to="/speaker/amazon">Amazon</Link></li>
                    <li><Link to="/speaker/voiz">VOIZ</Link></li>
                    <li><Link to="/speaker/belkin">Belkin</Link></li>
                </ul>
            </li>
            <li>
                <h2>Lightings</h2>
                <ul>
                    <li id="first"><Link to="/lighting/philips">Philips</Link></li>
                    <li><Link to="/lighting/amazon">Amazon</Link></li>
                    <li><Link to="/lighting/linkind">Linkind</Link></li>
                    <li><Link to="/lighting/kasa">Kasa</Link></li>
                </ul>
            </li>
            <li>
                <h2>Thermostats</h2>
                <ul>
                    <li id="first"><Link to="/thermostat/amazon">Amazon</Link></li>
                    <li><Link to="/thermostat/google">Google</Link></li>
                    <li><Link to="/thermostat/honeywell">Honeywell</Link></li>
                </ul>
            </li>
        </ul>
    </div>
  );
}

// Home.propTypes = {
//   items: PropTypes.arrayOf(ItemType).isRequired,
// };

export default Home;
