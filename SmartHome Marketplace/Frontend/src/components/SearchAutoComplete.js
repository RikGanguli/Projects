import React, { useState, useEffect } from 'react';
import axios from 'axios';
import Autosuggest from 'react-autosuggest';
import '../style.css';
import Thumbnail from './Thumbnail';
import { items } from '../items/index';

const SearchAutoComplete = () => {
  const [value, setValue] = useState('');
  const [suggestions, setSuggestions] = useState([]);
  const [selectedCategory, setSelectedCategory] = useState(null);
  const [selectedProduct, setSelectedProduct] = useState(null);

  const handleInputChange = (event, { newValue }) => {
    setValue(newValue);
  };

  
  const onSuggestionsFetchRequested = ({ value }) => {
    axios.get(`http://localhost:8081/autocomplete/${value}`)
      .then(response => {
        setSuggestions(response.data);
      })
      .catch(error => {
        console.error('Autocomplete failed:', error.message);
      });
  };

  const onSuggestionsClearRequested = () => {
    setSuggestions([]);
  };

  const getSuggestionValue = suggestion => suggestion.productName;

  const renderSuggestion = suggestion => (
    <div>
      {suggestion.productName}
    </div>
  );

  const handleSearch = () => {
    
    console.log('Search:', value);
    

   
    const selected = suggestions.find(suggestion => getSuggestionValue(suggestion) === value);

   
    if (selected) {
      console.log(selected);
      console.log('Selected Category:', selected.category);
      console.log('Selected Product:', selected.product);

      setSelectedCategory(selected.category);
      setSelectedProduct(selected.product);
    } else {
      console.log("no product");
    }
  };

  const renderProducts = () => {
    if (selectedCategory && selectedProduct) {
      const products = items.ProductCatalog[selectedCategory][selectedProduct];

      if (products) {
        return products.map(product => (
          <Thumbnail
            key={product.id}
            piD={product.id}
            name={product.name}
            price={product.price}
            description={product.description}
            image={product.image}
            pd={`${selectedCategory.toLowerCase()}/${selectedProduct.toLowerCase()}/`}
          />
        ));
      }
    }

    return null;
  };

  return (
    <div id='content'>
      <div>
        <Autosuggest
          suggestions={suggestions}
          onSuggestionsFetchRequested={onSuggestionsFetchRequested}
          onSuggestionsClearRequested={onSuggestionsClearRequested}
          getSuggestionValue={getSuggestionValue}
          renderSuggestion={renderSuggestion}
          inputProps={{
            placeholder: 'Type to search...',
            type: 'text',
            value: value,
            onChange: handleInputChange,
          }}
        />
        <button onClick={handleSearch}>Search</button>

        {renderProducts()}
      </div>
    </div>
  );
};

export default SearchAutoComplete;
