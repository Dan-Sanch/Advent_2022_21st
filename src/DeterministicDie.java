public class DeterministicDie implements Die {

    private int nextRoll =0;
    private final int maxValue;
    private int rolledTimes = 0;

    public DeterministicDie(int faces) {
        this.maxValue = faces;
    }

    @Override
    public int roll() {
        rolledTimes++;
        // The die will only roll from 1 to the max allowed number. Any higher and it resets.
        if (++nextRoll > maxValue)
            nextRoll = 1;

        return nextRoll;
    }

    @Override
    public int getTimesRolled() {
        return rolledTimes;
    }
}
