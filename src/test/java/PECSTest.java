import animal.Animal;
import animal.Dog;
import org.junit.Test;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by mtumilowicz on 2018-11-22.
 */
public class PECSTest {
    @Test
    public void producer() {
        List<Dog> toAdd = List.of(new Dog(), new Dog());

        List<Animal> animals = fromList_goodDesign(toAdd);
//        LinkedList<Animal> dogs = fromList_badDesign(new LinkedList<Dog>()); // compile time error

        assertThat(animals.size(), is(2));
    }
    
    private static <T> LinkedList<T> fromList_badDesign(Collection<T> list) {
        return new LinkedList<>(list);
    }

    private static <T> LinkedList<T> fromList_goodDesign(Collection<? extends T> list) {
        return new LinkedList<>(list);
    }

    @Test
    public void consumer() {
        addDog_goodDesign(new LinkedList<Animal>());
//        addDog_goodDesign(new LinkedList<Cat>()); // compile time error
//        addDog_badDesign(new LinkedList<Animal>()); // compile time error
//        addDog_badDesign2(new LinkedList<Dog>()); // compile time error
    }
    
     private static void addDog_goodDesign(Collection<? super Dog> c) {
        c.add(new Dog());
    }

    private static void addDog_badDesign(Collection<Dog> c) {
        c.add(new Dog());
    }

    private static void addDog_badDesign2(Collection<Animal> c) {
        c.add(new Dog());
    }
}
