package com.ordermgt.main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.ordermgt.exception.InvalidExclusionItems;
import com.ordermgt.exception.InvalidMenuItemException;

public class OrderManagement {

	private Map<String, Set<String>> menuItemsWithIngredients = new HashMap<>();
	private Map<String, Double> menuItemsWithPrice = new HashMap<>();
	private Map<String, Double> ingrediantsWithPrice = new HashMap<>();

	{
		menuItemsWithPrice.put("Coffee", 5.0);
		menuItemsWithPrice.put("Chai", 4.0);
		menuItemsWithPrice.put("Banana Smoothie", 6.0);

		ingrediantsWithPrice.put("milk", 1.0);
		ingrediantsWithPrice.put("sugar", 0.5);
		ingrediantsWithPrice.put("soda", 0.5);
		ingrediantsWithPrice.put("mint", 0.5);
		ingrediantsWithPrice.put("water", 0.5);

		menuItemsWithIngredients.put("Coffee", new HashSet<>(Arrays.asList("coffee", "milk", "sugar", "water")));
		menuItemsWithIngredients.put("Chai", new HashSet<>(Arrays.asList("tea", "milk", "sugar", "water")));
		menuItemsWithIngredients.put("Banana Smoothie", new HashSet<>(Arrays.asList("banana", "milk", "sugar", "water")));

	}

	public List<Double> calculateOrderPrice(String orders[]) {
		List<Double> prices = new ArrayList<>();
		for (int i = 0; i < orders.length; i++) {
			try {
				prices.add(calculateOrderPrice(orders[i]));
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
		return prices;
	}

	public Double calculateOrderPrice(String order) {
		String menuItem = getMenuItem(order);
		Set<String> exclusions = getExclusions(order, menuItem);
		Double totalPrice = menuItemsWithPrice.get(menuItem);
		if (exclusions.isEmpty()) {
			return totalPrice;
		}
		validateExclusions(exclusions, menuItem);
		Double excludedItemPrice = exclusions.stream().mapToDouble(item -> ingrediantsWithPrice.get(item)).sum();
		return totalPrice - excludedItemPrice;
	}

	private Set<String> getExclusions(String order, String menuItem) {
		Set<String> exclusions = new HashSet<>();
		String ingredients[] = order.split(",");
		for (int i = 1; i < ingredients.length; i++) {
			String ingredientInput = ingredients[i].trim();
			if (ingredientInput.startsWith("-")) {
				String ingredient = ingredientInput.substring(1);
				if (isValidIngredient(ingredient, menuItem)) {
					exclusions.add(ingredient);
				}
			}
		}
		return exclusions;
	}

	private String getMenuItem(String order) {
		String menuItem = order.split(",")[0].trim();
		if (!isValidMenuItem(menuItem)) {
			throw new InvalidMenuItemException(menuItem + " This is Invalid MenuItem");
		}
		return menuItem;
	}

	private boolean isValidMenuItem(String menuItem) {
		return menuItemsWithPrice.containsKey(menuItem);
	}

	private boolean isValidIngredient(String ingredient, String menuItem) {
		return menuItemsWithIngredients.get(menuItem).stream().anyMatch(item -> item.equalsIgnoreCase(ingredient));
	}

	private void validateExclusions(Set<String> exclusions, String menuItem) {
		if (menuItemsWithIngredients.get(menuItem).stream().allMatch(item -> exclusions.contains(item))) {
			throw new InvalidExclusionItems("Invalid exclusions for menuItem : " + menuItem);
		}
	}
}
