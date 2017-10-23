package frogs;

import api.GraphSearch;
import api.Node;
import impl.DFSSearch;

import java.util.Arrays;
import java.util.Collection;
import java.util.Scanner;

/**
 * Created by dzhel on 8.10.2017 г..
 */
public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int N = scanner.nextInt();
        scanner.close();

        FrogsField initialField = createInitialField(N);
        FrogsField goalField = createGoalField(N);

        GraphSearch graphSearch = new DFSSearch();
        Collection<Node> path = graphSearch.searchPath(initialField, goalField);
        outputPath(path);
    }

    private static void outputPath(Collection<Node> path) {
        for (Node frogsField : path) {
            System.out.println(frogsField);
        }

    }

    private static FrogsField createGoalField(int N) {
        char[] field = new char[N * 2 + 1];
        Arrays.fill(field, 0, N, State.LEFT);
        field[N] = State.EMPTY;
        Arrays.fill(field, N + 1, N * 2 + 1, State.RIGHT);

        return new FrogsField(field);
    }

    private static FrogsField createInitialField(int N) {
        char[] field = new char[N * 2 + 1];
        Arrays.fill(field, 0, N, State.RIGHT);
        field[N] = State.EMPTY;
        Arrays.fill(field, N + 1, N * 2 + 1, State.LEFT);

        return new FrogsField(field);
    }
}
