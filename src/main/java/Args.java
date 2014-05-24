import impl.ArgsRuntime;
import parser.ArgsDefinition;

public class Args {
    ArgsRuntime argsRuntime = new ArgsRuntime();

    public Args(String argsPattern, String[] args) {
        ArgsDefinition argsDefinition = new ArgsDefinition();
        argsDefinition.parseArgsDefinition(argsPattern);
        argsRuntime.analyseArgs(argsDefinition, args);
    }

    public boolean getBoolean(char argumentName) {
        return argsRuntime.containsArg(argumentName);
    }

    public int getInt(char argumentName) {
        return argsRuntime.getArgValue(argumentName, Integer.class);
    }

    public String getString(char argumentName) {
        return argsRuntime.getArgValue(argumentName, String.class);
    }
}
