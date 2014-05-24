package parser;

public class StringParser implements ArgTypeParser {
    @Override
    public String parse(String arg) {
        return arg.substring(2);
    }
}
