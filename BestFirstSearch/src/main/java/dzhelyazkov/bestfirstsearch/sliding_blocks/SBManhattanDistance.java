package dzhelyazkov.bestfirstsearch.sliding_blocks;

import dzhelyazkov.bestfirstsearch.api.Heuristic;
import dzhelyazkov.utils.ManhattanDistanceCalc;
import dzhelyazkov.utils.Point;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by dzhel on 25.10.2017.
 */
public class SBManhattanDistance implements Heuristic<SBState> {
    @Override
    public Number calcDistance(SBState node1, SBState node2) {
        Map<Integer, Point> map1 = convertToMap(node1.getBoard());
        Map<Integer, Point> map2 = convertToMap(node2.getBoard());

        int distance = 0;
        ManhattanDistanceCalc manhattanDistanceCalc = new ManhattanDistanceCalc();
        for (Map.Entry<Integer, Point> map1Entry : map1.entrySet()) {
            Point point1 = map1Entry.getValue();
            Point point2 = map2.get(map1Entry.getKey());
            distance += manhattanDistanceCalc.calcDistance(point1, point2);
        }

        return distance;
    }

    private Map<Integer, Point> convertToMap(int[][] sbBoard) {
        Map<Integer, Point> result = new HashMap<>(sbBoard.length * sbBoard[0].length);
        for (int i = 0; i < sbBoard.length; i++) {
            for (int j = 0; j < sbBoard[i].length; j++) {
                result.put(sbBoard[i][j], new Point(j, i));
            }
        }

        return result;
    }

}
