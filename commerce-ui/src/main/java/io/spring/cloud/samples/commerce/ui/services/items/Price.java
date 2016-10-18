package io.spring.cloud.samples.commerce.ui.services.items;

public class Price {
	private String itemId;
	private String price;
	
	public Price(){
		
	}
	public Price (String itemId, String price){
		this.itemId=itemId;
		this.price=price;
	}
	
	public String getItemId() {
		return itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	
	@Override
  	public String toString() {
  		return "Price [id=" + itemId + ", price=" + price + "]\n";
  	}
}
