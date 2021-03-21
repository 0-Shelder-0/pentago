package constants;

public class StringConstants {
    public static final String Space = "\s";
    public static final String DoubleSpace = "\s\s";
    public static final String NewLine = "\r\n";

    public static final String Frame = "│";
    public static final String FrameLine = "─";
    public static final String FrameLeftBottom = "└";
    public static final String FrameRightBottom = "┘";
    public static final String FrameLeftTop = "┌";
    public static final String FrameRightTop = "┐";
    public static final String FrameLineTopSplitter = "┬";
    public static final String FrameLineBottomSplitter = "┴";

    public static final String Ball = "⚫";
    public static final String EmptyCell = "○";

    public static final String SetCommandHintFormat = "Set the %s ball in cell: ";
    public static final String RotateCommandHint = "Rotate the quadrant: ";

    public static final String EmptyCommandPattern = "^$";
    public static final String SetCommandPattern = "^(\\d+\\s){3}\\d+$";
    public static final String RotateCommandPattern = "^(\\d+\\s+){2}[RrLl]$";

    public static final String SetCommandFormat = ":xQuadrant :yQuadrant :xBall :yBall";
    public static final String RotateCommandFormat = ":xQuadrant :yQuadrant :[RrLl]";

    public static final String CommandIsIncorrectFormat = "Command '%s' is incorrect!";
    public static final String ParametersIsIncorrectFormat = "Parameters '%s' or/and '%s' is incorrect!";

    public static final String WinStatusMessage = "Winner: ";
    public static final String DrawStatusMessage = "Winners: ";
}