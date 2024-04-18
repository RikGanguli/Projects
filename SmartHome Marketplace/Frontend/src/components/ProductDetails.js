//import { Link } from "react-router-dom";
//import userEvent from "@testing-library/user-event";
import { useParams } from 'react-router-dom';
import { items } from '../items/index';

function ProductDetails() {
    const {piD} = useParams();
    var pType = ""
    var catType = ""
    var dirName = ""

    if(piD >= 1 && piD <= 5){
        pType = 'doorbell'
        catType = 'DoorbellCatalog'
        dirName = 'doorbells'
    }
    if(piD >= 6 && piD <= 10){
        pType = 'doorlock'
        catType = 'DoorlockCatalog'
        dirName = 'doorlocks'
    }
    if(piD >= 11 && piD <= 15){
        pType = 'speaker'
        catType = 'SpeakerCatalog'
        dirName = 'speakers'
    }
    if(piD >= 16 && piD <= 20){
        pType = 'light'
        catType = 'LightingCatalog'
        dirName = 'lightings'
    }
    if(piD >= 21 && piD <= 25){
        pType = 'thermostat'
        catType = 'ThermostatCatalog'
        dirName = 'thermostats'
    }
    const idx = (piD - 1) % 5;
    return (
        <div id="content">
      <div class="post">
      <div class="entry">
          <table id="bestseller">
            <tr></tr>
        <td><div id="shop_item">
            <h3>{items.ProductCatalog[`${catType}`][`${pType}`][`${idx}`].name}</h3>
            <h3>{items.ProductCatalog[`${catType}`][`${pType}`][`${idx}`].price}</h3>
            <ul>
                <li id="item"><img src={require(`../images/${dirName}/${items.ProductCatalog[`${catType}`][`${pType}`][`${idx}`].image}`)} alt="" /></li>
            </ul>
            <h3>{items.ProductCatalog[`${catType}`][`${pType}`][`${idx}`].description}</h3>
            </div>
        </td>
        </table>
        </div>
        </div>
        </div>
  );
}	

export default ProductDetails;