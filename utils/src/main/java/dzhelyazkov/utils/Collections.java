package dzhelyazkov.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

public class Collections {

    public static <T> T getRandomElement(Collection<T> elements) {
        return getRandomElement(new ArrayList<>(elements));
    }

    public static <T> T getRandomElement(List<T> elements) {
        return elements.get(new Random().nextInt(elements.size()));
    }
}
