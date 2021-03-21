package entities;

public class CommandResult {
    public final CommandType commandType;
    public final String command;

    public CommandResult(CommandType commandType, String command) {
        this.command = command;
        this.commandType = commandType;
    }
}
