package sh;

import java.util.HashMap;
import java.util.Map;

public class Argument {
    Map<Character, Object> argumentsValus = new HashMap<Character, Object>();
    public Argument(String schema, String[] args) {
        String[] argSchemaParts = schema.split(",");
        for (String argSchemaPart : argSchemaParts) {
            if (argSchemaPart.length() > 2) throw new BadSchemaException();

            if (argSchemaPart.length() == 1) {
                argumentsValus.put(argSchemaPart.charAt(0), false);
                for (String arg: args) {
                    if (arg.equals("-" + argSchemaPart)) {
                        argumentsValus.put(argSchemaPart.charAt(0), true);
                        break;
                    }
                }
            } else  {
                char argType = argSchemaPart.charAt(1);
                if (argType == '#') {
                    boolean foundArg = false;
                    for (String arg: args) {
                        if (arg.startsWith("-" + argSchemaPart.charAt(0))) {
                            String argValue = arg.substring(2);
                            int value = 0;
                            try {
                                value = Integer.parseInt(argValue);
                            } catch (NumberFormatException e) {
                                throw new BadArgFromatException();
                            }
                            argumentsValus.put(argSchemaPart.charAt(0), value);
                            foundArg = true;
                            break;
                        }
                    }

                    if (!foundArg) {
                        throw new ArgNotExistException();
                    }

                } else if (argType == '*') {
                    for (String arg: args) {
                        if (arg.startsWith("-" + argSchemaPart.charAt(0))) {
                            String argValue = arg.substring(2);
                            argumentsValus.put(argSchemaPart.charAt(0), argValue);
                            break;
                        }
                    }
                } else {
                    throw new BadSchemaException();
                }
            }
        }
    }

    public boolean getBoolean(char argName) {
        return (Boolean)argumentsValus.get(argName);
    }

    public int getInt(char argName) {
        return (Integer)argumentsValus.get(argName);
    }

    public String getString(char argName) {
        return (String)argumentsValus.get(argName);
    }
}
