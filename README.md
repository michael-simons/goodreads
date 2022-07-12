# Goodreads

`all.csv` contains a more or less unsorted lists of my books. Not necessary complete.
Here's a browseable, interactive list of all books: [Neo4j Aura meets Quarkus](https://neo4j-aura-quarkus-graphql.herokuapp.com).


## Use SQLite to query the database

```
sqlite3 :memory: \
 'CREATE table books(author VARCHAR(256), title VARCHAR(256), type VARCHAR(1), state VARCHAR(1))' \
 '.mode csv' \
 '.separator ;' \
 '.headers off' \
 '.import all.csv books' \
 "SELECT title FROM books WHERE author like '%King%' ORDER by title"
```

## Books

* [97 Things Every Programmer Should Know (EN)](http://www.oreilly.com/pub/pr/2499)
* [Kathrin Passig, Johannes Jander - Weniger schlecht programmieren (DE)](http://www.oreilly.de/book_details.php?masterid=120174)
* [Peter Seibel - Coders At Work (EN)](http://www.apress.com/us/book/9781430219484)
* [Bill Karwin - SQL Antipatterns (EN)](https://pragprog.com/book/bksqla/sql-antipatterns)
* [Eric Evans - Domain-Driven Design, Tackling Complexity in the Heart of Software (EN)](http://dddcommunity.org/book/evans_2003/)
* [Joshua Bloch - Effective Java, 3rd Edition](http://www.informit.com/store/effective-java-9780134685991)
* [Rob Conery - The Imposter's Handbook: Essential CS Skills and Concepts for Self-Taught Programmers](https://bigmachine.io/products/the-imposters-handbook)
* [Michael Nygard - Release It! Second Edition](https://pragprog.com/book/mnee2/release-it-second-edition)
* [Kevin Behr, George Spafford, Gene Kim - The Phoenix Project](https://itrevolution.com/book/the-phoenix-project/)
* [Martin Kleppmann - Designing Data-Intensive Applications](https://dataintensive.net/)
* [Marianne Bellotti - Kill It With Fire](https://nostarch.com/kill-it-fire)

## Problemsolver

I'd call those books "Problemsolver". You might not read them from front to back but as a reference for specific problems. 

* [Thorben Jansen - Hibernate Tips, More than 70 solutions to common Hibernate problems](https://www.thoughts-on-java.org/hibernate-tips-book/)
* [Simon Harrer, Jörg Lenhard, Linus Dietz - Java by Comparison](https://pragprog.com/book/javacomp/java-by-comparison)

## Non-IT

These are non IT specific books, but touch essential things in our business.
Either the way we work together or address some common misconceptions, for example sleep being an optional aspect to live:

* [Richard Sennett - Together.The Rituals, Pleasures and Politics of Cooperation](https://yalebooks.yale.edu/book/9780300188288/together)
* [Matthew Walker - Why We Sleep](https://www.gatesnotes.com/Books/Why-We-Sleep)
* [Robin DiAngelo - White Fragility](https://www.tolerance.org/magazine/summer-2019/whats-my-complicity-talking-white-fragility-with-robin-diangelo) (A recommended read for all white developers and a good starter to understand why we need to work actively against racism and not manifest structural racism in our software based solutions)

## Essays and other articles

### Best practices

* [Jonathan Giles - Java Best Practices](http://java.jonathangiles.net)
* [Jon P Smith - What makes a good software library?](https://www.thereformedprogrammer.net/what-makes-a-good-software-library/)

### Architecture

* [Beate Ritterbach – Vererbung: für Objekte nützlich, für Werte gefährlich (DE)](http://www.heise.de/developer/artikel/Vererbung-fuer-Objekte-nuetzlich-fuer-Werte-gefaehrlich-3254433.html#mobile_detect_force_desktop)
* [Oliver Gierke – Whoops! Where did my architecture go (EN)](http://olivergierke.de/2013/01/whoops-where-did-my-architecture-go/)
* [Mark Seemann - Domain modelling with REST(EN)](http://blog.ploeh.dk/2016/12/07/domain-modelling-with-rest/)

### Concepts

* [John McClean - Trampolining: a practical guide for awesome Java Developers (EN)](https://medium.com/@johnmcclean/trampolining-a-practical-guide-for-awesome-java-developers-4b657d9c3076#.63mh5t4x9)

### Philosophy

* [Edsger W. Dijkstra – The Humble Programmer (EN)](https://www.cs.utexas.edu/~EWD/transcriptions/EWD03xx/EWD340.html)


## Quotes

### Josh Bloch on designing for inheritance / extension

From "Effective Java, 2nd Edition"

> The class must document its self-use of overridable methods. By convention, a method that invokes overridable methods contains a description of these invocations at the end of its documentation comment. The description begins with the phrase "This implementation."
> 
> The best solution to this problem is to prohibit subclassing in classes that are not designed and documented to be safely subclassed.
> 
> If a concrete class does not implement a standard interface, then you may inconvenience some programmers by prohibiting inheritance. If you feel that you must allow inheritance from such a class, one reasonable approach is to ensure that the class never invokes any of its overridable methods and to document this fact. In other words, eliminate the class's self-use of overridable methods entirely. In doing so, you'll create a class that is reasonably safe to subclass. Overriding a method will never affect the behavior of any other method.
