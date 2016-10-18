package io.spring.cloud.samples.commerce.ui.controllers;

import java.util.List;

import io.spring.cloud.samples.commerce.ui.services.items.Item;
import io.spring.cloud.samples.commerce.ui.services.items.ItemService;
import io.spring.cloud.samples.commerce.ui.services.items.Price;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UIController {
	@Autowired
    ItemService service;
	
	@RequestMapping("/items")
    public String getItem() {
        return service.getItems();
    }
	
	@RequestMapping("/category/{cat}")
    public String getItem(@PathVariable("cat") String category) {
        return service.getItemsByCategory(category);
    }
	
	@RequestMapping("/item/{id}")
	public String itemsById(@PathVariable("id") Long id) {
		return service.getItemsById(id);
	}
	
}
