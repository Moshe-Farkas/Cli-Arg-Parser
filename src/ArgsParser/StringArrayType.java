package ArgsParser;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;

public class StringArrayType implements ArgDataType {

    private List<String> values = null;

    @Override
    public void setArg(ListIterator<String> argsIterator) {
        values = new ArrayList<>();
        for (String str : argsIterator.next().split(" ")) {
            values.add(str.replace("\"", ""));
        }
    }

    @Override
    public String[] getValue() {
        if (values == null)
            return null;

        String[] values = new String[this.values.size()];
        int i = 0;
        for (String str : this.values) {
            values[i++] = str;
        }
        return values;
    }

    @Override
    public boolean hasValue() {
        return values != null;
    }
}
