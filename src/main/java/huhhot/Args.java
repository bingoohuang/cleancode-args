package huhhot;

import huhhot.exception.ArgFormatInvalidException;
import huhhot.exception.ArgNotFoundException;
import huhhot.exception.ArgSchemaInvalidException;
import huhhot.exception.ArgTypeInvalidException;

import java.util.HashMap;
import java.util.Map;

public class Args {
    private Map<Character, ArgType> schemaMap = new HashMap<Character, ArgType>();
    private Map<Character, Object> argsValue = new HashMap<Character, Object>();

    static enum ArgType {
        BOOL, STRING, INT
    }

    public Args(String argsSchema, String[] realArgs) {
        parseArgsSchema(argsSchema);
        parseRealArgs(realArgs);
    }


    public boolean getBoolean(char argName) {
        checkValueType(argName, ArgType.BOOL);

        return (Boolean) argsValue.get(argName);
    }

    public int getInt(char argName) {
        checkValueType(argName, ArgType.INT);

        return (Integer) argsValue.get(argName);
    }

    public String getString(char argName) {
        checkValueType(argName, ArgType.STRING);

        return (String) argsValue.get(argName);
    }

    private void parseRealArgs(String[] realArgs) {
        for (Map.Entry<Character, ArgType> entry : schemaMap.entrySet()) {
            char argName = entry.getKey();
            ArgType argType = entry.getValue();

            Object argValue = parseArgValue(realArgs, argType, argName);
            if (argValue == null) throw new ArgNotFoundException();

            argsValue.put(argName, argValue);
        }
    }

    private Object parseArgValue(String[] realArgs, ArgType argType, char argName) {
        switch (argType) {
            case BOOL:
                return parseBoolean(realArgs, argName);
            case INT:
                return parseInt(realArgs, argName);
            case STRING:
                return parseString(realArgs, argName);
        }

        return null;
    }

    private String parseString(String[] realArgs, char argName) {
        return findArgValue(realArgs, argName);
    }

    private Integer parseInt(String[] realArgs, char argName) {
        String foundValue = findArgValue(realArgs, argName);

        if (foundValue == null) return null;

        try {
            return Integer.parseInt(foundValue);
        } catch (NumberFormatException e) {
            throw new ArgFormatInvalidException();
        }
    }

    private Boolean parseBoolean(String[] realArgs, char argName) {
        String foundValue = findArgValue(realArgs, argName);

        return "".equals(foundValue);
    }

    private String findArgValue(String[] realArgs, char argName) {
        for (String realArg : realArgs) {
            if (realArg.startsWith("-" + argName))
                return realArg.length() > 2 ? realArg.substring(2) : "";
        }

        return null;
    }

    private void parseArgsSchema(String argsSchema) {
        String[] argSchemas = argsSchema.split(",");
        for (String schema : argSchemas) {
            if (schema.length() == 0 || schema.length() > 2)
                throw new ArgSchemaInvalidException(schema + "不符合约定，空定义或者超过长度");

            char argName = schema.charAt(0);
            ArgType argType = parseArgType(schema);

            schemaMap.put(argName, argType);
        }
    }

    private ArgType parseArgType(String schema) {
        if (schema.length() == 1) return ArgType.BOOL;

        char argTypeChar = schema.charAt(1);
        if (argTypeChar == '#') return ArgType.INT;
        if (argTypeChar == '*') return ArgType.STRING;

        throw new ArgSchemaInvalidException(schema + "不符合约定，不是以*/#结尾");
    }

    private void checkValueType(char argName, ArgType argType) {
        if (schemaMap.get(argName) != argType)
            throw new ArgTypeInvalidException();
    }

}
