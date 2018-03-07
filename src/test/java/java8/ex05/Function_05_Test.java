package java8.ex05;

import java8.data.Data;
import java8.data.Person;
import org.junit.Test;

import java.util.List;
import java.util.function.Consumer;

/**
 * Exercice 5 - java.util.function.Consumer
 */
public class Function_05_Test {

    //tag::functions[]
    //  compléter la fonction
    //  modifier le mot de passe en "secret"
    Consumer<Person> changePasswordToSecret = p -> p.setPassword("secret");

    //  compléter la fonction
    //  vérifier que l'age > 4 avec une assertion JUnit
    Consumer<Person> verifyAge = p -> { assert p.getAge() > 4; };

    //  compléter la fonction
    //  vérifier que le mot de passe est "secret" avec une assertion JUnit
    Consumer<Person> verifyPassword = p -> { assert p.getPassword().equals("secret"); };
    //end::functions[]


    @Test
    public void test_consumer() throws Exception {
        List<Person> personList = Data.buildPersonList();

        // invoquer la méthode personList.forEach pour modifier les mots de passe en "secret"
        personList.forEach(changePasswordToSecret);

        // remplacer la boucle for par l'invocation de la méthode forEach
        // Utiliser la méthode andThen pour chaîner les vérifications verifyAge et verifyPassword
        // personList.forEach...
        for(Person p : personList) {
            verifyAge.accept(p);
            verifyPassword.accept(p);
        }
        
        personList.forEach(verifyAge.andThen(verifyPassword));
    }
}
