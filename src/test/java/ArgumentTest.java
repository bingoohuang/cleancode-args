import org.junit.Test;
import sh.ArgNotExistException;
import sh.Argument;
import sh.BadArgFromatException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ArgumentTest {
    @Test
    public void testBoolean() {
        Argument argument = new Argument("l", new String[]{"-l"});
        boolean logging = argument.getBoolean('l');
        assertTrue(logging);
    }

    @Test
    public void testBooleanFalse() {
        Argument argument = new Argument("l", new String[]{});
        boolean logging = argument.getBoolean('l');
        assertFalse(logging);
    }

    @Test
    public void testBooleanIntString() {
        Argument argument = new Argument("l,p#,d*", new String[]{"-l", "-p80", "-d/home/bingoo"});
        boolean logging = argument.getBoolean('l');
        assertTrue(logging);

        int port = argument.getInt('p');
        assertEquals(80, port);

        String path = argument.getString('d');
        assertEquals("/home/bingoo", path);
    }

    @Test(expected = BadArgFromatException.class)
    public void testBadInt() {
        new Argument("l,p#,d*", new String[]{"-l", "-pbingoo"});
    }

    @Test(expected = ArgNotExistException.class)
    public void testNoexistInt() {
        new Argument("l,p#,d*", new String[]{"-l"}).getInt('p');
    }
}
