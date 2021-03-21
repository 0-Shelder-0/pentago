package console;

import constants.StringConstants;
import entities.Ball;
import entities.GameMap;
import entities.Quadrant;

public class Drawer {
    public static void drawInConsole(GameMap gameMap) {
        Quadrant[][] quadrantsMap = gameMap.getQuadrantsMap();
        int mapLength = gameMap.getMapLength();
        int quadrantLength = gameMap.getQuadrantLength();
        Ball[][][][] ballsMap = getBallsMap(quadrantsMap, mapLength);
        StringBuilder stringBuilder = getStringBuilder(mapLength, quadrantLength, ballsMap);

        System.out.println(stringBuilder.toString());
    }

    private static Ball[][][][] getBallsMap(Quadrant[][] quadrantsMap, int mapLength) {
        Ball[][][][] ballsMap = new Ball[mapLength][mapLength][][];

        for (int xQuadrant = 0; xQuadrant < mapLength; xQuadrant++) {
            for (int yQuadrant = 0; yQuadrant < mapLength; yQuadrant++) {
                ballsMap[xQuadrant][yQuadrant] = quadrantsMap[xQuadrant][yQuadrant].getBallsMap();
            }
        }
        return ballsMap;
    }

    private static StringBuilder getStringBuilder(int mapLength, int quadrantLength, Ball[][][][] ballsMap) {
        StringBuilder stringBuilder = new StringBuilder();
        fillStartLine(stringBuilder, mapLength, quadrantLength);

        for (int xQuadrant = 0; xQuadrant < mapLength; xQuadrant++) {
            for (int xBall = 0; xBall < quadrantLength; xBall++) {
                fillStartQuadrantLine(stringBuilder, mapLength, quadrantLength, xQuadrant, xBall);
                for (int yQuadrant = 0; yQuadrant < mapLength; yQuadrant++) {
                    for (int yBall = 0; yBall < quadrantLength; yBall++) {
                        if (ballsMap[xQuadrant][yQuadrant][xBall][yBall] != null) {
                            String ballStr = ballsMap[xQuadrant][yQuadrant][xBall][yBall].toString();
                            stringBuilder.append(ballStr);
                        } else {
                            stringBuilder.append(StringConstants.EmptyCell);
                        }
                        if (yBall != quadrantLength - 1 || yQuadrant != mapLength - 1)
                            stringBuilder.append(StringConstants.DoubleSpace);
                    }
                }
                fillEndQuadrantLine(stringBuilder, mapLength, quadrantLength, xQuadrant, xBall);
            }
        }

        fillEndLine(stringBuilder, mapLength, quadrantLength);

        return stringBuilder;
    }

    private static void fillStartQuadrantLine(StringBuilder stringBuilder,
                                              int mapLength,
                                              int quadrantLength,
                                              int xQuadrant,
                                              int xBall) {
        String startQuadrant = getStartOrEndQuadrantLine(mapLength,
                                                         quadrantLength,
                                                         xQuadrant,
                                                         xBall,
                                                         StringConstants.FrameLeftTop,
                                                         StringConstants.FrameLeftBottom);

        stringBuilder.append(startQuadrant);
        stringBuilder.append(StringConstants.Space);
    }

    private static void fillEndQuadrantLine(StringBuilder stringBuilder,
                                            int mapLength,
                                            int quadrantLength,
                                            int xQuadrant,
                                            int xBall) {
        stringBuilder.append(StringConstants.Space);
        String endQuadrant = getStartOrEndQuadrantLine(mapLength,
                                                       quadrantLength,
                                                       xQuadrant,
                                                       xBall,
                                                       StringConstants.FrameRightTop,
                                                       StringConstants.FrameRightBottom);

        stringBuilder.append(endQuadrant);
        stringBuilder.append(StringConstants.NewLine);
    }

    private static String getStartOrEndQuadrantLine(int mapLength,
                                                    int quadrantLength,
                                                    int xQuadrant,
                                                    int xBall,
                                                    String frameTop,
                                                    String frameBottom) {
        if (xBall == 0 && xQuadrant != 0) {
            return frameTop;
        } else if (xBall == quadrantLength - 1 && xQuadrant != mapLength - 1) {
            return frameBottom;
        } else {
            return StringConstants.Frame;
        }
    }

    private static void fillStartLine(StringBuilder stringBuilder, int mapLength, int quadrantLength) {
        fillLine(stringBuilder,
                 mapLength,
                 quadrantLength,
                 StringConstants.FrameLeftTop,
                 StringConstants.FrameLineTopSplitter,
                 StringConstants.FrameRightTop);

        stringBuilder.append(StringConstants.NewLine);
    }

    private static void fillEndLine(StringBuilder stringBuilder, int mapLength, int quadrantLength) {
        fillLine(stringBuilder,
                 mapLength,
                 quadrantLength,
                 StringConstants.FrameLeftBottom,
                 StringConstants.FrameLineBottomSplitter,
                 StringConstants.FrameRightBottom);
    }

    private static void fillLine(StringBuilder stringBuilder,
                                 int mapLength,
                                 int quadrantLength,
                                 String startLine,
                                 String splitterLine,
                                 String endLine) {
        stringBuilder.append(startLine);

        for (int quadrantNumber = 0; quadrantNumber < mapLength; quadrantNumber++) {
            stringBuilder.append(StringConstants.FrameLine.repeat(Math.max(0, quadrantLength * 3)));
            if (quadrantNumber == 0 && quadrantLength > 2) {
                stringBuilder.append(StringConstants.FrameLine);
            }
            if (quadrantNumber != mapLength - 1) {
                stringBuilder.append(splitterLine);
            }
        }

        stringBuilder.append(endLine);
    }
}