package datamodel;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.stream.Stream;

public final class DataFactory {

    // Hier kommt das "Singleton"-Objekt. (LAZY! Wird erst beim ersten Mal erstellt)
    private static DataFactory instance;

    // Private Konstruktor (kann nur in dieser Klasse aufgerufen werden)
    private DataFactory() { }

    // Statische Methode, um die Instanz zu bekommen
    public static DataFactory getInstance() {
        if (instance == null) {
            instance = new DataFactory();
        }
        return instance;
    }

    private final Validator validator = new Validator();

    private class IdPool<ID> {
        private final List<ID> ids;
        private final BiConsumer<List<ID>, Integer> idExpander;
        private int current = 0;
        IdPool(List<ID> initial, BiConsumer<List<ID>, Integer> expander) {
            this.ids = new ArrayList<>(initial == null ? List.of() : initial);
            this.idExpander = expander;
        }
        ID next() {
            if (current >= ids.size()) {
                idExpander.accept(ids, 25);
            }
            return ids.get(current++);
        }
    }

    private final IdPool<Long> customerIdPool = new IdPool<>(
        List.of(892474L, 643270L, 286516L, 412396L, 456454L, 651286L),
        (idPool, expandBy) ->
            Stream.generate(() -> (long)((Math.random() * (999999 - 100000)) + 100000))
                .filter(id -> !idPool.contains(id))
                .limit(expandBy)
                .forEach(idPool::add));

    /**
    * <i>Factory</i> method to create {@link Customer} object from arguments
    * as mix of name parts and contacts. No object is created from invalid
    * name parts or contacts.
    * <p>
    * Examples:
    * <pre>
    * - createCustomer("Eric", "Meyer", "eric98@yahoo.com", "eric98@yahoo.com", "(030) 3945-642298")
    * - createCustomer("Bayer, Anne", "anne24@yahoo.de", "(030) 3481-23352", "fax: (030)23451356")
    * - createCustomer(" Tim ", " Schulz-Mueller ", "tim2346@gmx.de")
    * - createCustomer("Nadine-Ulla Blumenfeld", "+49 152-92454")
    * - createCustomer("Khaled Saad Mohamed Abdelalim", "+49 1524-12948210")
    * </pre>
    * @param args name parts and contacts to create a {@link Customer} object
    * @return {@link Customer} object with valid name and contacts or empty
    */
    public Optional<Customer> createCustomer(String... args) {
        final StringBuilder flatName = new StringBuilder();
        final List<String> contacts = Arrays.stream(args)
            .map(arg -> {
                var contact = validator.validateContact(arg);
                if (contact.isEmpty()) {
                    flatName.append(arg).append(" ");
                }
                return contact;
            })
            .flatMap(Optional::stream)
            .toList();
        return validator.validateName(flatName.toString())
            .map(np -> new Customer(customerIdPool.next(), np.lastName(), np.firstName(), contacts));
    }
}
