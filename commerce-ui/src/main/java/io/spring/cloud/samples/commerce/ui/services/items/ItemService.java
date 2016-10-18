package io.spring.cloud.samples.commerce.ui.services.items;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.http.HttpStatus;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.cloudfoundry.com.fasterxml.jackson.databind.JsonNode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;


@Service
@EnableConfigurationProperties(ItemProperties.class)
public class ItemService {
	
	@Autowired
	ItemProperties itemProperties;

    @Autowired
    RestTemplate restTemplate;

    //Items
    ArrayList<Item> items = new ArrayList<Item>();
    
    @HystrixCommand(fallbackMethod = "fallbackItem")
    public String getItems() {
    	ResponseEntity<String> items = 
    			  restTemplate.getForEntity("http://Item/items", String.class);
    	
    	ResponseEntity<String> prices = 
  			  restTemplate.getForEntity("http://Price/prices", String.class);
  	
    	/*assertThat(response.getStatusCode(), is(HttpStatus.OK));
    	ObjectMapper mapper = new ObjectMapper();
    	com.fasterxml.jackson.databind.JsonNode root = mapper.readTree(response.getBody());
    	JsonNode name = root.path("name");
    	assertThat(name.asText(), is("bar"));*/
    	//return restTemplate.getForObject(url, responseType, urlVariables)
        	//	getForObject("http://Item/random", Item.class);
    	return items.getBody()+ prices.getBody();
    	//return parseJasonResponse(items.getBody(),"");
    }
    
    public String getItemsByCategory(String category){
    	ResponseEntity<String> items = 
  			  restTemplate.getForEntity("http://Item/category/"+category, String.class);
    	ResponseEntity<String> prices = 
    			  restTemplate.getForEntity("http://Price/prices", String.class);
    	return items.getBody()+ prices.getBody();
    }
    
    public String getItemsById(Long id){
    	ResponseEntity<String> item = 
  			  restTemplate.getForEntity("http://Item/item/"+id.toString(), String.class);
    	ResponseEntity<String> prices = 
    			  restTemplate.getForEntity("http://Price/prices", String.class);
    	return item.getBody()+ prices.getBody();
    }

    private String fallbackItem() {
        return itemProperties.getRandomPriceFromProperty().toString();
    	//return new Item(9999L, "This", "is", "a test");
    }
    
    private String parseJasonResponse(String itemsResponse, String pricesResponse){
    	items = new ArrayList<Item>();
    	//JSONObject jsonobj=new JSONObject(itemsResponse);
    	JSONArray jsonarray = new JSONArray(itemsResponse);
    	for (int i = 0; i < jsonarray.length(); i++) {
    	    JSONObject jsonobject = jsonarray.getJSONObject(i);
    	    Item tempItem=new Item();
    	    tempItem.setId(Long.parseLong(jsonobject.getString("id")));
    	    tempItem.setName(jsonobject.getString("name"));
    	    tempItem.setDescription(jsonobject.getString("description"));
    	    tempItem.setCategory(jsonobject.getString("category"));
    	    items.add(tempItem);
    	}
    	String response="";
    	for (int i=0; i<items.size(); i++){
    		System.out.println(items.get(i).toString()+"-------------------------------");
    		response+=items.get(i).toString();
    	}
    	return response;
    }
}
