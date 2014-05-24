package impl;

import exception.BadArgDefinitionException;
import exception.BadArgExeception;
import parser.ArgTypeParser;
import parser.ArgsDefinition;

import java.util.HashMap;
import java.util.Map;

public class ArgsRuntime {
    Map<Character, Object> argumentResult = new HashMap<Character, Object>();

    public void analyseArgs(ArgsDefinition argsDefinition, String[] args) {
        for (String arg : args) {
            if (arg.length() <= 1 || !arg.startsWith("-"))
                throw new BadArgExeception(arg + " is not started with hyphen(-)");

            char argName = arg.charAt(1);
            ArgTypeParser argTypeParser = argsDefinition.getArgTypeParser(argName);
            if (argTypeParser != null) {
                Object result = argTypeParser.parse(arg);
                argumentResult.put(argName, result);
            }
        }
    }

    public boolean containsArg(char argumentName) {
        return argumentResult.containsKey(argumentName);
    }

    public <T> T getArgValue(char argumentName, Class<T> clazz) {
        try {
            return (T) argumentResult.get(argumentName);
        } catch (ClassCastException ex) {
            throw new BadArgDefinitionException(argumentName + "'s value is not a type of " + clazz);
        }
    }

}

