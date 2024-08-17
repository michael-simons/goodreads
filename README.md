# Goodreads

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
* [DevOps Tools for Java Developers (Various authors)](https://www.oreilly.com/library/view/devops-tools-for/9781492084013/)
* [Dan McQuillan - Resisting AI, An anti-fascist approach to artificial intelligence](https://bristoluniversitypress.co.uk/resisting-ai) (One of the most important books in the field of AI in 2023 and later)

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
* [Jonathan Taplin - The End of Reality: How Four Billionaires are Selling a Fantasy Future of the Metaverse, Mars, and Crypto](https://www.sandmanbooks.com/book/9781541703155) (Proper eye-opener, with a lot of history and a semi-optimistic outlook)

## Work in general

* [Gavin Mueller - Breaking Things At Work: The Luddites Were Right About Why You Hate Your Job](https://archive.org/details/breaking-things-at-work-the-luddites-were-right-about-why-you-hate-your-job-by-gavin-mueller/mode/2up) (I don't hate my job, quite the contrary, but this book is both a good history whirlwind and a reassurance that I am not the only one who struggles to understand ongoing automation, enshittification and in general, making billionaires richer by making us all poorer and less autonomous)

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

## My library

`all.csv` contains an incomplete list of books in my library. The CSV file has 4 columns separated by `;`. 

| Name   | Description                                                   |
|--------|---------------------------------------------------------------|
| Author | One or more authors, `last name, first name` separated by `&` |
| Title  | Title of the book                                             |
| Type   | R, S, C (Roman (Fiction), Sachbuch (Non-Fiction), Comic)      |
| State  | R, U (Read, Unread)                                           |

### Interacting with the CSV file

#### Using SQLite to query the database

```
sqlite3 :memory: \
 '.mode csv' \
 '.separator ,' \
 '.import "|curl -s https://raw.githubusercontent.com/michael-simons/goodreads/master/all.csv" books' \
 "SELECT title FROM books WHERE author like '%King%' ORDER by title"
```

#### Using DuckDB

[DuckDB](https://duckdb.org) is an incredible versatile, in-process SQL OLAP database management system and while most likely total overkill for the small dataset, it's fun. Install and start DuckDB:

```sql
-- Required to directly import the csv file from Github
INSTALL httpfs;
-- Just query the dataset
SELECT DISTINCT author FROM read_csv('https://raw.githubusercontent.com/michael-simons/goodreads/master/all.csv', header=true, auto_detect=true);
-- Create a table named books
CREATE TABLE books AS SELECT * FROM read_csv('https://raw.githubusercontent.com/michael-simons/goodreads/master/all.csv', header=true, auto_detect=true);
-- Query and manipulate as needed
-- Save the result (overwriting all.csv and sorting it on the way)
COPY (SELECT * FROM books ORDER BY author COLLATE de ASC, title COLLATE de ASC) TO 'all.csv' WITH (header true);
````

#### Using Neo4j

I used to run a browseable, interactive list of all books on Heroku using a free [Neo4j AuraDB instance](https://neo4j.com/cloud/platform/aura-graph-database/), but Heroku stopped offering a free service a while ago. The repository containing the application code (based on Quarkus) is still available:
[neo4j-aura-quarkus-graphql project](https://github.com/michael-simons/neo4j-aura-quarkus-graphql). Follow the instruction in the README.

The essential query to import the CSV into Neo4j looks like this

```cypher
LOAD CSV WITH HEADERS FROM 'https://raw.githubusercontent.com/michael-simons/goodreads/master/all.csv' AS row FIELDTERMINATOR ',' 
MERGE (b:Book {
  title: trim(row.Title)
})
SET b.type = row.Type, b.state = row.State
WITH b, row
UNWIND split(row.Author, '&') AS author
WITH b, split(author, ',') AS author
WITH b, ((trim(coalesce(author[1], '')) + ' ') + trim(author[0])) AS author
MERGE (a:Person {
  name: trim(author)
})
MERGE (a)-[r:WROTE]->(b)
WITH b, a
WITH b, collect(a) AS authors
RETURN id(b) AS id, b.title, b.state, authors
```

#### Using xsv

[xsv](https://github.com/BurntSushi/xsv) is a powerful tool for manipulating CSV. Here's an example how to get a list of unique authors

```bash
curl -s https://raw.githubusercontent.com/michael-simons/goodreads/master/all.csv | \
  xsv select -d "," Author |\
  uniq
```

#### Using the webui

If you have [JBang](https://www.jbang.dev) installed you can start an admin "UI" like this:

```bash
jbang admin.java
```

Access the page at [localhost:8080](http://localhost:8080).
