package dzhelyazkov.evolutinary_algorithms;

import dzhelyazkov.evolutinary_algorithms.impl.RouletteWheelSelector;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

class RouletteWheelSelectorTest {

    private static final Function<Integer, Integer> SELECTIONS_FUNC = (integer -> (int) Math.pow(integer, 4));

    @ParameterizedTest
    @ValueSource(ints = { 10, 20, 50, 100 })
    void test(int individuals) {
        int selectionsCount = SELECTIONS_FUNC.apply(individuals);
        System.out.printf("Individuals: %d, Selections: %d\n", individuals, selectionsCount);

        List<NumberSelection> selections =
                IntStream.rangeClosed(1, individuals).boxed().map(NumberSelection::new).collect(Collectors.toList());
        Collections.reverse(selections);

        CrossoverSelector<NumberSelection> selector =
                new RouletteWheelSelector<>(numberSelection -> numberSelection.number);
        selector.setPopulation(selections);
        Stream.generate(selector).limit(selectionsCount).forEach(numberSelection -> numberSelection.selected++);

        for (NumberSelection selection : selections) {
            System.out.printf("%d -> %.2f%%\n",
                    selection.number,
                    ((double) selection.selected / selectionsCount) * 100);
        }

        List<NumberSelection> sortedSelections = new ArrayList<>(selections);
        sortedSelections.sort((o1, o2) -> Integer.compare(o2.selected, o1.selected));

        Assertions.assertEquals(selections, sortedSelections);
    }

    private class NumberSelection implements Individual {
        final int number;

        int selected = 0;

        private NumberSelection(int number) {
            this.number = number;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o)
                return true;
            if (o == null || getClass() != o.getClass())
                return false;

            NumberSelection that = (NumberSelection) o;

            return number == that.number;
        }

        @Override
        public int hashCode() {
            return number;
        }

        @Override
        public String toString() {
            return String.valueOf(number);
        }
    }
}