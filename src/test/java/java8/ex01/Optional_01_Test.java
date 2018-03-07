package java8.ex01;

import java8.data.Data;
import java8.data.Person;
import org.junit.Test;

import com.sun.org.apache.bcel.internal.generic.ReturnaddressType;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

/**
 * Exercice 01 - Optional
 */
public class Optional_01_Test {

	class NotFountException extends RuntimeException {
	}

	// tag::findMethod[]
	<T> Optional<T> find(List<T> list, Predicate<T> predicate) {
		T result = null;

		for (T p : list) {
			if (predicate.test(p)) {
				return Optional.of(p);
			}
		}

		return Optional.empty();
	}
	// end::findMethod[]

	// tag::findMethod[]
	<T> T find(List<T> list, Predicate<T> predicate, T defaultValue) {
		return find(list, predicate).orElse(defaultValue);
	}
	// end::findMethod[]
	
	@Test
	public void test_optional_found() throws Exception {

		List<Person> personList = Data.buildPersonList(100);

		//  invoquer la méthode find(List<T> list, Predicate<T> predicate)
		//  age == 10
		Optional<Person> result = find(personList, p -> p.getAge() == 10);

		assertThat(result, instanceOf(Optional.class));
		assertThat(result.isPresent(), is(true));
		assertThat(result.get(), instanceOf(Person.class));
		assertThat(result.get(), hasProperty("firstname", is("first_10")));
		assertThat(result.get(), hasProperty("age", is(10)));
	}

	@Test
	public void test_optional_notfound() throws Exception {

		List<Person> personList = Data.buildPersonList(100);

		//  invoquer la méthode find(List<T> list, Predicate<T> predicate)
		//  age == 400
		Optional<Person> result = find(personList, p -> p.getAge() == 400);

		assertThat(result, instanceOf(Optional.class));
		assertThat(result.isPresent(), is(false));
	}

	@Test(expected = NotFountException.class)
	public void test_optional_notfound_throw_exception() throws Exception {

		List<Person> personList = Data.buildPersonList(100);

		//  invoquer la méthode find(List<T> list, Predicate<T> predicate)
		//  age == 10 et firstname == "last_10"
		Optional<Person> result = find(personList, p -> p.getAge() == 10 && p.getFirstname() == "last_10");

		//  Utiliser la méthode orElseThrow pour déclencher l'exception NotFountException si non trouvé
		result.orElseThrow(() -> new NotFountException());
	}

	@Test
	public void test_optional_notfound_with_default_value() throws Exception {

		List<Person> personList = Data.buildPersonList(100);

		Person defaultValue = new Person();
		defaultValue.setFirstname("DEFAULT");
		defaultValue.setLastname("DEFAULT");

		//  invoquer la méthode find(List<T> list, Predicate<T> predicate, T
		// defaultValue)
		//  predicate => age == 400
		Person result = find(personList, p -> p.getAge() == 400, new Person("DEFAULT", "DEFAULT", 0, null));

		assertThat(result, notNullValue());
		assertThat(result, hasProperty("firstname", is("DEFAULT")));
		assertThat(result, hasProperty("lastname", is("DEFAULT")));
	}

}
