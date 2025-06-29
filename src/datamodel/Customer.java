package datamodel;

<<<<<<< HEAD
import java.util.Collections;

/**
 * Entity class representing a Customer as a person who creates and holds (owns) orders in the system.
 * @version <code style=color:green>{@value application.package_info#Version}</code>
 * @author <code style=color:blue>{@value application.package_info#Author}</code>
 */
public class Customer {
        /**
     * Eindeutige Kunden-ID, Standardwert -1 für nicht gesetzt.
     */
    private long id = -1;

    /**
     * Nachname des Kunden, initial leer.
     */
    private String lastName = "";

    /**
     * Vorname des Kunden, initial leer.
     */
    private String firstName = "";

    /**
     * Liste der Kontakte (z.B. E-Mail, Telefonnummer) des Kunden.
     */
    private final java.util.List<String> contacts = new java.util.ArrayList<>();
    /**
     * Erstellt einen neuen Kunden mit leerem Namen und keiner ID
     */
    public Customer() { }

    /**
     * Erstellt einen neuen Kunden mit dem gegebenen Namen.
     * @param name Vollständiger Name des Kunden
     */
    public Customer(String name) {
        setName(name);
     }

    /**
    * Gibt die ID des Kunden zurück.
    * @return Kunden-ID, -1 bei keiner ID.
    */
    public long getId() {
        return id;
    }
    /**
     * Setzt die ID des Kunden. Kann nur einmalig gesetzt werden.
     * Bei erneutem Setzen bleibt die ID unverändert.
     * @param id Die zu setzende Kunden-ID
     * @return Referenz auf das Customer-Objekt (this)
     */
    public Customer setId(long id) {
        if (id < 0) throw new IllegalArgumentException("invalid id (negative)");
        if (this.id == -1) {
            this.id = id;
        }
        // ignoriert weiteren setId-Aufruf (wie Vorgabe)
        return this;
    }
    /**
     * Gibt den Nachnamen des Kunden zurück.
     * @return Nachname des Kunden als String
     */
    public String getLastName() {
        return lastName;
    }
    /**
     * Gibt den Vornamen des Kunden zurück.
     * @return Vorname des Kunden als String
     */
    public String getFirstName() {
        return firstName;
    }
    /**
     * Setzt den Vor- und Nachnamen des Kunden.
     * @param first Vorname des Kunden
     * @param last Nachname des Kunden
     * @return Referenz auf das Customer-Objekt (this)
     */
    public Customer setName(String first, String last) {
        if (first == null || last == null || last.isEmpty())
            throw new IllegalArgumentException("last name empty");
        this.firstName = first;
        this.lastName = last;
        return this;
    }
    /**
     * Setzt den vollständigen Namen des Kunden. 
     * Der Name wird nach Regeln aufgeteilt (siehe splitName).
     * @param name Vollständiger Name
     * @return Referenz auf das Customer-Objekt (this)
     */
    public Customer setName(String name) {
        splitName(name);
        return this;
    }
    /**
     * Gibt die Anzahl der Kontakte zurück, die diesem Kunden zugeordnet sind.
     * @return Anzahl der Kontakte als int
=======
import java.util.*;
import java.util.function.BiConsumer;


/**
 * Entity class representing a {@link Customer} as a person who creates
 * and holds (owns) orders in the system.
 * 
 * @version <code style=color:green>{@value application.package_info#Version}</code>
 * @author <code style=color:blue>{@value application.package_info#Author}</code>
 */
public final class Customer {

    /**
     * Immutable Customer id attribute set once when the object is created.
     */
    private final long id;

    /**
     * Customer's surname attribute set when the object is created. Can be
     * updated by the {@link Validator} {@code updateName()} method.
     */
    private String lastName;

    /**
     * Customer's oone-surname name parts set when the object is created. Can
     * be updated by the {@link Validator} {@code updateName()} method.
     */
    private String firstName;

    /**
     * Customer contact information with multiple entries of email addresses
     * and/or phone numbers. Contacts can be update by {@link Validator}
     * {@code addContact()} and {@code removeContact()} methods.
     */
    protected final List<String> contacts;

    /**
     * None-public accessor to update first- and lastName attributes by the
     * {@link Validator} {@code updateName()} method.
     */
    protected final BiConsumer<String, String> nameUpdater =
        (first, last) -> { firstName=first; lastName=last; };

    /**
     * Non-public constructor used by {@link DataFactory} that also provides
     * the <i>id</i> attribute. {@link Customer} objects cannot be created
     * outside this package.
     */
    protected Customer(long id, String lastName, String firstName, List<String> contacts) {
        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
        this.contacts = contacts==null? new ArrayList<>() : contacts;
    }

    /**
     * Public <i>id</i> getter.
     * @return customer <i>id</i> attribute of {@link Customer} object
     */
    public long id() {
        return id;
    }

    /**
     * Public getter of the <i>lastName</i> attribute that always holds
     * a valid last name (never null, not empty).
     * @return value of lastName attribute, never null, not empty
     */
    public String lastName() {
        return lastName;
    }

    /**
     * Public getter of the <i>firstName</i> attribute that always holds
     * a valid first name (never null, may be empty if unknown).
     * @return value of firstName attribute, never null, may be empty
     */
    public String firstName() {
        return firstName;
    }

    /**
     * Return the number of contacts.
     * @return number of contacts
>>>>>>> se1-remote/d1-datafactory
     */
    public int contactsCount() {
        return contacts.size();
    }
<<<<<<< HEAD
    /**
     * Gibt alle Kontakte (z.B. Email, Telefonnummer) dieses Kunden zurück.
     * @return Iterable mit allen Kontaktdaten
     */
    public Iterable<String> getContacts() {
        return Collections.unmodifiableList(contacts);
    }
    /**
     * Fügt einen neuen Kontakt (z.B. Email, Telefonnummer) hinzu.
     * Doppelte Kontakte werden ignoriert.
     * @param contact Kontaktinformation als String
     * @return Referenz auf das Customer-Objekt (this)
     */
    public Customer addContact(String contact) {
        String trimmed = trim(contact);
        if (contact == null || trimmed.isEmpty())
            throw new IllegalArgumentException("contact is null or empty");
        if (trimmed.length() < 6)
            throw new IllegalArgumentException("contact less than 6 characters: \"" + contact + "\".");
        if (!contacts.contains(trimmed)) {
            contacts.add(trimmed);
        }
        return this;
    }
    /**
     * Löscht den Kontakt an der angegebenen Position aus der Kontaktliste.
     * @param i Index des zu löschenden Kontakts
     * @throws UnsupportedOperationException falls nicht implementiert
     */
    public void deleteContact(int i) {
        if (i >= 0 && i < contacts.size()) {
            contacts.remove(i);
        }
    }
    /**
     * Löscht alle Kontakte des Kunden.
     * @throws UnsupportedOperationException falls nicht implementiert
     */
    public void deleteAllContacts() {
        contacts.clear();    
    }

    /**
     * Split single-String name into last- and first name parts according to
     * rules:
     * <ul>
     * <li> if a name contains no seperators (comma or semicolon {@code [,;]}),
     *      the trailing consecutive part is the last name, all prior parts
     *      are first name parts, e.g. {@code "Tim Anton Schulz-Müller"}, splits
     *      into <i>first name:</i> {@code "Tim Anton"} and <i>last name</i>
     *      {@code "Schulz-Müller"}.
     * <li> names with seperators (comma or semicolon {@code [,;]}) split into
     *      a last name part before the seperator and a first name part after
     *      the seperator, e.g. {@code "Schulz-Müller, Tim Anton"} splits into
     *      <i>first name:</i> {@code "Tim Anton"} and <i>last name</i>
     *      {@code "Schulz-Müller"}.
     * <li> leading and trailing white spaces {@code [\s]}, commata {@code [,;]}
     *      and quotes {@code ["']} must be trimmed from names, e.g.
     *      {@code "  'Schulz-Müller, Tim Anton'    "}.
     * <li> interim white spaces between name parts must be trimmed, e.g.
     *      {@code "Schulz-Müller, <white-spaces> Tim <white-spaces> Anton <white-spaces> "}.
     * </ul>
     * <pre>
     * Examples:
     * +------------------------------------+-----------------------+-----------------------+
     * |Single-String name                  |first name parts       |last name parts        |
     * +------------------------------------+-----------------------+-----------------------+
     * |"Eric Meyer"                        |"Eric"                 |"Meyer"                |
     * |"Meyer, Anne"                       |"Anne"                 |"Meyer"                |
     * |"Meyer; Anne"                       |"Anne"                 |"Meyer"                |
     * |"Tim Schulz‐Mueller"                |"Tim"                  |"Schulz‐Mueller"       |
     * |"Nadine Ulla Blumenfeld"            |"Nadine Ulla"          |"Blumenfeld"           |
     * |"Nadine‐Ulla Blumenfeld"            |"Nadine‐Ulla"          |"Blumenfeld"           |
     * |"Khaled Saad Mohamed Abdelalim"     |"Khaled Saad Mohamed"  |"Abdelalim"            |
     * +------------------------------------+-----------------------+-----------------------+
     * 
     * Trim leading, trailing and interim white spaces and quotes:
     * +------------------------------------+-----------------------+-----------------------+
     * |" 'Eric Meyer'  "                   |"Eric"                 |"Meyer"                |
     * |"Nadine     Ulla     Blumenfeld"    |"Nadine Ulla"          |"Blumenfeld"           |
     * +------------------------------------+-----------------------+-----------------------+
     * </pre>
     * @param name single-String name to split into first- and last name parts
     * @throws IllegalArgumentException if name argument is null or empty
     */
    public void splitName(String name) {
        if(name == null){
            throw new IllegalArgumentException("name null");
        }

        if (name.trim().isEmpty()){
            throw new IllegalArgumentException("name empty");
        }

        // Entferne führende/trailende Leerzeichen, Kommata, Semikola, Quotes
        String trimmed = trim(name);

        // Komma oder Semikolon: Nachname, Vorname
        String[] split;
        if (trimmed.contains(",") || trimmed.contains(";")) {
            split = trimmed.split("[,;]", 2);
            this.lastName = trim(split[0]);
            this.firstName = trim(split.length > 1 ? split[1] : "");
        } else {
            // Kein Komma/Semikolon: Letztes Wort ist Nachname, Rest ist Vorname
            String[] parts = trimmed.trim().split("\\s+");
            if (parts.length == 1) {
                this.firstName = "";
                this.lastName = parts[0];
            } else {
                this.firstName = String.join(" ", java.util.Arrays.copyOf(parts, parts.length - 1));
                this.lastName = parts[parts.length - 1];
            }
        }
    }

    /**
     * Trim leading and trailing white spaces {@code [\s]}, commata {@code [,;]}
     * and quotes {@code ["']} from a String (used for names and contacts).
     * @param s String to trim
     * @return trimmed String
     */
    private String trim(String s) {
        s = s.replaceAll("^[\\s\"',;]*", "");   // trim leading white spaces[\s], commata[,;] and quotes['"]
        s = s.replaceAll( "[\\s\"',;]*$", "");  // trim trailing accordingly
        return s;
=======

    /**
     * Public <i>contacts</i> getter as immutable {@code Iterable<String>}.
     * @return contacts as immutable {@code Iterable<String>}
     */
    public Iterable<String> contacts() {
        return contacts;
>>>>>>> se1-remote/d1-datafactory
    }
}