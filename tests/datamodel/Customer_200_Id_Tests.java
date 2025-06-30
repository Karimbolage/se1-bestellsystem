package datamodel;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

/**
 * Tests for {@link Customer} class: [200..299] Id-tests with tested
 * methods:
 * <pre>
 * - id() (getter, immutable)
 * </pre>
 * @author <code style=color:blue>{@value application.package_info#Author}</code>
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class Customer_200_Id_Tests {

    // Nutze die Singleton Factory
    private final DataFactory factory = DataFactory.getInstance();

    /*
     * Regular test case 200: id() liefert eine ID ungleich -1 zurück (da sie von der Factory gesetzt wird).
     */
    @Test @Order(200)
    void test200_idIsAssignedByFactory() {
        Optional<Customer> kundeOpt = factory.createCustomer("Eric", "Meyer");
        assertTrue(kundeOpt.isPresent());
        Customer kunde = kundeOpt.get();
        // Da IDs automatisch von der Factory gesetzt werden und >= 0 sind
        assertTrue(kunde.id() >= 0);
    }

    /*
     * Regular test case 201: IDs sind eindeutig (zumindest in Testdaten).
     */
    @Test @Order(201)
    void test201_idIsUnique() {
        Optional<Customer> k1 = factory.createCustomer("Anne", "Bayer");
        Optional<Customer> k2 = factory.createCustomer("Tim", "Schulz-Mueller");
        assertTrue(k1.isPresent() && k2.isPresent());
        assertTrue(k1.get().id() != k2.get().id());
    }

    /*
     * Corner test: Keine negative ID möglich, Factory vergibt nur gültige IDs.
     */
    @Test @Order(210)
    void test210_noNegativeId() {
        Optional<Customer> kundeOpt = factory.createCustomer("Nadine", "Blumenfeld");
        assertTrue(kundeOpt.isPresent());
        assertTrue(kundeOpt.get().id() >= 0);
    }

    /*
     * Exception test: Es ist nicht mehr möglich, eine ungültige ID zu setzen,
     * weil die IDs von der Factory vergeben werden und der Konstruktor geschützt ist.
     * Deshalb entfällt ein direkter Test für setId(-1).
     */
}
