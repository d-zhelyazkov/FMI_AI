package xrc.ai.ml.instance;

public class AttributeValueBean implements AttributeValue {

    private final double value;

    public AttributeValueBean(double value) {
        this.value = value;
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

        AttributeValueBean that = (AttributeValueBean) o;

        return xrc.utils.Double.equal(that.value, value);
    }

    @Override
    public int hashCode() {
        long temp = Double.doubleToLongBits(value);
        return (int) (temp ^ (temp >>> 32));
    }

    @Override
    public String toString() {
        return Double.toString(value);
    }
}
