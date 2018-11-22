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

        List<Animal> animals = new LinkedList<>(toAdd);
//        LinkedList<Animal> dogs = fromList_badDesign(toAdd); // compile time error

        assertThat(animals.size(), is(2));
    }
    
    private static <T> LinkedList<T> fromList_badDesign(List<T> list) {
        return new LinkedList<>(list);
    }

    @Test
    public void consumer() {
        List<Animal> dogs = new LinkedList<>();
//        List<Animal> cats = new LinkedList<>();
//        addDog(cats) // compile time error
        addDog(dogs);
    }
    
     private static void addDog(Collection<? super Dog> c) {
        c.add(new Dog());
    }
}
