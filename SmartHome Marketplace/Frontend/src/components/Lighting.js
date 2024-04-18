import '../style.css';
import React from 'react';
import '../items/index';
import { items } from '../items/index';
import Thumbnail from './Thumbnail';
import { useParams } from 'react-router-dom';

function Lighting() {
    const lights = items.ProductCatalog.LightingCatalog.light;
    const {manuf} = useParams();
  return (
    <div id="content">
      <div class="post">
      <div class="entry">
          <table id="bestseller">
            <tr>
                {lights.map((light) => {
                  if(manuf === undefined) {
                    return <Thumbnail
                      piD={light.id}  
                      key={light.id}
                      name={light.name}
                      price={light.price}
                      description={light.description}
                      image={light.image}
                      pd='lightings/'
                  />
                  } else {
                    if(light.manufacturer.toLowerCase() === manuf) {
                      return <Thumbnail
                        piD={light.id}  
                        key={light.id}
                        name={light.name}
                        price={light.price}
                        description={light.description}
                        image={light.image}
                        pd='lightings/'
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

export default Lighting;