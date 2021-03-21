package entities;

import color.BallColor;
import color.ColorConverter;
import color.ConsoleColor;
import constants.StringConstants;

public class Ball {
    private final String _ballStr;
    private final BallColor _color;

    public Ball(BallColor color) {
        this(color, StringConstants.Ball);
    }

    public Ball(BallColor color, String ballStr) {
        _color = color;
        _ballStr = ballStr;
    }

    public BallColor getColor() {
        return _color;
    }

    @Override
    public String toString() {
        return String.format("%s%s%s", ColorConverter.convertToConsoleColor(_color), _ballStr, ConsoleColor.Reset);
    }
}
