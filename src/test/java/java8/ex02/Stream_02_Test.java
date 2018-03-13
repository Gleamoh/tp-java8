package java8.ex02;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.Collections;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.Test;

import java8.data.Data;
import java8.data.domain.Customer;
import java8.data.domain.Order;
import java8.data.domain.Pizza;

/**
 * Exercice 02 - Transformation
 */
public class Stream_02_Test {

	@Test
	public void test_map() throws Exception {

		List<Order> orders = new Data().getOrders();

		// Trouver la liste des clients ayant déjà passés une commande

		// grouper les orders par client
		Map<Customer, List<Order>> customerOrders = orders.stream().collect(Collectors.groupingBy(Order::getCustomer));

		// recuper la listes des clients
		List<Customer> result = customerOrders.keySet().stream().collect(Collectors.toList());

		assertThat(result, hasSize(2));
	}

	@Test
	public void test_flatmap() throws Exception {

		List<Order> orders = new Data().getOrders();

		// calculer les statistiques sur les prix des pizzas vendues

		// appique un flatmap pour fusion la liste de liste de pizza en une
		// seule liste de pizza
		List<Pizza> pizzas = orders.stream().map(Order::getPizzas).flatMap(list -> list.stream())
				.collect(Collectors.toList());
		
		// utiliser l'opération summaryStatistics
		IntSummaryStatistics result = pizzas.stream().collect(Collectors.summarizingInt(Pizza::getPrice));

		assertThat(result.getSum(), is(10900L));
		assertThat(result.getMin(), is(1000));
		assertThat(result.getMax(), is(1375));
		assertThat(result.getCount(), is(9L));
	}
}
