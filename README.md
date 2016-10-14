## Architecture

* [Beate Ritterbach – Vererbung: für Objekte nützlich, für Werte gefährlich (DE)](http://www.heise.de/developer/artikel/Vererbung-fuer-Objekte-nuetzlich-fuer-Werte-gefaehrlich-3254433.html#mobile_detect_force_desktop)
* [Oliver Gierke – Whoops! Where did my architecture go (EN)](http://olivergierke.de/2013/01/whoops-where-did-my-architecture-go/)

## Philosophy

* [Edsger W. Dijkstra – The Humble Programmer (EN)](https://www.cs.utexas.edu/~EWD/transcriptions/EWD03xx/EWD340.html)

## Quotes

### Josh Bloch on designing for inheritance / extension

From "Effective Java, 2nd Edition"

> The class must document its self-use of overridable methods. By convention, a method that invokes overridable methods contains a description of these invocations at the end of its documentation comment. The description begins with the phrase "This implementation."
> 
> The best solution to this problem is to prohibit subclassing in classes that are not designed and documented to be safely subclassed.
> 
> If a concrete class does not implement a standard interface, then you may inconvenience some programmers by prohibiting inheritance. If you feel that you must allow inheritance from such a class, one reasonable approach is to ensure that the class never invokes any of its overridable methods and to document this fact. In other words, eliminate the class's self-use of overridable methods entirely. In doing so, you'll create a class that is reasonably safe to subclass. Overriding a method will never affect the behavior of any other method.