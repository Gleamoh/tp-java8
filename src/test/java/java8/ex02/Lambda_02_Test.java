package java8.ex02;

import java8.data.Account;
import java8.data.Data;
import java8.data.Person;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;


/**
 * Exercice 02 - Map
 */
public class Lambda_02_Test {

    // tag::PersonToAccountMapper[]
    interface PersonTo<T> {
        T map(Person p);
    }
    
    //ou
    
//    interface Mapper<A, B> {
//    	A map(B b)
//    }
    
//    interface PersonToFirstname {
//    	String map(Person p);
//    }
    // end::PersonToAccountMapper[]

    // tag::map[]
    private <T> List<T> map(List<Person> personList, PersonTo<T> mapper) {
        // TODO implémenter la méthode pour transformer une liste de personnes en liste de comptes
    	List<T> accounts = new ArrayList<>();
    	for (Person p : personList) {
			accounts.add(mapper.map(p));
		}
        return accounts;
    }
    // end::map[]

    // tag::map[]
//    private List<String> map(List<Person> personList, PersonToFirstname mapper) {
//        // TODO implémenter la méthode pour transformer une liste de personnes en liste de comptes
//    	List<String> accounts = new ArrayList<>();
//    	for (Person p : personList) {
//			accounts.add(mapper.map(p));
//		}
//        return accounts;
//    }
    // end::map[]

    // tag::test_map_person_to_account[]
    @Test
    public void test_map_person_to_account() throws Exception {

        List<Person> personList = Data.buildPersonList(100);

        //  transformer la liste de personnes en liste de comptes
        //  tous les objets comptes ont un solde à 100 par défaut
        List<Account> result = map(personList, p -> {
        	Account a = new Account();
        	a.setOwner(p);
        	a.setBalance(100);
        	return a;
        });

        assert result.size() == personList.size();
        for (Account account : result) {
            assert account.getBalance().equals(100);
            assert account.getOwner() != null;
        }
    }
    // end::test_map_person_to_account[]

    // tag::test_map_person_to_firstname[]
    @Test
    public void test_map_person_to_firstname() throws Exception {

        List<Person> personList = Data.buildPersonList(100);

        //  transformer la liste de personnes en liste de prénoms
        List<String> result = map(personList, p -> p.getFirstname());

        assert result.size() == personList.size();
        for (String firstname : result) {
            assert firstname.startsWith("first");
        }
    }
    // end::test_map_person_to_firstname[]
}
