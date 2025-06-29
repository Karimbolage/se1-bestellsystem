<<<<<<< HEAD
# C2: *Customer.java*

Steps:

1. [Branch Assembly](#1-branch-assembly)

1. [Supplement Class *Customer.java* with *Javadoc*](#2-supplement-class-customerjava-with-javadoc)

1. [Implement Class *Customer.java*](#3-implement-class-customerjava)

1. [Push Commits to your Remote Repository](#4-push-commits-to-your-remote-repository)
=======
# D1: Refactoring: *DataFactory*

[*"Refactoring"*](https://refactoring.guru/refactoring) is the process of
improving the code structure or capabilities without adding or changing
functionality.

In the [*"se1-bestellsystem"*](https://github.com/sgra64/se1-bestellsystem)
project, a simple class
[*Customer*](https://github.com/sgra64/se1-bestellsystem/blob/c12-customer/src/datamodel/Customer.java)
was created.

It has several shortcomings:

- Creation of objects of class *Customer* is possible everywhere, any number
    of *Customer* objects can be created without registration.

- Objects of class *Customer* can be created from almost arbitrary arguments,
    e.g. the uniqueness of *id* attributes is not gueranteed. Duplicates
    can be created.

- Class *Customer* includes *"program logic"* such as splitting single-String
    names, e.g. *"Meyer, Eric"* into first *"Eric"* and last *"Meyer"* name
    attributes.

While the first and second points are more of the nature of consistency
(duplicate id, duplicate objects), the third point refers to a structural
problem.

Goals of the *Refactoring* are:

1. Classes of the *datamodel* (*Customer, Article, Order*) should contain
    only data model aspects and not program logic.

1. More stringent validation should be introduced such that:

    - *Customer* objects are only created with valid names and contacts
        validated as email addresses or phone numbers.

1. Duplicates (multiple *Customer* objects with same *id*, but different
    name or contact information) should be avoided.

1. Improving consistentcy by turning *datamodel* classes into *"immutable"*
    classes, which are classes that have no setter methods, e.g. no
    `setId()` methods.

1. Introduction of the *"fluent"* naming scheme introduced by
    [*lombok Accessors*](https://projectlombok.org/features/experimental/Accessors)
    with removing the `get-` and `set-` prefixes of method names, e.g.
    `id()` rather than `getId()`.


Two new classes are introduced to centralize object creation and argument
validation:

- class *"DataFactory"* (according to the
    [*Factory*](https://refactoring.guru/design-patterns/factory-method)
    [*Software Design pattern*](https://refactoring.guru/design-patterns))
    centralizes the creation of *datamodel* objects of classes:
    *Customer, Article, Order*.

- class *"Validator"* centralizes the logic to validate input (*names*,
    *contacts*). It is used by class *DataFactory* to create objects only
    from validated input.

- Arbitrary creation of *datamodel* objects anywhere in the code with `new`
    is prevented by reducing the visibility of constructors to `protected`
    (constructors can be invoked only by classes of the *"datamodel"* package).

The UML Class Diagram shows the new classes created in effect of the refactoring
in the *datamodel* package:

- class `DataFactory` with factory methods, e.g. *createCustomer(...)*, to
    create objects such as of class *Customer* only from validated arguments.
    Class *DataFactory* also has an inner class `IdPool<ID>` to assign *id*
    from a central pool of unique *id*.

- class `Validator` is used to validate names and contact information (email
    or phone numbers) and

- class `Customer` has setter methods removed (*"immutable class"*) and
    uses the *"fluent"* naming scheme.

<img src="https://raw.githubusercontent.com/sgra64/se1-bestellsystem/refs/heads/markup/d12-datamodel/d1-DataFactory.png" alt="drawing" width="800"/>



Steps:

1. [Create a Singleton Class: *DataFactory*](#1-create-a-singleton-class-datafactory)

1. [Understand Inner Class *IdPool*](#2-understand-inner-class-idpool)

1. [Immutalize Class *Customer*](#3-immutalize-class-customer)

1. [Run Driver Code](#4-run-driver-code)

1. [Commit and Push Changes](#6-commit-and-push-changes)

<!-- 1. [Update JUnit-Tests](#5-update-junit-tests) -->
>>>>>>> se1-remote/d1-datafactory



&nbsp;

<<<<<<< HEAD
## 1. Branch Assembly

Tag the latest commit on branch `main` with `"base"`.

Create a new branch: `c12-customer` to implement the class: *Customer.java*.

Set a new remote with name: `se1-remote` pointing to the remote repository
from where the branch will be pulled:

```sh
git remote add se1-remote https://github.com/sgra64/se1-bestellsystem.git

git remote -v                   # show all remotes
```

Output will show two remotes: *"origin"* pointing to your remote repository
and the new remote: *"se1-remote"* :

```
origin  git@gitlab.bht-berlin.de:<your-name>/se1-bestellsystem.git (fetch)
origin  git@gitlab.bht-berlin.de:<your-name>/se1-bestellsystem.git (push)
se1-remote      https://github.com/sgra64/se1-bestellsystem.git (fetch)
se1-remote      https://github.com/sgra64/se1-bestellsystem.git (push)
```

Pull remote branch `se1-remote/c12-customer` into the new local
branch `c12-customer`:

```sh
# pull the remote branch (pull: fetch + merge), --strategy-option theirs
git pull se1-remote c12-customer \
    --squash --allow-unrelated-histories
```

Commit the changes that arrived with the pull:

```sh
git commit -m "pull se1-remote c12-customer"
```

A new class [*Application_C12.java*](src/application/Application_C12.java)
has been added to package `application`, which creates and prints some
*Customer* objects:

```java
/**
 * Run application code on an instance rather than in {@code static main();}
 */
@Override
public void run(String[] args) {
    // 
    final Customer eric = new Customer("Eric Meyer")
        .setId(892474L)     // set id, first time
        .setId(947L)        // ignored, since id can only be set once
        .addContact("eric98@yahoo.com")
        .addContact("eric98@yahoo.com") // ignore duplicate contact
        .addContact("(030) 3945-642298");

    final Customer anne = new Customer("Bayer, Anne")
        .setId(643270L)
        .addContact("anne24@yahoo.de")
        .addContact("(030) 3481-23352")
        .addContact("fax: (030)23451356");

    final Customer tim = new Customer("Tim Schulz-Mueller")
        .setId(286516L)
        .addContact("tim2346@gmx.de");

    final Customer nadine = new Customer("Nadine-Ulla Blumenfeld")
        .setId(412396L)
        .addContact("+49 152-92454");

    final Customer khaled = new Customer()
        .setName("Khaled Saad Mohamed Abdelalim")
        .setId(456454L)
        .addContact("+49 1524-12948210");

    final TableFormatter tf = new TableFormatter("|%-6s", "| %-32s", "| %-32s |")
        .line()
        .row("ID", "NAME", "CONTACTS")  // table header
        .line();

    final List<Customer> customers = List.of(eric, anne, tim, nadine, khaled);
}
```

Compile and run the code:

```sh
mk compile run                      # compile and run the code
```

Output shows an empty table since class *Customer* is still incomplete:

```
Hello, se1.bestellsystem (Application_C12, modular)
+------+---------------------------------+----------------------------------+
|ID    | NAME                            | CONTACTS                         |
+------+---------------------------------+----------------------------------+
|0     | ,                               |                                  |
|0     | ,                               |                                  |
|0     | ,                               |                                  |
|0     | ,                               |                                  |
|0     | ,                               |                                  |
+------+---------------------------------+----------------------------------+
```
=======
## 1. Create a Singleton Class: *DataFactory*

Fetch the code drop
[*d1-datafactory*](https://github.com/sgra64/se1-bestellsystem/tree/d1-datafactory)
(fetch the branch and merge, or pull the branch).

Incoming files:

```
M src/application/Application.java
A src/application/Application_D1.java
M src/application/package-info.java
M src/datamodel/Customer.java
A src/datamodel/Validator.java
```

Create a new class `DataFactory` in package `datamodel` that implements the
[*Singleton Pattern*](https://refactoring.guru/design-patterns/singleton)
(chose whether *lazy* or *strict*).

Supplement class `DataFactory` with code and *javadoc* from the
[*gist*](https://gist.github.com/sgra64/4a926af94b1bc231a84f8baf84cf8a3e).


Understand method `Optional<Customer> createCustomer(String... args)`:

```java
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
    /*
     * separate contacts from names in args
     */
    final StringBuilder flatName = new StringBuilder();
    final List<String> contacts = Arrays.stream(args)
        .map(arg -> {
            var contact = validator.validateContact(arg);
            if(contact.isEmpty()) {
                flatName.append(arg).append(" ");
            }
            return contact;
        })
        .flatMap(Optional::stream)
        .toList();
    // 
    return validator.validateName(flatName.toString())
        .map(np -> new Customer(customerIdPool.next(), np.lastName(), np.firstName(), contacts));
}
```

Answer questions:

1. What is the input of the method (see examples in *javadoc*)?

1. What is the result of the method?

1. Where is the actual *Customer* object created?

1. What does `.flatMap(Optional::stream)` do?

1. Why can't *Customer* objects be created outside package *datamodel*?
>>>>>>> se1-remote/d1-datafactory



&nbsp;

<<<<<<< HEAD
## 2. Supplement Class *Customer.java* with *Javadoc*

Create class `Customer.java` in package `datamodel` from the *UML Class Diagram:* 

<img src="https://raw.githubusercontent.com/sgra64/se1-bestellsystem/refs/heads/markup/main/Class-customer.png" alt="drawing" width="400"/>

Follow steps:

1. Create an empty class `Customer.java` in package `datamodel`.

1. Add *attributes*, *constructors* and *methods* as specified.

1. Add [*Javadoc*](https://en.wikipedia.org/wiki/Javadoc) from the documentation
    of class [*Customer.java*](https://sgra64.github.io/se1-bestellsystem/c12-customer)
    to understand methods and their expected behavior.

Understand particularly the purpose of method
[*splitName(String name)*](https://sgra64.github.io/se1-bestellsystem/c12-customer/se1.bestellsystem/datamodel/Customer.html#splitName(java.lang.String))
that splits a single-String name, e.g. "Meyer, Eric" into its first and last name parts.

Rebuild *Javadoc* locally:

```sh
mk javadoc                      # build Javadoc for the project
```
```
javadoc -d docs $(eval echo $JDK_JAVADOC_OPTIONS) \
  $(builtin cd src; find . -type d | sed -e 's!/!.!g' -e 's/^[.]*//')
Loading source files for package application...
Loading source files for package datamodel...
Constructing Javadoc information...
Creating destination directory: "docs\"
Building index for all the packages and classes...
Standard Doclet version 21+35-LTS-2513
Building tree for all the packages and classes...
Generating docs\se1.bestellsystem\application\Application.html...
Generating docs\se1.bestellsystem\application\Application_C12.html...
Generating docs\se1.bestellsystem\datamodel\Customer.html...
Generating docs\se1.bestellsystem\application\Runner.html...
Generating docs\se1.bestellsystem\application\package-summary.html...
Generating docs\se1.bestellsystem\application\package-tree.html...
Generating docs\se1.bestellsystem\datamodel\package-summary.html...
Generating docs\se1.bestellsystem\datamodel\package-tree.html...
Generating docs\se1.bestellsystem\module-summary.html...
Generating docs\overview-tree.html...
Building index for all classes...
Generating docs\allclasses-index.html...
Generating docs\allpackages-index.html...
Generating docs\index-all.html...
Generating docs\search.html...
Generating docs\index.html...
Generating docs\help-doc.html...
```

Fix errors and warnings when *Javadoc* compiles.

Open file: `docs/index.html` in a browser, navigate to class *Customer.java* and
compare with the *Javadoc* in
[*Customer.java*](https://sgra64.github.io/se1-bestellsystem/c12-customer/se1.bestellsystem/datamodel/Customer.html)

Both should be identical, except for the `Autor:` tag, which should show your name.
=======
## 2. Understand Inner Class *IdPool*

Read the following code and understand class `IdPool<ID>` and the use with
variable `customerIdPool` below:

```java
class IdPool<ID> {
    private final List<ID> ids;
    private final BiConsumer<List<ID>, Integer> idExpander;
    private int current = 0;

    /**
     * Constructor.
     * @param initial initial set of <i>id</i> stored in pool
     * @param expander call-out to expand <i>id</i> pool by given size
     */
    IdPool(List<ID> initial, BiConsumer<List<ID>, Integer> expander) {
        this.ids = new ArrayList<>(initial==null? List.of() : initial);
        this.idExpander = expander;
    }

    /**
     * Retrieve next <i>id</i> from pool. Expand, if exhausted.
     * @return next <i>id</i> from pool
     */
    ID next() {
        if(current >= ids.size()) {
            idExpander.accept(ids, 25);
        }
        return ids.get(current++);
    }
}

private final IdPool<Long> customerIdPool = new IdPool<>(
    List.of( // initial pool <i>id</i>
        892474L, 643270L, 286516L, 412396L, 456454L, 651286L
    ),
    (idPool, expandBy) -> // pool expander
        Stream.generate(() -> (long)((Math.random() * (999999 - 100000)) + 100000))
            .filter(id -> ! idPool.contains(id))
            .limit(expandBy)
            .forEach(idPool::add));
```

Answer questions:

1. What is *<ID>* ?

1. What does method *next()* return?

1. Where are *ids* stored?

1. What happens when the initial set of *id* is used-up?
    Point at the code (line) where this is detected.
    How many new *ids* are supplied into the pool?

1. What is the second argument of the *IdPool* constructor:
    `BiConsumer<List<ID>, Integer> expander`?

    - Where is this construct invoked?

    - What does it accept as arguments?

    - What does it produce as result?
>>>>>>> se1-remote/d1-datafactory



&nbsp;

<<<<<<< HEAD
## 3. Implement Class *Customer.java*

Implement methods according to the *Javadoc* specifications.
Pay attention return values and behavior for illegal arguments.

After implementation, re-compile and run the code. The table now shows the
created *Customer* objects:

```sh
mk compile run                      # compile and run the code
```
```
Hello, se1.bestellsystem (Application_C12, modular)
+------+---------------------------------+----------------------------------+
|ID    | NAME                            | CONTACTS                         |
+------+---------------------------------+----------------------------------+
|892474| Meyer, Eric                     | eric98@yahoo.com, (+1 contacts)  |
|643270| Bayer, Anne                     | anne24@yahoo.de, (+2 contacts)   |
|286516| Schulz-Mueller, Tim             | tim2346@gmx.de                   |
|412396| Blumenfeld, Nadine-Ulla         | +49 152-92454                    |
|456454| Abdelalim, Khaled Saad Mohamed  | +49 1524-12948210                |
+------+---------------------------------+----------------------------------+
```

Commit changes under `c12: Customer.java`.

Show commit log:

```sh
git log --oneline                   # show commit log
```
```
340e59f (HEAD -> main) c12: Customer.java       <-- latest commit
b1e8439 pull se1-remote c12-customer
aad5b4a add .gitmodules file and modules .env, .vscode, libs
44cdf9f add README.md
c43f7e1 add src, tests, resources
638c570 add .gitmodules, .gitmodules.sh
d8f0873 add .gitignore
4375dd3 (tag: root) root commit (empty)
```
=======
## 3. Immutalize Class *Customer*

<!-- An *immutable* class *Customer* does not allow changes to attributes.
*DataFactory* is the only class that can create new *Customer*
objects from validated parameters.

Making class *Customer* immutable means:
1. Make all attributes: `private` and `final`.
1. Remove *setter()* methods.
1. Remove all constructors, except one used by *DataFactory* with
    visibility: `protected` to prevent creation of *Customer*
    objects from other packages.
The remaining class has no *setter()* methods and hence is called *immutable*,
which is shown in the UML class diagram by stereotype: `<<immutable>>`.
<img src="Customer.png" alt="drawing" width="400"/>
-->

Study the immutalized class *Customer.class* that came with the code drop
and compare with the prior (none-immutalized) class.

Name three differences?
>>>>>>> se1-remote/d1-datafactory



&nbsp;

<<<<<<< HEAD
## 4. Push Commits to your Remote Repository

When tests are passing and Javadoc has been sucessfully created:

1. commit changes on your local branch `c12-customer`.

1. Push the branch to your remote repository.

1. Merge branch `c12-customer` to your local `main` branch.

1. Push branch `main` also to your remote repository.
=======
## 4. Run Driver Code

The new driver code came with the code drop *Application_D1.java*.

Customer objects can no longer be directly created by `new Customer(...);`.
Instead, the *DataFactory* is used:


```java
/**
 * Class that implements the {@link Runner} interface with {@code run(String[] args)}
 * method that executes tasks of the <i>D1</i> assignment.
 *
 * @version <code style=color:green>{@value application.package_info#Version}</code>
 * @author <code style=color:blue>{@value application.package_info#Author}</code>
 */
public class Application_D1 implements Runner {

    /**
     * Run code of the <i>C12</i> assignment.
     */
    @Override
    public void run(String[] args) {

        DataFactory df = DataFactory.getInstance();

        List<Customer> customers = List.of(
            df.createCustomer("Eric", "Meyer", "eric98@yahoo.com", "eric98@yahoo.com",
                "(030) 3945-642298"),
            df.createCustomer("Bayer, Anne", "anne24@yahoo.de", "(030) 3481-23352",
                "fax: (030)23451356"),
            df.createCustomer(" Tim ", " Schulz-Mueller ", "tim2346@gmx.de"),
            df.createCustomer("Nadine-Ulla Blumenfeld", "+49 152-92454"),
            df.createCustomer("Khaled Saad Mohamed Abdelalim", "+49 1524-12948210")
            // 
            // attempts to create Customer objects from invalid email address, no object is created
            df.createCustomer("Mandy Mondschein", "locomandy<>gmx.de"),
            df.createCustomer("", "nobody@gmx.de") // invalid name, no object is created
        // 
        ).stream()
            .flatMap(Optional::stream)
            .toList();

        // print numbers of created objects in respective collections
        System.out.println(String.format(
            "(%d) Customer objects built.\n" +
            "(%d) Article objects built.\n" +
            "(%d) Order objects built.\n---",
            customers.spliterator().getExactSizeIfKnown(), 0, 0));

        // print Customer table
        StringBuilder sb = printCustomers(customers);
        System.out.println(sb.insert(0, "Kunden:\n").toString());
    }
}
```

Running the code shows that invalid arguments (invalid email:
`"locomandy<>gmx.de"` and empty name `""`) do not create *Customer* objects
that are hence missing in the customer table:

```
Hello, se1.bestellsystem (Application_D1, modular)
(5) Customer objects built.
(0) Article objects built.
(0) Order objects built.
---
Kunden:
+----------+---------------------------------+---------------------------------+
| Kund.-ID | Name                            | Kontakt                         |
+----------+---------------------------------+---------------------------------+
|   892474 | Meyer, Eric                     | eric98@yahoo.com, (+2 contacts) |
|   643270 | Bayer, Anne                     | anne24@yahoo.de, (+2 contacts)  |
|   286516 | Schulz-Mueller, Tim             | tim2346@gmx.de                  |
|   412396 | Blumenfeld, Nadine-Ulla         | +49 152-92454                   |
|   456454 | Abdelalim, Khaled Saad Mohamed  | +49 1524-12948210               |
+----------+---------------------------------+---------------------------------+
```

Correct arguments and print the full customer list:

```
Hello, se1.bestellsystem (Application_D1, modular)
(7) Customer objects built.     <-- (7) Customers
(0) Article objects built.
(0) Order objects built.
---
Kunden:
+----------+---------------------------------+---------------------------------+
| Kund.-ID | Name                            | Kontakt                         |
+----------+---------------------------------+---------------------------------+
|   892474 | Meyer, Eric                     | eric98@yahoo.com, (+2 contacts) |
|   643270 | Bayer, Anne                     | anne24@yahoo.de, (+2 contacts)  |
|   286516 | Schulz-Mueller, Tim             | tim2346@gmx.de                  |
|   412396 | Blumenfeld, Nadine-Ulla         | +49 152-92454                   |
|   456454 | Abdelalim, Khaled Saad Mohamed  | +49 1524-12948210               |
|   651286 | Mondschein, Mandy               | locomandy@gmx.de                | <-- added
|   815160 | Meissner, Susanne               | nobody@gmx.de                   | <-- added
+----------+---------------------------------+---------------------------------+
```


<!-- 
&nbsp;

## 5. Update JUnit-Tests

*Customer* JUnit Tests no longer compile with the *DataFactory* changes.

Replace tests in: `tests/datamodel`. Download file:
[*c4-datafactory-tests.tar*](https://github.com/sgra64/se1-bestellsystem/blob/markup/c4-datafactory)
to the project directory and replace tests:

```sh
rm -rf tests/datamodel              # remove old tests
tar xvf c4-datafactory-tests.tar    # install new tests
```

Tests compile and run with the new code:

```sh
mk compile compile-tests run-tests
```
```
Test run finished after 486 ms
[        62 tests successful      ]
[         0 tests failed          ]
``` -->


&nbsp;

## 5. Commit and Push Changes

With all tests passing, commit and push changes to your remote repository.

```sh
git commit -m "c4: DataFactory, immutable Customer class, tests update"
git push                        # push new commit to your upstream remote repository
```
>>>>>>> se1-remote/d1-datafactory
