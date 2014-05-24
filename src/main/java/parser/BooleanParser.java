package parser;

public class BooleanParser implements ArgTypeParser {
    @Override
    public Boolean parse(String arg) {
        return true;
    }
}
