package java8.ex04;

import java8.data.Account;
import java8.data.Data;
import java8.data.Person;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Exercice 04 - FuncCollection Exercice synthèse des exercices précédents
 */
public class Lambda_04_Test {

	// tag::interfaces[]
	interface GenericPredicate<T> {
		// un predicate retourne un boolean
		boolean function(T t);
	}

	interface GenericMapper<T, E> {
		//
		E function(T t);
	}

	interface Processor<T> {
		void function(T t);

	}
	// end::interfaces[]

	// tag::FuncCollection[]
	class FuncCollection<T> {

		private Collection<T> list = new ArrayList<>();

		public void add(T a) {
			list.add(a);
		}

		public void addAll(Collection<T> all) {
			for (T el : all) {
				list.add(el);
			}
		}
		// end::FuncCollection[]

		// tag::methods[]
		private FuncCollection<T> filter(GenericPredicate<T> predicate) {
			FuncCollection<T> result = new FuncCollection<>();
			for (T t : list) {
				if (predicate.function(t))
					result.add(t);
			}
			return result;
		}

		private <E> FuncCollection<E> map(GenericMapper<T, E> mapper) {
			FuncCollection<E> result = new FuncCollection<>();
			for (T t : list) {
				result.add(mapper.function(t));
			}
			return result;
		}

		private void forEach(Processor<T> processor) {
			for (T t : list) {
				processor.function(t);
			}
		}
		// end::methods[]

	}

	// tag::test_filter_map_forEach[]
	@Test
	public void test_filter_map_forEach() throws Exception {

		List<Person> personList = Data.buildPersonList(100);
		FuncCollection<Person> personFuncCollection = new FuncCollection<>();
		personFuncCollection.addAll(personList);

		personFuncCollection
				// filtrer, ne garder uniquement que les personnes ayant un
				// age > 50
				.filter(p -> p.getAge() > 50)
				// transformer la liste de personnes en liste de comptes.
				// Un compte a par défaut un solde à 1000.
				.map(p -> {
					Account a = new Account();
					a.setOwner(p);
					a.setBalance(1000);
					return a;
				})
				// vérifier que chaque compte a un solde à 1000.
				// vérifier que chaque titulaire de compte a un age > 50
				.forEach(a -> {
					assert a.getBalance() == 1000;
					assert a.getOwner().getAge() > 50;
				});

	}
	// end::test_filter_map_forEach[]

	// tag::test_filter_map_forEach_with_vars[]
	@Test
	public void test_filter_map_forEach_with_vars() throws Exception {

		List<Person> personList = Data.buildPersonList(100);
		FuncCollection<Person> personFuncCollection = new FuncCollection<>();
		personFuncCollection.addAll(personList);

		// créer un variable filterByAge de type GenericPredicate
		// filtrer, ne garder uniquement que les personnes ayant un age >
		// 50
		GenericPredicate<Person> filterByAge = p -> p.getAge() > 50;

		// créer un variable mapToAccount de type GenericMapper
		// transformer la liste de personnes en liste de comptes. Un compte
		// a par défaut un solde à 1000.
		GenericMapper<Person, Account> mapToAccount = p -> {
			Account a = new Account();
			a.setOwner(p);
			a.setBalance(1000);
			return a;
		};

		// créer un variable verifyAccount de type GenericMapper
		// vérifier que chaque compte a un solde à 1000.
		// vérifier que chaque titulaire de compte a un age > 50
		Processor<Account> verifyAccount = a -> {
			assert a.getBalance() == 1000;
			assert a.getOwner().getAge() > 50;
		};

		personFuncCollection.filter(filterByAge).map(mapToAccount).forEach(verifyAccount);

	}
	// end::test_filter_map_forEach_with_vars[]

}
