package dndroller;

import java.util.Random;

public class Roller {
    private static final Random random = new Random();

    private static final int NUM_DICE = 0, DIE_VALUE = 1;

    public static int[] parseRollEntry(String entry) {
        int[] entries = new int[2];
        String[] entryStrs = entry.split("d");

        if (entryStrs.length != 2) {
            throw new IllegalArgumentException("Sorry, " + entry + " is not a valid dice roll!");
        }

        for (int i = 0; i < 2; i++) {
            if (!entryStrs[i].isEmpty()) {
                try {
                    entries[i] = Integer.parseInt(entryStrs[i]);

                    if (entries[i] <= 0) {
                        entries[i] = 1;
                    }
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("Sorry, " + entry
                            + " is not a valid dice roll!");
                }
            }
        }

        return entries;
    }

    public static String[] rollResult(String partyName, String rollEntry) {
        String[] out;
        int[] roll = parseRollEntry(rollEntry);

        final int VERBOSE_CUTOFF = 20;

        if (roll[NUM_DICE] == 0) {
            roll[NUM_DICE] = 1;
        }

        int rounds;
        if (roll[NUM_DICE] > VERBOSE_CUTOFF) {
            out = new String[2];
            rounds = roll[NUM_DICE];
        } else {
            out = new String[roll[NUM_DICE] + 2];
            rounds = roll[NUM_DICE];
        }

        out[0] = partyName + " rolled " + rollEntry;
        int total = 0;
        for (int i = 1; i <= rounds; i++) {
            int nextRoll = rollInt(roll[1]);
            if (roll[NUM_DICE] <= VERBOSE_CUTOFF) {
                out[i] = String.format("Roll %d: d%d -> %d", i, roll[DIE_VALUE], nextRoll);
            }
            total += nextRoll;
        }

        out[out.length - 1] = "Total: " + total;

        return out;
    }

    public static int roll(int[] roll) {
        if (roll.length != 2) {
            StringBuilder rollStr = new StringBuilder("{ ");
            for (int i = 0; i < roll.length - 1; i++) {
                rollStr.append(roll[i]).append(", ");
            }
            rollStr.append(roll[roll.length - 1]).append(" }");

            throw new IllegalArgumentException("Illegal roll entry: " + rollStr);
        }

        int total = 0;
        for (int i = 0; i < roll[NUM_DICE]; i++) {
            total += random.nextInt(roll[DIE_VALUE]) + 1;
        }

        return total;
    }

    public static int rollInt(int bound) {
        if (bound <= 0) {
            throw new IllegalArgumentException("Negative bound:" + bound);
        }

        return random.nextInt(bound) + 1;
    }

}
