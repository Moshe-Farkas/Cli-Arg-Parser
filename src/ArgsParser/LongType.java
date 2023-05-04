package ArgsParser;

import java.util.ListIterator;

public class LongType implements ArgDataType {

    private Long value = null;

    @Override
    public void setArg(ListIterator<String> argsIterator) {
        value = Long.valueOf(argsIterator.next());
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
