package datamodel;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;
import java.util.stream.StreamSupport;

/**
 * Tests für Kontakte eines Customers im Factory-System.
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class Customer_400_Contacts_Tests {

    private DataFactory factory;
    private Validator validator;
    private Customer c1;

    @BeforeEach
    public void setUpBeforeEach() {
        factory = DataFactory.getInstance();
        validator = new Validator();
        Optional<Customer> opt = factory.createCustomer("Eric Meyer");
        c1 = opt.orElseThrow(() -> new RuntimeException("Konnte Testkunde nicht erzeugen"));
    }

    @Test
    void testCustomerInvalidEmail() {
        Optional<Customer> opt = factory.createCustomer("Mandy", "Mondschein", "invalid<>email");
        assertTrue(opt.isEmpty());
    }


    @Test
    void testCreateCustomerWithNoValidContact() {
        // Weder das noch das andere ist ein gültiger Kontakt
        Optional<Customer> opt = factory.createCustomer("Tim", "Tester", "blablubb", "Foo-bar");
        // Es wird trotzdem ein Customer angelegt, aber ohne Kontakte!
        assertTrue(opt.isPresent());
        Customer c = opt.get();
        assertEquals(0, c.contactsCount());
    }

    @Test
    void testCreateCustomerManyContact() {
        // Weder das noch das andere ist ein gültiger Kontakt
        Optional<Customer> opt = factory.createCustomer("Tim", "Tester", "030 782 45 93", "anne24@yahoo.de", "anne24@gmx.de");
        // Es wird trotzdem ein Customer angelegt, aber ohne Kontakte!
        assertTrue(opt.isPresent());
        Customer c = opt.get();
        assertEquals(3, c.contactsCount());
    }
}