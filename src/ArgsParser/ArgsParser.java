package ArgsParser;

import java.util.*;

public class ArgsParser {

    private final Map<String, ArgDataType> argsFound;
    private final List<String> expectedArgs;

    public ArgsParser(String format, String[] args) {
        argsFound = new HashMap<>();
        expectedArgs = new LinkedList<>();
        parseFormatString(format);
        parseArgs(Arrays.asList(args));
    }

    public boolean hasArg(String argName) {
        if (!expectedArgs.contains(argName)) {
            throw new RuntimeException(String.format("Unexpected Argument: '%s'.", argName));
        }
        return argsFound.get(argName).hasValue();
    }

    public Boolean getBoolArg(String argName) {
        if (!expectedArgs.contains(argName)) {
            throw new RuntimeException(String.format("Unexpected Argument: '%s'.", argName));
        }
        return (Boolean) argsFound.get(argName).getValue();
    }

    public String[] getStringArrayArg(String argName) {
        if (!expectedArgs.contains(argName))
            throw new RuntimeException(String.format("Unexpected Argument: '%s'.", argName));
        return (String[]) argsFound.get(argName).getValue();
    }

    public Long getLongArg(String argName) {
        if (!expectedArgs.contains(argName)) {
            throw new RuntimeException(String.format("Unexpected Argument: '%s'.", argName));
        }
        return (Long) argsFound.get(argName).getValue();
    }

    public Double getDoubleArg(String argName) {
        if (!expectedArgs.contains(argName))
            throw new RuntimeException(String.format("Unexpected Argument: '%s'.", argName));
        return (Double) argsFound.get(argName).getValue();
    }

    private void parseFormatString(String format) {
        String[] args = format.split(",");
        for (String arg : args) {
            arg = arg.replaceAll(" ", "");
            parseFormatArg(arg);
            expectedArgs.add(arg.split(":")[0]);
        }
    }

    private void parseFormatArg(String arg) {
        String[] parsedArg = arg.split(":");
        if (parsedArg.length != 2) {
            throw new RuntimeException(String.format("Format String Error - Arg: '%s' Could Not Be Parsed.", arg));
        }
        String name = parsedArg[0];
        String dataType = parsedArg[1];
        switch (dataType) {
            case "bool" -> {
                argsFound.put(name, new BoolType());
                return;
            }
            case "long" -> {
                argsFound.put(name, new LongType());
                return;
            }
            case "double" -> {
                argsFound.put(name, new DoubleType());
                return;
            }
            case "string[]" -> {
                argsFound.put(name, new StringArrayType());
                return;
            }
        }
        throw new RuntimeException(String.format("Unexpected Data Type In Format String: '%s'.", dataType));
    }

    private void parseArgs(List<String> args) {
        ListIterator<String> argsIterator = args.listIterator();
        while (argsIterator.hasNext()) {
            String arg = argsIterator.next();
            if (arg.charAt(0) != '-') {
                throw new RuntimeException("Unexpected Argument: " + arg);
            }
            argsFound.get(arg.substring(1)).setArg(argsIterator);
        }
    }
}
