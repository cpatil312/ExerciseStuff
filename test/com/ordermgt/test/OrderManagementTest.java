package com.ordermgt.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.ordermgt.exception.InvalidExclusionItems;
import com.ordermgt.exception.InvalidMenuItemException;
import com.ordermgt.main.OrderManagement;

public class OrderManagementTest {

	private OrderManagement orderManagement = new OrderManagement();

	@Test
	public void test01() {
		Double price1 = orderManagement.calculateOrderPrice("Chai, -sugar, -milk");
		Assert.assertEquals(new Double(2.5), price1);
		Double price2 = orderManagement.calculateOrderPrice("Banana Smoothie, -milk, -milk");
		Assert.assertEquals(new Double(5.0), price2);
	}

	@Test
	public void test02() {
		Double price1 = orderManagement.calculateOrderPrice("Chai");
		Assert.assertEquals(new Double(4.0), price1);
		Double price2 = orderManagement.calculateOrderPrice("Banana Smoothie");
		Assert.assertEquals(new Double(6.0), price2);
	}

	@Test
	public void test03() {
		Double price = orderManagement.calculateOrderPrice("Chai, -sugar, -milk, -masala");
		Assert.assertEquals(new Double(2.5), price);
	}

	@Test(expected = InvalidMenuItemException.class)
	public void test04() {
		orderManagement.calculateOrderPrice("-water, -sugar, -milk");
	}

	@Test
	public void test05() {
		String order1 = "Chai, -sugar, -milk, -masala";
		String order2 = "-water, -sugar, -milk";
		String order3 = "Coffee";
		List<Double> prices = orderManagement
				.calculateOrderPrice((String[]) Arrays.asList(order1, order2, order3).toArray());
		assertFalse(prices.isEmpty());
		assertTrue(prices.size() == 2);
		Assert.assertEquals(new Double(2.5), prices.get(0));
		Assert.assertEquals(new Double(5.0), prices.get(1));
	}
	
	@Test(expected = InvalidExclusionItems.class)
	public void test06() {
		orderManagement.calculateOrderPrice("Coffee, -water, -sugar, -milk, -coffee");
	}
}
