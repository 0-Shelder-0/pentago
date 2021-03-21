import color.BallColor;
import console.CommandExecutor;
import console.CommandReader;
import console.Drawer;
import constants.StringConstants;
import entities.*;
import interfaces.TriFunction;

import java.util.Arrays;
import java.util.HashSet;

public class Application {
    private static GameStatus _gameStatus;

    public static void run(int numberOfPlayers) throws Exception {
        _gameStatus = GameStatus.InProcess;
        int ballIndex = 0;
        GameMap gameMap = new GameMap();
        BallColor[] ballColors = getBallColors(numberOfPlayers);

        Drawer.drawInConsole(gameMap);

        while (_gameStatus != GameStatus.Win && _gameStatus != GameStatus.Draw) {

            makeMove(ballIndex, gameMap, ballColors, StringConstants.SetCommandHintFormat, CommandType.Set);
            if (_gameStatus == GameStatus.Win || _gameStatus == GameStatus.Draw) {
                break;
            }
            makeMove(ballIndex, gameMap, ballColors, StringConstants.RotateCommandHint, CommandType.Rotate);

            ballIndex++;
            if (ballIndex >= numberOfPlayers) {
                ballIndex = 0;
            }
        }
    }

    private static void makeMove(int ballIndex,
                                 GameMap gameMap,
                                 BallColor[] ballColors,
                                 String commandHint,
                                 CommandType commandType) throws Exception {
        Drawer.drawInConsole(gameMap);
        System.out.print(String.format(commandHint, ballColors[ballIndex].toString().toLowerCase()));
        executeCommand(gameMap, ballColors, commandType, ballIndex);
        updateGameStatus(gameMap);
    }

    private static void executeCommand(GameMap gameMap,
                                       BallColor[] ballColors,
                                       CommandType commandType,
                                       int ballIndex) {
        boolean isExecute = false;
        while (!isExecute) {
            CommandResult commandResult = CommandReader.readCommand(commandType);
            while (commandResult.commandType == CommandType.Unknown) {
                printException(commandResult, commandType);
                commandResult = CommandReader.readCommand(commandType);
            }

            switch (commandResult.commandType) {
                case Set -> isExecute = CommandExecutor.execute(gameMap,
                                                                commandResult,
                                                                new Ball(ballColors[ballIndex]));
                case Rotate -> isExecute = CommandExecutor.execute(gameMap, commandResult, null);
            }
            if (!isExecute) {
                printException(commandResult, commandType);
            }
        }
    }

    private static void printException(CommandResult commandResult, CommandType expectedCommand) {
        System.out.println(String.format(StringConstants.CommandIsIncorrectFormat, commandResult.command));
        if (expectedCommand == CommandType.Set) {
            System.out.println(StringConstants.SetCommandFormat);
        } else if (expectedCommand == CommandType.Rotate) {
            System.out.println(StringConstants.RotateCommandFormat);
        }
    }

    private static BallColor[] getBallColors(int numberOfPlayers) {

        if (numberOfPlayers == 2) {
            return new BallColor[]{BallColor.White, BallColor.Black};
        }
        BallColor[] ballColors = Arrays.stream(BallColor.values())
                                       .skip(1)
                                       .limit(numberOfPlayers)
                                       .toArray(BallColor[]::new);
        return ballColors;
    }

    private static void updateGameStatus(GameMap gameMap) throws Exception {
        Ball[][] ballsMap = gameMap.getBallsMap();
        HashSet<BallColor> wins = new HashSet<>();
        checkSide(ballsMap, wins, Application::getRowBall);
        checkSide(ballsMap, wins, Application::getColumnBall);
        checkDiagonals(ballsMap, wins, Application::getLeftTopDiagonalBall);
        checkDiagonals(ballsMap, wins, Application::getLeftBottomDiagonalBall);
        checkDiagonals(ballsMap, wins, Application::getRightTopDiagonalBall);
        checkDiagonals(ballsMap, wins, Application::getRightBottomDiagonalBall);
        if (wins.size() > 0) {
            reportAboutGameStatus(wins);
            Drawer.drawInConsole(gameMap);
        }
    }

    private static void checkSide(Ball[][] ballsMap,
                                  HashSet<BallColor> wins,
                                  TriFunction<Ball[][], Integer, Integer, Ball> getBallFunc) {
        for (int x = 0; x < ballsMap.length; x++) {
            int length = 0;
            BallColor lastColor = null;
            for (int y = 0; y < ballsMap.length; y++) {
                Ball ball = getBallFunc.apply(ballsMap, x, y);
                if (isChangeColor(lastColor, ball)) {
                    length = 1;
                    lastColor = ball.getColor();
                } else if (isNotChangeColor(lastColor, ball)) {
                    length++;
                } else if (ball == null) {
                    length = 0;
                    lastColor = null;
                }
                if (length >= 5) {
                    wins.add(lastColor);
                }
            }
        }
    }

    private static void checkDiagonals(Ball[][] ballsMap,
                                       HashSet<BallColor> wins,
                                       TriFunction<Ball[][], Integer, Integer, Ball> getBallFunc) {
        for (int x = 0; x < ballsMap.length; x++) {
            int length = 0;
            BallColor lastColor = null;
            for (int y = 0; y < ballsMap.length - x; y++) {
                Ball ball = getBallFunc.apply(ballsMap, x, y);
                if (isChangeColor(lastColor, ball)) {
                    length = 1;
                    lastColor = ball.getColor();
                } else if (isNotChangeColor(lastColor, ball)) {
                    length++;
                } else if (ball == null) {
                    length = 0;
                    lastColor = null;
                }
                if (length >= 5) {
                    wins.add(lastColor);
                }
            }
        }
    }

    private static Ball getRowBall(Ball[][] ballsMap, int x, int y) {
        return ballsMap[x][y];
    }

    private static Ball getColumnBall(Ball[][] ballsMap, int x, int y) {
        return ballsMap[y][x];
    }

    private static Ball getLeftBottomDiagonalBall(Ball[][] ballsMap, int x, int y) {
        return ballsMap[y][x + y];
    }

    private static Ball getRightTopDiagonalBall(Ball[][] ballsMap, int x, int y) {
        return ballsMap[x + y][y];
    }

    private static Ball getRightBottomDiagonalBall(Ball[][] ballsMap, int x, int y) {
        return ballsMap[ballsMap.length - y - 1][x + y];
    }

    private static Ball getLeftTopDiagonalBall(Ball[][] ballsMap, int x, int y) {
        return ballsMap[ballsMap.length - x - y - 1][y];
    }

    private static boolean isChangeColor(BallColor lastColor, Ball ball) {
        return ball != null && (lastColor == null || lastColor != ball.getColor());
    }

    private static boolean isNotChangeColor(BallColor lastColor, Ball ball) {
        return ball != null && lastColor == ball.getColor();
    }

    private static void reportAboutGameStatus(HashSet<BallColor> colors) {
        if (colors.size() == 1) {
            _gameStatus = GameStatus.Win;
            System.out.print(StringConstants.WinStatusMessage);
        } else {
            _gameStatus = GameStatus.Draw;
            System.out.println(StringConstants.DrawStatusMessage);
        }
        for (BallColor color : colors) {
            System.out.println(color);
        }
    }
}
