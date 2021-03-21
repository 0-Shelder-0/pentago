package console;

import constants.StringConstants;
import entities.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CommandExecutor {

    public static boolean execute(GameMap gameMap, CommandResult commandResult, Ball ball) {
        try {
            if (commandResult.commandType == CommandType.Set) {
                set(gameMap, ball, commandResult.command);
            } else if (commandResult.commandType == CommandType.Rotate) {
                rotate(gameMap, commandResult.command);
            }
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    private static void set(GameMap gameMap, Ball ball, String command) throws Exception {
        List<Integer> coordinates = Arrays.stream(command.split(StringConstants.Space))
                                          .map(Integer::parseInt)
                                          .collect(Collectors.toList());
        int xQuadrant = coordinates.get(0);
        int yQuadrant = coordinates.get(1);
        int xBall = coordinates.get(2);
        int yBall = coordinates.get(3);
        if (gameMap.getCell(xQuadrant, yQuadrant, xBall, yBall) != null) {
            throw new Exception();
        }

        gameMap.setCell(xQuadrant, yQuadrant, xBall, yBall, ball);
    }


    private static void rotate(GameMap gameMap, String command) throws Exception {
        String[] parameterList = command.split(StringConstants.Space);
        int xQuadrant = Integer.parseInt(parameterList[0]);
        int yQuadrant = Integer.parseInt(parameterList[1]);
        char rotateChar = parameterList[2].charAt(0);

        QuadrantRotate rotate = QuadrantRotate.Unknown;
        if (rotateChar == 'r' || rotateChar == 'R') {
            rotate = QuadrantRotate.Right;
        } else if (rotateChar == 'l' || rotateChar == 'L') {
            rotate = QuadrantRotate.Left;
        }
        gameMap.rotate(xQuadrant, yQuadrant, rotate);
    }
}
