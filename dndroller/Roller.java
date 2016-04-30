package dndroller;

import java.util.Random;

public class Roller {
    private static final Random random = new Random();

    private static final int NUM_DICE = 0, DIE_VALUE = 1, MOD_VALUE = 2;
    private static final int PRE_MOD = 0, POST_MOD = 1;

    public static int[] parseRollEntry(String entry) {
        String[] entryStrs = new String[3];

        int index = entry.indexOf("d");
        if (index == -1) {
            throw new IllegalArgumentException("Sorry, " + entry + " is not a valid dice roll!");
        } else {
            entryStrs[NUM_DICE] = entry.substring(0, index);
            entryStrs[DIE_VALUE] = entry.substring(index + 1);
        }

        if (index != -1 && entryStrs[1].contains("+")) {
            String temp = entryStrs[1];
            index = temp.indexOf("+");
            if (index != -1) {
                entryStrs[DIE_VALUE] = temp.substring(0, index);
                entryStrs[MOD_VALUE] = temp.substring(index + 1);
            } else {
                entryStrs[MOD_VALUE] = "";
            }
        } else if (entryStrs[1].contains("-")) {
            String temp = entryStrs[1];
            index = temp.indexOf("-");
            if (index != -1) {
                entryStrs[DIE_VALUE] = temp.substring(0, index);
                entryStrs[MOD_VALUE] = temp.substring(index);
            } else {
                entryStrs[MOD_VALUE] = "";
            }
        } else {
            entryStrs[MOD_VALUE] = "";
        }

        int[] entries = new int[3];

        for (int i = 0; i < 3; i++) {
            if (!entryStrs[i].isEmpty()) {
                try {
                    entries[i] = Integer.parseInt(entryStrs[i]);
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("Sorry, " + entry
                            + " is not a valid dice roll!");
                }
            } else {
                entries[i] = 0;
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
            int[] nextRoll = rollInt(roll[DIE_VALUE], roll[MOD_VALUE]);
            if (roll[NUM_DICE] <= VERBOSE_CUTOFF) {
                if (roll[MOD_VALUE] > 0) {
                    out[i] = String.format("Roll %d: d%d+%d -> %d + %d (%d)", i, roll[DIE_VALUE],
                                roll[MOD_VALUE], nextRoll[PRE_MOD], roll[MOD_VALUE], nextRoll[POST_MOD]);
                } else if (roll[MOD_VALUE] < 0) {
                    out[i] = String.format("Roll %d: d%d%d -> %d%d (%d)", i, roll[DIE_VALUE],
                                roll[MOD_VALUE], nextRoll[PRE_MOD], roll[MOD_VALUE], nextRoll[POST_MOD]);
                } else {
                    out[i] = String.format("Roll %d: d%d -> %d", i, roll[DIE_VALUE], nextRoll[POST_MOD]);
                }
            }
            total += nextRoll[POST_MOD];
        }

        out[out.length - 1] = "Total: " + total;

        return out;
    }

    public static int roll(int[] roll) {
        if (roll.length != 3) {
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
        total += roll[MOD_VALUE];

        if (total < 0) {
            total = 0;
        }

        return total;
    }

    public static int[] rollInt(int bound, int modifier) {
        if (bound <= 0) {
            throw new IllegalArgumentException("Negative bound:" + bound);
        }

        int[] roll = new int[2];

        roll[PRE_MOD] = random.nextInt(bound) + 1;
        roll[POST_MOD] = roll[PRE_MOD] + modifier;
        if (roll[POST_MOD] < 0) {
            roll[POST_MOD] = 0;
        }

        return roll;
    }

}
