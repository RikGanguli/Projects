import '../style.css';
import React from 'react';
import '../items/index';
import { items } from '../items/index';
import Thumbnail from './Thumbnail';
import { useParams } from 'react-router-dom';

function Speaker() {
    const spkrs = items.ProductCatalog.SpeakerCatalog.speaker;
    const {manuf} = useParams();
  return (
    <div id="content">
      <div class="post">
      <div class="entry">
          <table id="bestseller">
            <tr>
                {spkrs.map((speaker) => {
                  if(manuf === undefined) {
                    return <Thumbnail
                      piD={speaker.id}  
                      key={speaker.id}
                      name={speaker.name}
                      price={speaker.price}
                      description={speaker.description}
                      image={speaker.image}
                      pd='speakers/'
                  />
                  } else {
                    if(speaker.manufacturer.toLowerCase() === manuf) {
                      return <Thumbnail
                        piD={speaker.id} 
                        key={speaker.id}
                        name={speaker.name}
                        price={speaker.price}
                        description={speaker.description}
                        image={speaker.image}
                        pd='speakers/'
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

export default Speaker;