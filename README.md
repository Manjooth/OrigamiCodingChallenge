# Forgetting Map

## Aim

The aim of the forgetting map is to design, implement and test a thread-safe `forgetting map`

A `forgetting map` should hold associations between a `key` and some `content`.

It should implement at least two methods

1. add (add an association)
2. find (find content using the specified key)

It should hold as many associations as it can, but no more than x associations at any time, with x being a parameter
passed to the constructor.
The association that is least used is removed from the map when a new entry requires a space and the map is at capacity,
where “least used” relates to the number of times each association has been retrieved by the “find” method.
A suitable tiebreaker should be used in the case where there are multiple least-used entries.


## How to make something thread-safe
- using `synchronisation`

Synchronization is the process of allowing only one thread at a time to complete the particular task. It means when
multiple threads executing simultaneously, and want to access the same resource at the same time, then the problem of
inconsistency will occur. so synchronization is used to resolve inconsistency problem by allowing only one thread at a
time. Synchronization uses a synchronized keyword. Synchronized is the modifier that creates a block of code known as
a critical section.

- using the `volatile` keyword

A volatile keyword is a field modifier that ensures that the object can be used by multiple threads at the same time
without having any problem. volatile is one good way of ensuring that the Java program is thread-safe. a volatile
keyword can be used as an alternative way of achieving Thread Safety in Java.

- using the `atomic variable`

Using an atomic variable is another way to achieve thread-safety in java. When variables are shared by multiple
threads, the atomic variable ensures that threads don’t crash into each other.

- using the `final` keyword

Final Variables are also thread-safe in java because once assigned some reference of an object It cannot
point to reference of another object.
