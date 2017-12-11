package xrc.ai.ml.algorithms;

import xrc.ai.ml.MLAlgorithm;
import xrc.ai.ml.instance.Attribute;
import xrc.ai.ml.instance.Instance;
import xrc.ai.ml.instance.InstanceSet;
import xrc.utils.Point;
import xrc.utils.distance_calculator.DistanceCalculator;
import xrc.utils.distance_calculator.EuclideanDistanceCalculator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public abstract class KNNBase implements MLAlgorithm{
    private final int K;

    private Collection<InstancePoint> instancePoints;

    private Collection<Attribute> attributes;

    Attribute classAttribute;

    KNNBase(int k) {
        K = k;
    }

    @Override
    public void train(InstanceSet trainSet, Attribute classAttribute) {
        this.classAttribute = classAttribute;

        attributes = trainSet.getAttributes();
        attributes.remove(classAttribute);

        Collection<Instance> instances = trainSet.getInstances();
        instancePoints = new ArrayList<>(instances.size());
        for (Instance instance : instances) {
            instancePoints.add(new InstancePoint(getInstancePoint(instance), instance));
        }
    }

    Collection<InstanceDistance> getNearestNeighbours(Instance instance) {
        Point point = getInstancePoint(instance);

        List<InstanceDistance> distances = new ArrayList<>(instancePoints.size());
        DistanceCalculator distanceCalculator = new EuclideanDistanceCalculator();
        for (InstancePoint instancePoint : instancePoints) {
            double distance = distanceCalculator.calcDistance(point, instancePoint.point);
            distances.add(new InstanceDistance(distance, instancePoint.instance));
        }

        Collections.sort(distances);

        return distances.subList(0, K);
    }

    private Point getInstancePoint(Instance instance) {
        double[] values = new double[attributes.size()];
        int i = 0;
        for (Attribute attribute : attributes) {
            values[i++] = instance.get(attribute).getDoubleValue();
        }
        return new Point(values);
    }

    protected class InstanceDistance implements Comparable<InstanceDistance> {
        final double distance;

        final Instance instance;

        InstanceDistance(double distance, Instance instancePoint) {
            this.distance = distance;
            this.instance = instancePoint;
        }

        @Override
        public int compareTo(InstanceDistance o) {
            return xrc.utils.Double.compare(distance, o.distance);
        }
    }

    private class InstancePoint {

        final Instance instance;

        final Point point;

        InstancePoint(Point point, Instance instance) {
            this.point = point;
            this.instance = instance;
        }
    }
}
