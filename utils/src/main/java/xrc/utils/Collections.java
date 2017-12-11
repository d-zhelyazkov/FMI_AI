package xrc.utils;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class Collections {

    public static <T> T getRandomElement(Collection<T> elements) {
        int elemIx = new Random().nextInt(elements.size());
        Iterator<T> iterator = elements.iterator();
        while ((elemIx--) > 0) {
            iterator.next();
        }

        return iterator.next();
    }

    public static <T> T getRandomElement(List<T> elements) {
        return elements.get(new Random().nextInt(elements.size()));
    }
}
