package console;

import constants.StringConstants;
import entities.CommandResult;
import entities.CommandType;

import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class CommandReader {

    public static CommandResult readCommand(CommandType commandType) {
        Scanner scanner = new Scanner(System.in);
        if (scanner.hasNextLine()) {
            String command = Arrays.stream(scanner.nextLine().split(StringConstants.Space))
                                   .filter(p -> p.length() > 0)
                                   .collect(Collectors.joining(StringConstants.Space));

            String regex = CommandTypeConverter.getPatternByType(commandType);
            if (Pattern.matches(regex, command)) {
                return new CommandResult(commandType, command);
            } else {
                return new CommandResult(CommandType.Unknown, command);
            }
        }

        return new CommandResult(CommandType.Unknown, "");
    }
}
