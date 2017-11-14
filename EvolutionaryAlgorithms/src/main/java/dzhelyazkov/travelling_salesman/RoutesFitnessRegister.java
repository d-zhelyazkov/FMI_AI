package dzhelyazkov.travelling_salesman;

import dzhelyazkov.distance_calculator.DistanceCalculator;
import dzhelyazkov.distance_calculator.EuclideanDistanceCalculator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class RoutesFitnessRegister implements Function<Route, Number> {

    private final Map<Route, RouteEntry> routeEntries = new HashMap<>();

    private double maxPerimeter = 0;

    @Override
    public Double apply(Route route) {
        RouteEntry routeEntry = routeEntries.get(route);
        if (routeEntry == null) {
            routeEntry = addRoute(route);
        }

        return routeEntry.fitness;
    }

    private RouteEntry addRoute(Route route) {
        double perimeter = calcPerimeter(route);
        if (perimeter > maxPerimeter) {
            setMaxPerimeter(perimeter);
        }

        RouteEntry routeEntry = new RouteEntry(perimeter, maxPerimeter - perimeter);
        routeEntries.put(route, routeEntry);

        return routeEntry;
    }

    private double calcPerimeter(Route route) {
        DistanceCalculator distanceCalculator = new EuclideanDistanceCalculator();
        List<Node> nodes = route.getGenes();
        double perimeter = 0;
        int nodesSize = nodes.size();
        for (int i = 0; i < nodesSize; i++) {
            perimeter += distanceCalculator.calcDistance(
                    nodes.get(i).getPosition(),
                    nodes.get((i + 1) % nodesSize).getPosition());
        }

        return perimeter;
    }

    private void setMaxPerimeter(double maxPerimeter) {
        this.maxPerimeter = maxPerimeter;

        for (RouteEntry routeEntry : routeEntries.values()) {
            routeEntry.fitness = maxPerimeter - routeEntry.perimeter;
        }
    }

    public double getPerimeter(Route route) {
        return routeEntries.get(route).perimeter;
    }

    public double getMaxPerimeter() {
        return maxPerimeter;
    }

    class RouteEntry {
        double perimeter;

        double fitness;

        RouteEntry(double perimeter, double fitness) {
            this.perimeter = perimeter;
            this.fitness = fitness;
        }
    }
}
