import '../style.css';
import React from 'react';
import '../items/index';
import { items } from '../items/index';
import Thumbnail from './Thumbnail';
import { useParams } from 'react-router-dom';

function Doorlock() {
    const doorlocks = items.ProductCatalog.DoorlockCatalog.doorlock;
    const {manuf} = useParams();
  return (
    <div id="content">
      <div class="post">
      <div class="entry">
          <table id="bestseller">
            <tr>
                {doorlocks.map((doorlock) => {
                  if(manuf === undefined) {
                    return <Thumbnail
                      piD={doorlock.id} 
                      key={doorlock.id}
                      name={doorlock.name}
                      price={doorlock.price}
                      description={doorlock.description}
                      image={doorlock.image}
                      pd='doorlocks/'
                  />
                  } else {
                    if(doorlock.manufacturer.toLowerCase() === manuf) {
                      return <Thumbnail 
                        piD={doorlock.id}
                        key={doorlock.id}
                        name={doorlock.name}
                        price={doorlock.price}
                        description={doorlock.description}
                        image={doorlock.image}
                        pd='doorlocks/'
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

export default Doorlock;