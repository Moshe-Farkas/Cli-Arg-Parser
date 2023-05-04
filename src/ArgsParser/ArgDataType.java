package ArgsParser;

import java.util.ListIterator;

public interface ArgDataType {
    void setArg(ListIterator<String> argsIterator);
    Object getValue();
    boolean hasValue();
}
