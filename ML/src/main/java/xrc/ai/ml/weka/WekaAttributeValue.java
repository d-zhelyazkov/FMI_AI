package xrc.ai.ml.weka;

import weka.core.Attribute;
import weka.core.Instance;
import xrc.ai.ml.instance.AttributeValue;

public class WekaAttributeValue implements AttributeValue {

    private final double value;

    private final String stringValue;

    public WekaAttributeValue(double value) {
        this(value, "");
    }

    public WekaAttributeValue(double value, String stringValue) {
        this.value = value;
        this.stringValue = stringValue;
    }

    public WekaAttributeValue(Instance instance, Attribute attribute) {
        value = instance.value(attribute);
        stringValue = (attribute.isNominal() || attribute.isString() || attribute.isDate())
                ? instance.stringValue(attribute) : "";
    }

    @Override
    public double getDoubleValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        WekaAttributeValue that = (WekaAttributeValue) o;

        return xrc.utils.Double.equal(that.value, value);
    }

    @Override
    public int hashCode() {
        long temp = Double.doubleToLongBits(value);
        return (int) (temp ^ (temp >>> 32));
    }

    @Override
    public String toString() {
        return String.format("%f %s", value, stringValue);
    }
}
