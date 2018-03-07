package java8.ex06;


import java.util.Objects;
import java.util.function.Supplier;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java8.data.Person;

/**
 * Exercice 06 - java.util.function.Supplier
 */
public class Function_06_Test {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    // tag::formatAge[]
    // compléter la méthode
    // la méthode retourne une chaîne de caractères de la forme [age=<AGE>] (exemple : [age=12])
    String formatAge(Supplier<Person> supplier) {
        
        return String.format("[age=%d]", supplier.get().getAge());
    }
    // end::formatAge[]


    @Test
    public void test_supplier_formatAge() throws Exception {
        // compléter le test unitaire pour qu'il soit passant
        String result = formatAge(() -> new Person("", "", 35, ""));

        assert result.equals("[age=35]");
    }

    @Test
    public void test_supplier_requireNonNull() throws Exception {

        expectedException.expect(NullPointerException.class);
        expectedException.expectMessage("require non null object");

        //  compléter le test unitaire pour qu'il soit passant
        Supplier<String> supplier = () -> "require non null object";

        // Avec un paramètre null, cette méthode déclenche un NullPointerException
        Objects.requireNonNull(null, supplier);

    }

}
