package parser;

import exception.BadArgDefinitionException;

import java.util.HashMap;
import java.util.Map;

public class ArgsDefinition {
    Map<Character, ArgTypeParser> argumentMap = new HashMap<Character, ArgTypeParser>();

    public void parseArgsDefinition(String argumentsDefinition) {
        String[] argumentDefinitions = argumentsDefinition.split(",");

        for (String argumentDefinition : argumentDefinitions) {
            if (argumentDefinition.length() > 2) throw new BadArgDefinitionException(
                    argumentDefinition + " is in unknown format");

            char argumentName = argumentDefinition.charAt(0);

            argumentMap.put(argumentName, createArgumentTypeParser(argumentDefinition));
        }
    }

    private ArgTypeParser createArgumentTypeParser(String argumentDefinition) {
        if (argumentDefinition.length() == 1) return new BooleanParser();

        char format = argumentDefinition.charAt(1);
        if (format == '#') return new IntegerParser();
        if (format == '*') return new StringParser();

        throw new BadArgDefinitionException(argumentDefinition + " is invalid");
    }

    public ArgTypeParser getArgTypeParser(char argName) {
        return argumentMap.get(argName);
    }
}
