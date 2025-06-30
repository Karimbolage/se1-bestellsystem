package datamodel;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

/**
 * Tests for Customer class: [300..399] Namens-Tests für das Factory/immutable Modell.
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class Customer_300_Name_Tests {

    private final DataFactory factory = DataFactory.getInstance();

    /*
     * Test cases 300: Zwei Argumente, z.B. Vorname und Nachname.
     */
    @Test @Order(300)
    void test300_FirstAndLastName() {
        Optional<Customer> c = factory.createCustomer("Eric", "Meyer");
        assertTrue(c.isPresent());
        assertEquals("Eric", c.get().firstName());
        assertEquals("Meyer", c.get().lastName());
    }

    @Test @Order(301)
    void test301_LastNameOnly() {
        Optional<Customer> c = factory.createCustomer("", "Meyer");
        assertTrue(c.isPresent());
        assertEquals("", c.get().firstName());
        assertEquals("Meyer", c.get().lastName());
    }

    @Test @Order(303)
    void test303_BothNamesEmptyIsInvalid() {
        Optional<Customer> c = factory.createCustomer("", "");
        assertTrue(c.isEmpty());
    }

    /*
     * Test cases 310: Ein String, verschiedene Namensschreibweisen.
     */
    @Test @Order(310)
    void test310_SingleStringNameStyle1() {
        Optional<Customer> c = factory.createCustomer("Eric Meyer");
        assertTrue(c.isPresent());
        assertEquals("Eric", c.get().firstName());
        assertEquals("Meyer", c.get().lastName());
    }

    @Test @Order(311)
    void test311_SingleStringNameStyle2() {
        Optional<Customer> c = factory.createCustomer("Meyer, Eric");
        assertTrue(c.isPresent());
        assertEquals("Eric", c.get().firstName());
        assertEquals("Meyer", c.get().lastName());
    }

    @Test @Order(312)
    void test312_SingleStringNameWithSemicolon() {
        Optional<Customer> c = factory.createCustomer("Meyer; Eric");
        assertTrue(c.isPresent());
        assertEquals("Eric", c.get().firstName());
        assertEquals("Meyer", c.get().lastName());
    }

    @Test @Order(313)
    void test313_SingleStringNameWithDot() {
        Optional<Customer> c = factory.createCustomer("E. Meyer");
        assertTrue(c.isPresent());
        assertEquals("E.", c.get().firstName());
        assertEquals("Meyer", c.get().lastName());
    }

    /*
     * Test cases 320: Doppelter Nachname mit '-'.
     */
    @Test @Order(320)
    void test320_DoubleLastName() {
        Optional<Customer> c = factory.createCustomer("Tim Schulz-Mueller");
        assertTrue(c.isPresent());
        assertEquals("Tim", c.get().firstName());
        assertEquals("Schulz-Mueller", c.get().lastName());
    }

    @Test @Order(321)
    void test321_DoubleLastNameComma() {
        Optional<Customer> c = factory.createCustomer("Schulz-Mueller, Tim");
        assertTrue(c.isPresent());
        assertEquals("Tim", c.get().firstName());
        assertEquals("Schulz-Mueller", c.get().lastName());
    }

    @Test @Order(322)
    void test322_DoubleLastNameSemicolon() {
        Optional<Customer> c = factory.createCustomer("Schulz-Mueller; Tim");
        assertTrue(c.isPresent());
        assertEquals("Tim", c.get().firstName());
        assertEquals("Schulz-Mueller", c.get().lastName());
    }

    /*
     * Test cases 330: Einzel-Argument Konstruktor (über Factory)
     */
    @Test @Order(330)
    void test330_SingleArgNameStyle1() {
        Optional<Customer> c = factory.createCustomer("Eric Meyer");
        assertTrue(c.isPresent());
        assertEquals("Eric", c.get().firstName());
        assertEquals("Meyer", c.get().lastName());
    }

    @Test @Order(331)
    void test331_SingleArgNameStyle2() {
        Optional<Customer> c = factory.createCustomer("Meyer, Eric");
        assertTrue(c.isPresent());
        assertEquals("Eric", c.get().firstName());
        assertEquals("Meyer", c.get().lastName());
    }

    @Test @Order(332)
    void test332_SingleArgNameSemicolon() {
        Optional<Customer> c = factory.createCustomer("Meyer; Eric");
        assertTrue(c.isPresent());
        assertEquals("Eric", c.get().firstName());
        assertEquals("Meyer", c.get().lastName());
    }

    @Test @Order(333)
    void test333_SingleArgDoubleLastName() {
        Optional<Customer> c = factory.createCustomer("Tim Schulz-Mueller");
        assertTrue(c.isPresent());
        assertEquals("Tim", c.get().firstName());
        assertEquals("Schulz-Mueller", c.get().lastName());
    }

    @Test @Order(334)
    void test334_SingleArgDoubleLastNameComma() {
        Optional<Customer> c = factory.createCustomer("Schulz-Mueller, Tim");
        assertTrue(c.isPresent());
        assertEquals("Tim", c.get().firstName());
        assertEquals("Schulz-Mueller", c.get().lastName());
    }

    @Test @Order(335)
    void test335_SingleArgDoubleLastNameSemicolon() {
        Optional<Customer> c = factory.createCustomer("Schulz-Mueller; Tim");
        assertTrue(c.isPresent());
        assertEquals("Tim", c.get().firstName());
        assertEquals("Schulz-Mueller", c.get().lastName());
    }
}