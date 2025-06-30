package datamodel;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
/**
 * Tests for {@link Customer} class: [100..199] with tested Constructors:
 * <pre>
 * - Customer()             // default constructor
 * - Customer(String name)  // constructor with name argument
 * </pre>
 * @author <code style=color:blue>{@value application.package_info#Author}</code>
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class Customer_100_Constructor_Tests {

    private final DataFactory factory = DataFactory.getInstance();

    @Test @Order(100)
    void test100_DefaultCustomerWithEmptyName() {
        Optional<Customer> c1 = factory.createCustomer("");
        assertTrue(c1.isEmpty());
    }

    @Test @Order(110)
    void test110_CreateRegularFirstLastName() {
        Optional<Customer> kundeOpt = factory.createCustomer("Eric Meyer");
        assertTrue(kundeOpt.isPresent());
        Customer kunde = kundeOpt.get();
        assertEquals("Eric", kunde.firstName());
        assertEquals("Meyer", kunde.lastName());
    }

    @Test @Order(111)
    void test111_CreateRegularLastCommaFirstName() {
        Optional<Customer> kundeOpt = factory.createCustomer("Meyer, Eric");
        assertTrue(kundeOpt.isPresent());
        Customer kunde = kundeOpt.get();
        assertEquals("Eric", kunde.firstName());
        assertEquals("Meyer", kunde.lastName());
    }

    @Test @Order(112)
    void test112_CreateLastNameOnly() {
        Optional<Customer> kundeOpt = factory.createCustomer("Meyer");
        assertTrue(kundeOpt.isPresent());
        Customer kunde = kundeOpt.get();
        assertEquals("", kunde.firstName());
        assertEquals("Meyer", kunde.lastName());
    }

    @Test @Order(120)
    void test120_CornerShortestPossibleNames() {
        Optional<Customer> c1 = factory.createCustomer("E M");
        assertTrue(c1.isPresent());
        assertEquals("E", c1.get().firstName());
        assertEquals("M", c1.get().lastName());

        Optional<Customer> c2 = factory.createCustomer("M, E");
        assertTrue(c2.isPresent());
        assertEquals("E", c2.get().firstName());
        assertEquals("M", c2.get().lastName());

        Optional<Customer> c3 = factory.createCustomer("M");
        assertTrue(c3.isPresent());
        assertEquals("", c3.get().firstName());
        assertEquals("M", c3.get().lastName());
    }

    @Test @Order(121)
    void test121_LongFirstAndLastName() {
        Optional<Customer> c1 = factory.createCustomer("Nadine Ulla Maxine Adriane Blumenfeld");
        assertTrue(c1.isPresent());
        assertEquals("Nadine Ulla Maxine Adriane", c1.get().firstName());
        assertEquals("Blumenfeld", c1.get().lastName());
    }

    @Test @Order(122)
    void test122_LongFirstAndMultipartLastName() {
        Optional<Customer> c1 = factory.createCustomer("Nadine Ulla Maxine Adriane Von-Blumenfeld-Bozo");
        assertTrue(c1.isPresent());
        assertEquals("Nadine Ulla Maxine Adriane", c1.get().firstName());
        assertEquals("Von-Blumenfeld-Bozo", c1.get().lastName());
    }

    @Test @Order(123)
    void test123_LongMultipartLastCommaFirstName() {
        Optional<Customer> c1 = factory.createCustomer("Von-Blumenfeld-Bozo, Nadine Ulla Maxine Adriane");
        assertTrue(c1.isPresent());
        assertEquals("Nadine Ulla Maxine Adriane", c1.get().firstName());
        assertEquals("Von-Blumenfeld-Bozo", c1.get().lastName());
    }

    @Test @Order(130)
    void test130_EmptyNameIsInvalid() {
        Optional<Customer> c1 = factory.createCustomer("");
        assertTrue(c1.isEmpty());
    }

    @Test @Order(131)
    void test131_NullArgumentIsInvalid() {
        Optional<Customer> c1 = factory.createCustomer((String) null);
        assertTrue(c1.isEmpty());
    }
}