package datamodel;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;


/**
 * Tests for {@link Customer} class: [200..299] Id-tests with tested
 * methods:
 * <pre>
 * - getId()
 * - setId(long id)
 * </pre>
 * @author <code style=color:blue>{@value application.package_info#Author}</code>
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class Customer_200_Id_Tests {
    /*
     * Regular test case 200: getId() nach Konstruktion gibt -1 zurück (unassigned).
     */
    @Test @Order(200)
    void test200_getIdNachKonstruktion() {
        Customer kunde = new Customer();
        assertEquals(-1, kunde.getId());
    }

    /*
     * Regular test case 201: getId() nach erstem setId(x) ist x.
     */
    @Test @Order(201)
    void test201_getIdNachSetzen() {
        Customer kunde = new Customer();
        kunde.setId(30);
        assertEquals(30, kunde.getId());
    }

    /*
     * Regular test case 202: getId() nach zweitem setId(y) bleibt beim ersten Wert.
     */
    @Test @Order(202)
    void test202_getIdBleibtBeimErstenWert() {
        Customer kunde = new Customer();
        kunde.setId(30);
        kunde.setId(100);
        assertEquals(30, kunde.getId());
    }

    /*
     * Corner test case 210: setId() mit minimal erlaubtem Wert (0) und Wert +1.
     */
    @Test @Order(210)
    void test210_setIdMinUndMinPlusEins() {
        Customer kunde = new Customer();
        kunde.setId(0L);
        assertEquals(0L, kunde.getId());

        Customer kunde2 = new Customer();
        kunde2.setId(1L);
        assertEquals(1L, kunde2.getId());
    }

    /*
     * Corner test case 211: setId() mit maximalem Wert (Long.MAX_VALUE) und Wert -1.
     */
    @Test @Order(211)
    void test211_setIdMaxUndMaxMinusEins() {
        Customer kunde = new Customer();
        kunde.setId(Long.MAX_VALUE);
        assertEquals(Long.MAX_VALUE, kunde.getId());

        Customer kunde2 = new Customer();
        kunde2.setId(Long.MAX_VALUE - 1);
        assertEquals(Long.MAX_VALUE - 1, kunde2.getId());
    }

        /*
     * Corner test case 212: setId() mit Wert 0.
     */
    @Test @Order(212)
    void test212_setIdMitNull() {
        Customer kunde = new Customer();
        kunde.setId(0);
        assertEquals(0, kunde.getId());
    }
    
    /*
     * Exception test case 220: setId(-1) wirft IllegalArgumentException mit richtiger Nachricht.
     */
    @Test @Order(220)
    void test220_setIdMitNegativemWert() {
        Customer kunde = new Customer();
        IllegalArgumentException thrown = assertThrows(
            IllegalArgumentException.class,
            () -> kunde.setId(-1L)
        );
        assertEquals("invalid id (negative)", thrown.getMessage());
    }

    /*
     * Exception test case 221: setId(Long.MIN_VALUE) wirft IllegalArgumentException mit richtiger Nachricht.
     */
    @Test @Order(221)
    void test221_setIdMitLongMinValue() {
        Customer kunde = new Customer();
        IllegalArgumentException thrown = assertThrows(
            IllegalArgumentException.class,
            () -> kunde.setId(Long.MIN_VALUE)
        );
        assertEquals("invalid id (negative)", thrown.getMessage());
    }

}
