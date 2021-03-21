package color;

import java.util.Map;

public class ColorConverter {
    private static final Map<BallColor, ConsoleColor> colorMap = Map.of(
            BallColor.Black, ConsoleColor.Black,
            BallColor.White, ConsoleColor.White,
            BallColor.Red, ConsoleColor.Red,
            BallColor.Yellow, ConsoleColor.Yellow,
            BallColor.Green, ConsoleColor.Green,
            BallColor.Blue, ConsoleColor.Blue
    );

    public static boolean exist(BallColor ballColor) {
        return colorMap.containsKey(ballColor);
    }

    public static String convertToConsoleColor(BallColor ballColor) {
        if (exist(ballColor)) {
            return colorMap.get(ballColor).toString();
        }
        return "";
    }
}
