package datamodel;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;


/**
 * Tests for Customer class: [500..599] setName(name) extended
 * test cases:
 *  - Test cases 500: setName(name) with '-' connected multi-part last names.
 *  - Test cases 510: setName(name) with double first names.
 *  - Test cases 520: setName(name) with multiple first names.
 *  - Test cases 530: setName(name) with multiple first and last names.
 *  - Test cases 550: setName(name) with multiple, multi-dash first and last names.
 *  - Test cases 550: setName(name) with extreme long names.
 * @author sgra64
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class Customer_500_NameXXL_Tests {

    /*
     * Tested object is an instance of the {@link Customer} class.
     * Created for execution of every @Test method since the test
     * runner executes all @Test methods in parallel.
     */
    private DataFactory factory;// test-object, "unit-under-test"


    /**
     * Setup method executed before each @Test method, used to create
     * test-object or "unit-under-test".
     * @throws Exception if any exception occurs
     */
    @BeforeEach
    public void setUpBeforeEach() throws Exception {
        factory = DataFactory.getInstance();
    }


    /*
     * Test cases 500: setName(name) with '-' connected multi-part last names.
     */
    @Test @Order(500)
    void test500_setNameMultipartLastName() {
        Optional<Customer> custOpt = factory.createCustomer("Tim Schulz-Mueller-Meyer");
        assertTrue(custOpt.isPresent());
        Customer customer = custOpt.get();
        assertEquals("Schulz-Mueller-Meyer", customer.lastName());
    }

    @Test @Order(501)
    void test501_setNameMultipartLastName() {
        Optional<Customer> custOpt = factory.createCustomer("Schulz-Mueller-Meyer, Tim");    // name style 2
        assertTrue(custOpt.isPresent());
        Customer customer = custOpt.get();
        assertEquals("Tim", customer.firstName());
        assertEquals("Schulz-Mueller-Meyer", customer.lastName());
    }

    @Test @Order(502)
    void test502_setNameMultipartLastName() {
        Optional<Customer> custOpt = factory.createCustomer("Schulz-Mueller-Meyer; Tim");    // name style 3
        assertTrue(custOpt.isPresent());
        Customer customer = custOpt.get();
        assertEquals("Tim", customer.firstName());
        assertEquals("Schulz-Mueller-Meyer", customer.lastName());
    }


    /*
     * Test cases 510: setName(name) with double first names.
     */
    @Test @Order(510)
    void test510_setNameDoubleFirstName() {
        Optional<Customer> custOpt = factory.createCustomer("Nadine Ulla Blumenfeld");
        assertTrue(custOpt.isPresent());
        Customer customer = custOpt.get();       // name style 1
        assertEquals("Nadine Ulla", customer.firstName());
        assertEquals("Blumenfeld", customer.lastName());
    }

    @Test @Order(511)
    void test511_setNameDoubleFirstName() {
        Optional<Customer> custOpt = factory.createCustomer("Blumenfeld, Nadine Ulla");
        assertTrue(custOpt.isPresent());
        Customer customer = custOpt.get();       // name style 2
        assertEquals("Nadine Ulla", customer.firstName());
        assertEquals("Blumenfeld", customer.lastName());
    }

    @Test @Order(512)
    void test512_setNameDoubleFirstName() {
        Optional<Customer> custOpt = factory.createCustomer("Blumenfeld; Nadine Ulla");      // name style 3
        assertTrue(custOpt.isPresent());
        Customer customer = custOpt.get(); 
        assertEquals("Nadine Ulla", customer.firstName());
        assertEquals("Blumenfeld", customer.lastName());
    }


    /*
     * Test cases 520: setName(name) with multiple first names.
     */
    @Test @Order(520)
    void test520_setNameMultipartFirstNames() {
        Optional<Customer> custOpt = factory.createCustomer("Nadine Ulla Maxine Blumenfeld");    // name style 1
        assertTrue(custOpt.isPresent());
        Customer customer = custOpt.get(); 
        assertEquals("Nadine Ulla Maxine", customer.firstName());
        assertEquals("Blumenfeld", customer.lastName());
    }

    @Test @Order(521)
    void test521_setNameMultipartFirstNames() {
        Optional<Customer> custOpt = factory.createCustomer("Blumenfeld, Nadine Ulla Maxine");   // name style 2
        assertTrue(custOpt.isPresent());
        Customer customer = custOpt.get();
        assertEquals("Nadine Ulla Maxine", customer.firstName());
        assertEquals("Blumenfeld", customer.lastName());
    }

    @Test @Order(522)
    void test522_setNameMultipartFirstNames() {
        Optional<Customer> custOpt = factory.createCustomer("Blumenfeld; Nadine Ulla Maxine");   // name style 3
        assertTrue(custOpt.isPresent());
        Customer customer = custOpt.get();
        assertEquals("Nadine Ulla Maxine", customer.firstName());
        assertEquals("Blumenfeld", customer.lastName());
    }


    /*
     * Test cases 530: setName(name) with multiple first and last names.
     */
    @Test @Order(530)
    void test530_setNameMultipartFirstNames() {
        Optional<Customer> custOpt = factory.createCustomer("Khaled Mohamed Arif Saad-Abdelalim");   // name style 1
        assertTrue(custOpt.isPresent());
        Customer customer = custOpt.get();
        assertEquals("Khaled Mohamed Arif", customer.firstName());
        assertEquals("Saad-Abdelalim", customer.lastName());
    }

    @Test @Order(531)
    void test531_setNameMultipartNames() {
        Optional<Customer> custOpt = factory.createCustomer("Saad-Abdelalim, Khaled Mohamed-Arif");  // name style 2
        assertTrue(custOpt.isPresent());
        Customer customer = custOpt.get();
        assertEquals("Khaled Mohamed-Arif", customer.firstName());
        assertEquals("Saad-Abdelalim", customer.lastName());
    }


    /*
     * Test cases 550: setName(name) with multiple, multi-dash first and last names.
     */
    @Test @Order(550)
    void test550_setNameMultiDashMultipartFirstNames() {
        Optional<Customer> custOpt = factory.createCustomer("Khaled-Mohamed Arif Saad-Abdelalim"); 
        assertTrue(custOpt.isPresent());
        Customer customer = custOpt.get();  
        assertEquals("Khaled-Mohamed Arif", customer.firstName());
        assertEquals("Saad-Abdelalim", customer.lastName());
    }

    @Test @Order(551)
    void test551_setNameMultiDashMultipartFirstNames() {
        Optional<Customer> custOpt = factory.createCustomer("Khaled-Mohamed-Arif Saad-Abdelalim");   // name style 1
        assertTrue(custOpt.isPresent());
        Customer customer = custOpt.get(); 
        assertEquals("Khaled-Mohamed-Arif", customer.firstName());
        assertEquals("Saad-Abdelalim", customer.lastName());
    }

    @Test @Order(552)
    void test552_setNameMultipartNames() {
        Optional<Customer> custOpt = factory.createCustomer("Khaled Mohamed-Arif Saad-Abdelalim");   // name style 1
        assertTrue(custOpt.isPresent());
        Customer customer = custOpt.get(); 
         assertEquals("Khaled Mohamed-Arif", customer.firstName());
        assertEquals("Saad-Abdelalim", customer.lastName());
    }

    @Test @Order(553)
    void test553_setNameMultiDashMultipartFirstNames() {
        Optional<Customer> custOpt = factory.createCustomer("Khaled-Mohamed-Arif-Saad-Abdelalim");   // name style 1
        assertTrue(custOpt.isPresent());
        Customer customer = custOpt.get();
        assertEquals("", customer.firstName());
        assertEquals("Khaled-Mohamed-Arif-Saad-Abdelalim", customer.lastName());
    }

    @Test @Order(544)
    void test544_setNameMultiDashMultipartFirstNames() {
        Optional<Customer> custOpt = factory.createCustomer("Khaled Mohamed Arif Saad Abdelalim");   // name style 1
        assertTrue(custOpt.isPresent());
        Customer customer = custOpt.get();
        assertEquals("Khaled Mohamed Arif Saad", customer.firstName());
        assertEquals("Abdelalim", customer.lastName());
    }


    /*
     * Test cases 550: setName(name) with extreme long names.
     */
    @Test @Order(550)
    void test550_setNameExtremeLongNames() {
        Optional<Customer> custOpt = factory.createCustomer("Auguste Viktoria Friederike Luise Feodora Jenny ");
        assertTrue(custOpt.isPresent());
        Customer customer = custOpt.get();
        assertEquals("Auguste Viktoria Friederike Luise Feodora", customer.firstName());
        assertEquals("Jenny", customer.lastName());
        //
    }
}