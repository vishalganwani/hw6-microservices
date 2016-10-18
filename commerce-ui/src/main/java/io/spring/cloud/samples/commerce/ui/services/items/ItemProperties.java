package io.spring.cloud.samples.commerce.ui.services.items;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

@ConfigurationProperties(prefix = "commerce")
@RefreshScope
public class ItemProperties {
	
	  Map<String,String> prices = new HashMap<String,String>();
	  private static final Random RANDOM = new Random();

	  
	  public Map<String, String> getPrices() {
	    return prices;
	  }

	  public void setPrices(Map<String, String> prices) {
	    this.prices = prices;
	  }

	  public String getPriceForItem(String itemId) {
	    return prices.get(itemId);
	  }

	  public void putPrice(String itemId, String price) {
	    prices.put(itemId, price);
	  }
	

	  public Price getRandomPriceFromProperty() {
	    if(prices == null || prices.size() == 0) {
	      return new Price("0000", "00000");
	    }
	    else {
	      int index = RANDOM.nextInt(prices.size());
	      return new Price(prices.get("1001"), prices.get("1001"));
	    }
	  }
	
	
	  /*List<Item> items = new ArrayList<Item>();

	  public List<Item> getMessages() {
	    return items;
	  }

	  public void setMessages(List<Item> messages) {
	    this.items = messages;
	  }

	  public void addMessage(Item message) {
	    this.items.add(message);
	  }

	  public Item getItemsFromProperty() {
	    if(items == null || items.size() == 0) {
	      return new Item(9999L, "This", "is", "a test");
	    }
	    else {
	      
	      return new Item(9999L, "This", "is", "a test");
	    }
	  }*/

}
