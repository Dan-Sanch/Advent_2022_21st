public class DiracDiceGame {

    public static long instantiatedGames = 0;
    private static final int GAME_OVER_SCORE = 21;
    private static final int TRACK_LENGTH = 10;
    private int player1Score = 0;
    private int player2Score = 0;
    private final int player1Position;
    private final int player2Position;

    public DiracDiceGame(int player1StartPosition, int player2StartPosition) {
        player1Position = player1StartPosition;
        player2Position = player2StartPosition;

        instantiatedGames++;
    }

    private DiracDiceGame(int p1StartPosition, int p2StartPosition, int p1StartScore, int p2StartScore) {
        this(p1StartPosition, p2StartPosition);
        this.player1Score = p1StartScore;
        this.player2Score = p2StartScore;
    }

    public DiracGameResults playGame() {
        return playTurnOfPlayer(1);
    }

    private DiracGameResults playTurnOfPlayer(int turnPlayer) {
        DiracGameResults gameResults = new DiracGameResults();

        for (DiceRolls diceRoll : DiceRolls.values()) {
            gameResults.add(
                    resolveTurn(turnPlayer, diceRoll)
            );
        }

        return gameResults;
    }

    private DiracGameResults resolveTurn(int turnPlayer, DiceRolls diceRoll) {
        DiracGameResults rollsResults;

        int playerNewScore = calculateScoreForPlayer(turnPlayer, diceRoll.value);

        if (playerNewScore >= GAME_OVER_SCORE) {
            // Game won, no more turns played
            rollsResults = new DiracGameResults(turnPlayer, diceRoll.dicePermutations);
        }
        else {
            // Game continues
            rollsResults = endTurn(turnPlayer, diceRoll);
        }

        return rollsResults;
    }

    private DiracGameResults endTurn(int currentPlayer, DiceRolls playerRoll) {
        int newScore = calculateScoreForPlayer(currentPlayer, playerRoll.value);
        int newPosition = calculateNewPositionForPlayer(currentPlayer, playerRoll.value);
        DiracDiceGame newGame = setupNextUniverse(currentPlayer, newScore, newPosition);

        DiracGameResults nextResults = newGame.playTurnOfPlayer(getNextPlayer(currentPlayer));
        nextResults.multiply(playerRoll.dicePermutations);
        return nextResults;
    }

    private DiracDiceGame setupNextUniverse(int currentPlayer, int newScore, int newPosition) {
        // Set up a "new" game, with updated state
        int nextP1Pos = currentPlayer ==1? newPosition : player1Position;
        int nextP2Pos = currentPlayer ==2? newPosition : player2Position;
        int nextP1Score = currentPlayer ==1? newScore : player1Score;
        int nextP2Score = currentPlayer ==2? newScore : player2Score;
        return new DiracDiceGame(nextP1Pos, nextP2Pos, nextP1Score, nextP2Score);
    }

    private static int getNextPlayer(int turnPlayer) {
        return turnPlayer == 1 ? 2 : 1;
    }

    private int calculateScoreForPlayer(int turnPlayer, int rollTotal) {
        int playerNewPosition = calculateNewPositionForPlayer(turnPlayer, rollTotal);
        int playerScore = turnPlayer==1? player1Score:player2Score;
        return playerScore + playerNewPosition;
    }

    private int calculateNewPositionForPlayer(int turnPlayer, int rollTotal) {
        int playerPosition = turnPlayer ==1? player1Position:player2Position;
        return calcNewPosition(playerPosition, rollTotal);
    }

    private int calcNewPosition(int currentPosition, int movedSpaces) {
        int newPosition = (currentPosition+movedSpaces) % TRACK_LENGTH;
        if (newPosition==0)
            newPosition = TRACK_LENGTH;

        return newPosition;
    }

    // Note: Enum only works for rolling three 3-sided dice
    private enum DiceRolls {
        THREE(3,1),
        FOUR(4,3),
        FIVE(5,6),
        SIX(6,7),
        SEVEN(7,6),
        EIGHT(8,3),
        NINE(9,1);

        public final int value;
        public final int dicePermutations;

        DiceRolls(int value, int permutations) {
            this.value = value;
            this.dicePermutations = permutations;
        }
    }
}