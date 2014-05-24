import exception.BadArgExeception;
import org.junit.Test;

import static org.junit.Assert.*;

public class ArgsTest {
    @Test
    public void testBooleanTrue() {
        Args args = new Args("v", new String[]{"-v"});
        boolean v = args.getBoolean('v');
        assertTrue(v);
    }

    @Test
    public void testBooleanFalse() {
        Args args = new Args("v", new String[]{});
        boolean v = args.getBoolean('v');
        assertFalse(v);
    }

    @Test
    public void testInt() {
        Args args = new Args("v#", new String[]{"-v80"});
        int port = args.getInt('v');
        assertEquals(80, port);
    }

    @Test(expected = BadArgExeception.class)
    public void testIntBad() {
        new Args("v#", new String[]{"-v8d"});
    }

    @Test
    public void testFalseInt() {
        Args args = new Args("v#,b", new String[]{"-v80"});
        int port = args.getInt('v');
        assertEquals(80, port);
        assertFalse(args.getBoolean('b'));
    }

    @Test
    public void testTrueInt() {
        Args args = new Args("v#,b", new String[]{"-v80", "-b"});
        int port = args.getInt('v');
        assertEquals(80, port);
        assertTrue(args.getBoolean('b'));
    }

    @Test
    public void testString() {
        Args args = new Args("v*", new String[]{"-v/home/bingoo"});
        String path = args.getString('v');
        assertEquals("/home/bingoo", path);
    }
}
