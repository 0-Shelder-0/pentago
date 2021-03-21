package entities;

import constants.StringConstants;

public class GameMap {
    private final int _mapLength;
    private final int _quadrantLength;
    private final Quadrant[][] _quadrantsMap;

    public GameMap() {
        this(2, 3);
    }

    public GameMap(int mapLength, int quadrantLength) {
        _mapLength = mapLength;
        _quadrantLength = quadrantLength;
        _quadrantsMap = new Quadrant[_mapLength][_mapLength];
        for (int x = 0; x < _mapLength; x++) {
            for (int y = 0; y < _mapLength; y++) {
                _quadrantsMap[x][y] = new Quadrant(quadrantLength);
            }
        }
    }

    public int getMapLength() {
        return _mapLength;
    }

    public int getQuadrantLength() {
        return _quadrantLength;
    }

    public boolean inMap(int x, int y) {
        return x >= 0 && x < _mapLength && y >= 0 && y < _mapLength;
    }

    public Ball getCell(int xQuadrant, int yQuadrant, int xBall, int yBall) throws Exception {
        if (!inMap(xQuadrant, yQuadrant)) {
            throw new Exception(String.format(StringConstants.ParametersIsIncorrectFormat, xQuadrant, yQuadrant));
        }
        return _quadrantsMap[xQuadrant][yQuadrant].getCell(xBall, yBall);
    }

    public void setCell(int xQuadrant, int yQuadrant, int xBall, int yBall, Ball ball) throws Exception {
        if (!inMap(xQuadrant, yQuadrant)) {
            throw new Exception(String.format(StringConstants.ParametersIsIncorrectFormat, xQuadrant, yQuadrant));
        }
        _quadrantsMap[xQuadrant][yQuadrant].setCell(xBall, yBall, ball);
    }

    public void rotate(int xQuadrant, int yQuadrant, QuadrantRotate rotate) throws Exception {
        if (!inMap(xQuadrant, yQuadrant)) {
            throw new Exception(String.format(StringConstants.ParametersIsIncorrectFormat, xQuadrant, yQuadrant));
        }
        _quadrantsMap[xQuadrant][yQuadrant].rotate(rotate);
    }

    public Quadrant[][] getQuadrantsMap() {
        return _quadrantsMap.clone();
    }

    public Ball[][] getBallsMap() throws Exception {
        int length = _mapLength * _quadrantLength;
        Ball[][] ballsMap = new Ball[length][length];
        for (int xQuadrant = 0; xQuadrant < _mapLength; xQuadrant++) {
            for (int xBall = 0; xBall < _quadrantLength; xBall++) {
                for (int yQuadrant = 0; yQuadrant < _mapLength; yQuadrant++) {
                    for (int yBall = 0; yBall < _quadrantLength; yBall++) {
                        ballsMap[_quadrantLength * xQuadrant + xBall][_quadrantLength * yQuadrant + yBall]
                                = getCell(xQuadrant, yQuadrant, xBall, yBall);
                    }
                }
            }
        }

        return ballsMap;
    }
}
