package ArgsParser;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ArgsParserTests {

    @Test
    public void testAllArgsProvided() {
        String formatString = "pi: double, flag: bool, num: long, strs: string[]";
        String[] args = new String[] {"-pi", "5.2", "-flag", "-strs", "oksoto", "-num", "887889"};
        ArgsParser argsParser = new ArgsParser(formatString, args);
        assertEquals(argsParser.getBoolArg("flag"), true);
        assertEquals(argsParser.getDoubleArg("pi"), 5.2);
        assertEquals(887889, argsParser.getLongArg("num"));
        assertArrayEquals(new String[] {"oksoto"}, argsParser.getStringArrayArg("strs"));
    }

    @Test
    public void testPartialArgsProvided() {
        String formatString = "pi: double, flag: bool, num: long, strs: string[]";
        String[] args = new String[] {"-pi", "5.2", "-flag"};
        ArgsParser argsParser = new ArgsParser(formatString, args);
        assertEquals(argsParser.getBoolArg("flag"), true);
        assertEquals(argsParser.getDoubleArg("pi"), 5.2);
        assertNull(argsParser.getLongArg("num"));
    }

    @Test
    public void testNoArgsProvided() {
        String formatString = "pi: double, flag: bool, num: long, strs: string[]";
        ArgsParser argsParser = new ArgsParser(formatString, new String[] {});
        assertFalse(argsParser.getBoolArg("flag"));
        assertNull(argsParser.getLongArg("num"));
        assertNull(argsParser.getDoubleArg("pi"));
    }

    @Test
    public void testUnexpectedArgQuery() {
        String formatString = "pi: double, flag: bool, num: long, strs: string[]";
        ArgsParser argsParser = new ArgsParser(formatString, new String[] {});
        assertThrows(RuntimeException.class, () -> {
            argsParser.hasArg("test");
        });
    }

    @Test
    public void testUnexpectedArgNamesInArgs() {
        String formatString = "pi: double, flag: bool, num: long, strs: string[]";
        ArgsParser argsParser = new ArgsParser(formatString, new String[] {});
        assertThrows(RuntimeException.class, () -> {
            argsParser.getBoolArg("test");
        });
    }

    @Test
    public void testUnexpectedDataType() {
        assertThrows(RuntimeException.class, () -> {
            ArgsParser argsParser = new ArgsParser("pi: double, l: bool, p: bool, num: int", new String[] {});
        });
    }

    @Test
    public void testLongType() {
        ArgsParser argsParser = new ArgsParser("num: long", new String[] {"-num", "-80"});

        assertEquals(-80, argsParser.getLongArg("num"));
        assertThrows(NumberFormatException.class, () -> {
            ArgsParser arg = new ArgsParser("num: long", new String[] {"-num", "test"});
        });
        argsParser = new ArgsParser("num: long", new String[] {});
        assertNull(argsParser.getLongArg("num"));
        assertFalse(argsParser.hasArg("num"));
    }

    @Test
    public void testBoolType() {
        ArgsParser argsParser = new ArgsParser("flag: bool", new String[] {"-flag"});
        assertTrue(argsParser.getBoolArg("flag"));
        argsParser = new ArgsParser("flag1: bool, flag2: bool, flag3: bool", new String[] {"-flag1", "-flag2", "-flag3"});
        assertTrue(argsParser.getBoolArg("flag1"));
        assertTrue(argsParser.getBoolArg("flag2"));
        assertTrue(argsParser.getBoolArg("flag3"));
        argsParser = new ArgsParser("flag: bool", new String[] {});
        assertFalse(argsParser.getBoolArg("flag"));
        assertFalse(argsParser.hasArg("flag"));
    }

    @Test
    public void testDoubleType() {
        ArgsParser argsParser = new ArgsParser("num: double", new String[] {"-num", "0.5"});
        assertEquals(0.5, argsParser.getDoubleArg("num"));
        assertTrue(argsParser.hasArg("num"));
        assertThrows(NumberFormatException.class, () -> {
           ArgsParser argsParser1 = new ArgsParser("num: double", new String[] {"-num", "test"});
        });
    }

    @Test
    public void testStringArrayType() {
        ArgsParser argsParser = new ArgsParser("strs: string[]", new String[] {"-strs", "single-word"});
        assertTrue(argsParser.hasArg("strs"));
        assertArrayEquals(new String[] {"single-word"}, argsParser.getStringArrayArg("strs"));
        argsParser = new ArgsParser("strs: string[]", new String[] {"-strs", "ok the only"});
        assertArrayEquals(new String[] {"ok", "the", "only"}, argsParser.getStringArrayArg("strs"));
        argsParser = new ArgsParser("strs: string[]", new String[] {});
        assertNull(argsParser.getStringArrayArg("strs"));
    }

    @Test
    public void testGarbageFormatString() {
        assertThrows(RuntimeException.class, () -> {
            ArgsParser argsParser = new ArgsParser("test", new String[] {});
        });
        assertThrows(RuntimeException.class, () -> {
           ArgsParser argsParser = new ArgsParser("num: int", new String[] {});
        });
        assertThrows(RuntimeException.class, () -> {
           ArgsParser argsParser = new ArgsParser("num: double strs: string[]", new String[] {});
        });
    }

    @Test
    public void testGarbageArgs() {
        assertThrows(RuntimeException.class, () -> {
           ArgsParser argsParser = new ArgsParser("ok: bool", new String[]{"-flag"});
        });
        assertThrows(RuntimeException.class, () -> {
           ArgsParser argsParser = new ArgsParser("d: bool", new String[] {"d"});
        });
        assertThrows(RuntimeException.class, () -> {
           ArgsParser argsParser = new ArgsParser("strs: string[]", new String[] {"-strs"});
        });
    }
}
