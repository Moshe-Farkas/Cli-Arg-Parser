package ArgsParser;

import java.util.ListIterator;

public class DoubleType implements ArgDataType {

    private Double value = null;

    @Override
    public void setArg(ListIterator<String> argsIterator) {
        value = Double.valueOf(argsIterator.next());
    }

    @Override
    public Object getValue() {
        return value;
    }

    @Override
    public boolean hasValue() {
        return value != null;
    }
}
