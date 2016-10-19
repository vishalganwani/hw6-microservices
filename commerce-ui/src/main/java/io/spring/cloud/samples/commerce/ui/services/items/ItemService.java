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
    	ResponseEntity<String> itemsResponse = 
    			  restTemplate.getForEntity("http://Item/items", String.class);
    	ResponseEntity<String> prices = 
  			  restTemplate.getForEntity("http://Price/prices", String.class);
    	parseJasonResponse(itemsResponse.getBody(),prices.getBody());	
    	return printItems();
    }
    
    @HystrixCommand(fallbackMethod = "fallbackItemCat")
    public String getItemsByCategory(String category){
    	ResponseEntity<String> itemsResponse = 
  			  restTemplate.getForEntity("http://Item/category/"+category, String.class);
    	ResponseEntity<String> prices = 
    			  restTemplate.getForEntity("http://Price/prices", String.class);
    	parseJasonResponse(itemsResponse.getBody(),prices.getBody());	
    	return printItems();
    }
    
    
    @HystrixCommand(fallbackMethod = "fallbackItemId")
    public String getItemsById(Long id){
    	ResponseEntity<String> itemsResponse = 
  			  restTemplate.getForEntity("http://Item/item/"+id.toString(), String.class);
    	ResponseEntity<String> prices = 
    			  restTemplate.getForEntity("http://Price/prices", String.class);
    	parseJasonResponse(itemsResponse.getBody(),prices.getBody());	
    	return printItems();
    }

    private String fallbackItem() {
        return itemProperties.getItemInfoFromProperty().toString();
    }
    
    private String fallbackItemId(Long id) {
        return itemProperties.getItemInfoFromProperty().toString();
    }

    private String fallbackItemCat(String category) {
        return itemProperties.getItemInfoFromProperty().toString();
    }

    private void parseJasonResponse(String itemsResponse,String prices){
    	items = new ArrayList<Item>();
    	JSONArray jsonarray = new JSONArray(itemsResponse);
    	for (int i = 0; i < jsonarray.length(); i++) {
    	    JSONObject jsonobject = jsonarray.getJSONObject(i);
    	    Item tempItem=new Item();
    	    tempItem.setId(jsonobject.getLong("id"));
    	    tempItem.setName(jsonobject.getString("name"));
    	    tempItem.setDescription(jsonobject.getString("description"));
    	    tempItem.setCategory(jsonobject.getString("category"));
    	    items.add(tempItem);
    	}
    	for (int i=0;i<items.size();i++){
    		Long newId = items.get(i).getId();
        	String idPrice = extractPrice(prices,newId);
        	items.get(i).setPrice(idPrice);
    	}
    }
    
    private String extractPrice(String prices, Long id){
    	int start = prices.indexOf("\""+id.toString()+"\":")+id.toString().length()+4;    	
    	int end = prices.indexOf("\"",start);
    	String idPrice = prices.substring(start, end);    	
    	return idPrice;
    }
    
    private String printItems(){    	
    	String response="";
    	for (int i=0; i<items.size(); i++){
    		response+=items.get(i).toString()+"\n";
    	}
    	
    	return response;
    }
    
}
