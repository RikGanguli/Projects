import '../style.css';
import React from 'react';
import '../items/index';
import { items } from '../items/index';
import Thumbnail from './Thumbnail';
import { useParams } from 'react-router-dom';

function Thermostat() {
    const tstats = items.ProductCatalog.ThermostatCatalog.thermostat;
    const {manuf} = useParams();
  return (
    <div id="content">
      <div class="post">
      <div class="entry">
          <table id="bestseller">
            <tr>
                {tstats.map((thermostats) => {
                  if(manuf === undefined) {
                    return <Thumbnail
                      piD={thermostats.id}  
                      key={thermostats.id}
                      name={thermostats.name}
                      price={thermostats.price}
                      description={thermostats.description}
                      image={thermostats.image}
                      pd='thermostats/'
                  />
                  } else {
                    if(thermostats.manufacturer.toLowerCase() === manuf) {
                      return <Thumbnail
                        piD={thermostats.id} 
                        key={thermostats.id}
                        name={thermostats.name}
                        price={thermostats.price}
                        description={thermostats.description}
                        image={thermostats.image}
                        pd='thermostats/'
                      />
                    }
                  }
                  return null;
              })}
            </tr>
          </table>
          </div>
      </div>
    </div>
  );
}

// Home.propTypes = {
//   items: PropTypes.arrayOf(ItemType).isRequired,
// };

export default Thermostat;