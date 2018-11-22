[![Build Status](https://travis-ci.com/mtumilowicz/java11-pecs-principle.svg?branch=master)](https://travis-ci.com/mtumilowicz/java11-pecs-principle)

# java11-pecs-principle
Overview of PECS principle.

_Reference_: Effective Java: Joshua Bloch  
_Reference_: https://howtodoinjava.com/java/generics/java-generics-what-is-pecs-producer-extends-consumer-super/

# preface
Please refer my other github project: https://github.com/mtumilowicz/java11-covariance-contravariance-invariance

* Covariance: `? extends Integer`
    ```
    List<Integer> ints = new LinkedList<>();
    List<? extends Number> nums = ints;
    ```
* Contravariance: `? super Integer`
    ```
    List<Integer> ints = new LinkedList<>();
    List<? super Integer> nums = ints;
    ```
* Invariance: neither covariant nor contravariant
    > Generics are invarant
    
* Remark:
    * covariance is read-only
    * contravariance is write-only
    * otherwise compile-time error

**PECS** stands for producer-extends, consumer-super:
* Use the `<? extends T>` wildcard if you need to 
retrieve object of type `T` from a collection.
* Use the `<? super T>` wildcard if you need to put 
objects of type `T` in a collection.

Properly used, wildcard types are nearly invisible to the 
users of a class (if the user of a class has to think 
about wildcard types, there is probably something wrong 
with its API.)

# project description
We provide easy examples of PECS principle (and 
we show also examples of bad design - contradiction
of PECS) in `PECSTest`:
* producer
    * constructor of a `LinkedList` is a good example of
    producer
        ```
        public LinkedList(Collection<? extends E> c) {
            this();
            addAll(c);
        }
        ```
    * if we use side function with bad design
        ```
        private static <T> LinkedList<T> fromList_badDesign(Collection<T> list) { // it should be Collection<? extends T>
            return new LinkedList<>(list);
        }
        ```
        then
        ```
        LinkedList<Animal> dogs = fromList_badDesign(new LinkedList<Dog>()); // compile time error
        ```
    * if we use side function with good design
        ```
        private static <T> LinkedList<T> fromList_goodDesign(Collection<? extends T> list) {
            return new LinkedList<>(list);
        }
        ```
        then
        ```
        LinkedList<Animal> dogs = fromList_badDesign(new LinkedList<Dog>()); // OK!
        ```
* consumer
    * `addAll` method of `Collections` is a good example of
    consumer
        ```
        boolean addAll(Collection<? super T> c, T... elements) {
            ...
        }
        ```
    * suppose we have collection of animals, we want to add
    a dog to it, **but** we don't want to add a dog to the 
    cats of course.
        ```
        private static void addDog_goodDesign(Collection<? super Dog> c) {
            c.add(new Dog());
        }
        
        private static void addDog_badDesign(Collection<Dog> c) {
            c.add(new Dog());
        }
        
        private static void addDog_badDesign2(Collection<Animal> c) {
            c.add(new Dog());
        }
        ```
        then
        ```
        List<Animal> animals = new LinkedList<>();
        addDog_goodDesign(animals); // OK
        //        addDog_badDesign(animals); // compile time error
        //        addDog_goodDesign(new LinkedList<Cat>()); // compile time error
        //        addDog_badDesign2(new LinkedList<Dog>()); // compile time error
        ```
        note that collections are invariant so:
        ```
        addDog_badDesign2(new LinkedList<Dog>()); // compile time error
        ```
    