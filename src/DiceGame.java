public class DiceGame implements Game {
    private static final int GAME_OVER_SCORE = 1000;
    private static final int TRACK_LENGTH = 10;
    private int player1Score = 0;
    private int player2Score = 0;
    private int player1Position;
    private int player2Position;
    // Note for part two: the "die" behavior is a red herring. Is not the die that has to change, but the game itself
    private Die die;

    public DiceGame(int player1StartPosition, int player2StartPosition, Die useDie) {
        player1Position = player1StartPosition;
        player2Position = player2StartPosition;
        die = useDie;
    }

    @Override
    public boolean isOver() {
        return scoreOfPlayer(1) >= GAME_OVER_SCORE
            || scoreOfPlayer(2) >= GAME_OVER_SCORE;
    }

    @Override
    public int scoreOfPlayer(int player) {
        return switch (player) {
            case 1 -> player1Score;
            case 2 -> player2Score;
            default -> -1;
        };
    }

    @Override
    public int getWinningPlayer() {
        return scoreOfPlayer(1) > scoreOfPlayer(2)? 1:2;
    }
    @Override
    public int getLosingPlayer() {
        return getWinningPlayer()==1? 2:1;
    }

    @Override
    public void playRound() {
        playPlayer1();
        if (isOver())
            return;
        playPlayer2();
    }

    private void playPlayer1() {
        int movedSpaces = die.roll() + die.roll() + die.roll();
        player1Position = calcNewPosition(player1Position, movedSpaces);
        player1Score += player1Position;
    }
    private void playPlayer2() {
        int movedSpaces = die.roll() + die.roll() + die.roll();
        player2Position = calcNewPosition(player2Position, movedSpaces);
        player2Score += player2Position;
    }

    private int calcNewPosition(int currentPosition, int movedSpaces) {
        int newPosition = (currentPosition+movedSpaces) % TRACK_LENGTH;
        if (newPosition==0)
            newPosition = TRACK_LENGTH;

        return newPosition;
    }
}
