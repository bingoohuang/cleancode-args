package parser;

import exception.BadArgExeception;

public class IntegerParser implements ArgTypeParser {
    @Override
    public Integer parse(String arg) {
        String argValue = arg.substring(2);
        try {
            return Integer.parseInt(argValue, 10);
        } catch (NumberFormatException e) {
            throw new BadArgExeception(argValue + " is not an integer");
        }
    }
}
