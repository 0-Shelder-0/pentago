package entities;

import constants.StringConstants;

public class Quadrant {
    private final int _length;
    private Ball[][] _ballsMap;

    public Quadrant(int length) {
        _length = length;
        _ballsMap = new Ball[_length][_length];
    }

    public int getLength() {
        return _length;
    }

    public boolean inQuadrant(int x, int y) {
        return x >= 0 && x < _length && y >= 0 && y < _length;
    }

    public Ball getCell(int x, int y) throws Exception {
        if (!inQuadrant(x, y)) {
            throw new Exception(String.format(StringConstants.ParametersIsIncorrectFormat, x, y));
        }
        return _ballsMap[x][y];
    }

    public void setCell(int x, int y, Ball ball) throws Exception {
        if (!inQuadrant(x, y)) {
            throw new Exception(String.format(StringConstants.ParametersIsIncorrectFormat, x, y));
        }
        _ballsMap[x][y] = ball;
    }

    public void rotate(QuadrantRotate rotate) {
        if (rotate == QuadrantRotate.Unknown) {
            return;
        }
        Ball[][] rotatedMap = new Ball[_length][_length];
        for (int x = 0; x < _length; x++) {
            for (int y = 0; y < _length; y++) {
                if (rotate == QuadrantRotate.Left) {
                    rotatedMap[_length - y - 1][x] = _ballsMap[x][y];
                } else if (rotate == QuadrantRotate.Right) {
                    rotatedMap[y][_length - x - 1] = _ballsMap[x][y];
                }
            }
        }
        _ballsMap = rotatedMap;
    }

    public Ball[][] getBallsMap() {
        return _ballsMap.clone();
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int x = 0; x < _length; x++) {
            for (int y = 0; y < _length; y++) {
                if (_ballsMap[x][y] == null) {
                    stringBuilder.append(StringConstants.DoubleSpace);
                } else {
                    stringBuilder.append(_ballsMap[x][y]);
                }
                stringBuilder.append(StringConstants.DoubleSpace);
            }
            stringBuilder.append(StringConstants.NewLine);
        }
        return stringBuilder.toString();
    }
}