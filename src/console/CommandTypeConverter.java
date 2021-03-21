package console;

import constants.StringConstants;
import entities.CommandType;

public class CommandTypeConverter {
    public static String getPatternByType(CommandType commandType) {
        if (commandType == CommandType.Set) {
            return StringConstants.SetCommandPattern;
        }
        if (commandType == CommandType.Rotate) {
            return StringConstants.RotateCommandPattern;
        }

        return StringConstants.EmptyCommandPattern;
    }
}
