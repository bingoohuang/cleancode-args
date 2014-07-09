package huhhot;

import huhhot.exception.ArgFormatInvalidException;
import huhhot.exception.ArgNotFoundException;
import huhhot.exception.ArgTypeInvalidException;
import org.junit.Assert;
import org.junit.Test;

public class ArgsTest {
    @Test
    public void testBooleanTrue() {
        Args args = new Args("l", new String[]{"-l"});
        boolean logging = args.getBoolean('l');
        Assert.assertEquals(true, logging);
    }

    @Test
    public void testBooleanFalse() {
        Args args = new Args("l", new String[]{});
        boolean logging = args.getBoolean('l');
        Assert.assertEquals(false, logging);
    }

    @Test
    public void testInt() {
        Args args = new Args("l#", new String[]{"-l8080"});
        int port = args.getInt('l');
        Assert.assertEquals(8080, port);
    }

    @Test(expected = ArgTypeInvalidException.class)
    public void testIntButGetAsString() {
        Args args = new Args("l#", new String[]{"-l8080"});
        args.getString('l');
    }

    @Test(expected = ArgFormatInvalidException.class)
    public void testIntInvalidFormat() {
        Args args = new Args("l#", new String[]{"-labc0"});
        args.getInt('l');
    }

    @Test(expected = ArgNotFoundException.class)
    public void testIntNotExists() {
        Args args = new Args("l#", new String[]{});
        args.getInt('l');
    }

    @Test
    public void testString() {
        Args args = new Args("l*", new String[]{"-l8080"});
        String value = args.getString('l');
        Assert.assertEquals("8080", value);
    }

    @Test(expected = ArgNotFoundException.class)
    public void testStringNotExists() {
        Args args = new Args("l*", new String[]{});
        args.getString('l');
    }

    @Test
    public void testBooleanIntString() {
        Args args = new Args("l,p#,d*",
                new String[]{"-l", "-p8080", "-d/home/bingoo"});
        Assert.assertEquals(true, args.getBoolean('l'));
        Assert.assertEquals(8080, args.getInt('p'));
        Assert.assertEquals("/home/bingoo", args.getString('d'));
    }
}
