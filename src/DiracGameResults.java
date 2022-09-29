class DiracGameResults {
    private long p1Wins = 0;
    private long p2Wins = 0;

    public DiracGameResults(){}

    public DiracGameResults(int playerId, int initialScore) {
        switch (playerId) {
            case 1 -> p1Wins += initialScore;
            case 2 -> p2Wins += initialScore;
        };
    }

    public long winsForPlayer(int playerId) {
        return switch (playerId) {
            case 1 -> p1Wins;
            case 2 -> p2Wins;
            default -> -1;
        };
    }

    public void add(DiracGameResults addResults) {
        p1Wins += addResults.p1Wins;
        p2Wins += addResults.p2Wins;
    }

    public void multiply(int factor) {
        p1Wins *= factor;
        p2Wins *= factor;
    }

    public int playerWithMoreWins() {
        return winsForPlayer(1) > winsForPlayer(2)? 1:2;
    }
}
