//import PropTypes from 'prop-types';
//import Thumbnail from './Thumbnail';
//import { itemImages } from '../items';
//import './Home.css';
//import ItemType from '../types/item';
import '../style.css';
import React from 'react';
import '../items/index';
import { items } from '../items/index';
import Thumbnail from './Thumbnail';
import { useParams } from 'react-router-dom';

function Doorbell() {
    const doorbells = items.ProductCatalog.DoorbellCatalog.doorbell;
    const {manuf} = useParams();
    const userName = localStorage.getItem("CurrentUser") || [];
  return (
    <div id="content">
      <div class="post">
      <div class="entry">
          <table id="bestseller">
            <tr>
                {doorbells.map((doorbell) => {
                  if(manuf === undefined) {
                    return <Thumbnail 
                      piD={doorbell.id}
                      key={doorbell.id}
                      name={doorbell.name}
                      price={doorbell.price}
                      description={doorbell.description}
                      image={doorbell.image}
                      pd='doorbells/'
                  />
                  } else {
                    if(doorbell.manufacturer.toLowerCase() === manuf) {
                      return <Thumbnail 
                        piD={doorbell.id}
                        key={doorbell.id}
                        name={doorbell.name}
                        price={doorbell.price}
                        description={doorbell.description}
                        image={doorbell.image}
                        pd='doorbells/'
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

export default Doorbell;

