import utils.FileReaderTools;

import java.util.stream.IntStream;

public class Main {
    private static final String INPUT_FILE_NAME = "resources\\dice game input.txt";

    public static void main(String[] args) {
        String[] linesArray = FileReaderTools.readFileAsArray(INPUT_FILE_NAME);

        int player1StarterPosition = parseStarterPosition(linesArray[0]);
        int player2StarterPosition = parseStarterPosition(linesArray[1]);
        printLine("Player 1 starting position: %d", player1StarterPosition);
        printLine("Player 2 starting position: %d", player2StarterPosition);

        partOne(player1StarterPosition, player2StarterPosition);
        printLine("-------------");
        partTwo(player1StarterPosition, player2StarterPosition);
    }

    private static void partOne(int player1StartPosition, int player2StartPosition) {
        Die useDie = new DeterministicDie(100);
        Game game = new DiceGame(player1StartPosition, player2StartPosition, useDie);
        while (!game.isOver()) {
            game.playRound();
        }
        long resultScore = (long)
                game.scoreOfPlayer(game.getLosingPlayer()) *
                useDie.getTimesRolled();

        printLine("Result score: %d", resultScore);
    }

    private static void partTwo(int player1StartPosition, int player2StartPosition) {
        DiracDiceGame game = new DiracDiceGame(player1StartPosition, player2StartPosition);
        DiracGameResults results = game.playGame();

        int winnerPlayer = results.playerWithMoreWins();
        printLine("Total universes: %d", results.winsForPlayer(1) + results.winsForPlayer(2));
        printLine("Games won by player 1: %d", results.winsForPlayer(1));
        printLine("Games won by player 2: %d", results.winsForPlayer(2));
//        printLine("Instantiated games: %d", DiracDiceGame.instantiatedGames);
    }

    private static int parseStarterPosition(String s) {
        String[] tokens = s.split(" ");
        int positionIndex = IntStream.range(0, tokens.length)
                .filter(i -> "position:".equals(tokens[i]))
                .findFirst()
                .orElse(-2); // No element found

        return Integer.parseInt(tokens[++positionIndex]);
    }

    // ****** Utility code ********* //
    private static int dbg_indentationLevel = 0;
    private static void printLine(String formatString, Object... args) {
        String indentation = "\t".repeat(dbg_indentationLevel);
        System.out.printf(indentation+formatString+"\n", args);
    }
}
