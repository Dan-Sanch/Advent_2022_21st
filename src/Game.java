public interface Game {
    boolean isOver();

    int scoreOfPlayer(int player);

    int getWinningPlayer();

    int getLosingPlayer();

    void playRound();
}
