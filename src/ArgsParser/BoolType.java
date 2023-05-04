package ArgsParser;

import java.util.ListIterator;

public class BoolType implements ArgDataType {

    private boolean value = false;

    @Override
    public void setArg(ListIterator<String> argsIterator) {
        value = true;
    }

    @Override
    public Object getValue() {
        return value;
    }

    @Override
    public boolean hasValue() {
        return value;
    }
}
