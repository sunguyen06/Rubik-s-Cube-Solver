package BACKEND;

import java.util.Random;

public class CubeScrambler {
    static Random r = new Random();

    static String[] moves = {"Rx", "Lx", "Uy", "Dy", "Fz", "Bz"};
    static String[] directions = {" ", "\' ", "2 "};

    public static String getMove(String move1, String move2) {
        String move = moves[r.nextInt(moves.length)];

        // Checks for redundancy.

        if (move2 == move || isRedundant(move1, move2, move)) {
            return getMove(move1, move2);
        }

        return move;
    }

    public static String getScramble() {
        String scramble = "", move1 = "  ", move2 = "  ", direction = "";

        for(int i = 0; i < 22; i++) {
            String currentMove = getMove(move1, move2);
            direction = directions[r.nextInt(directions.length)];
            scramble += currentMove.charAt(0) + direction;

            move1 = move2;
            move2 = currentMove;
        }

        return scramble;
    }

    public static String getScrambledNet(String scramble) {
        String output = Tools.fromScramble(scramble);
        return output;
    }

    public static boolean isRedundant(String move1, String move2, String move3) {
        if(move2.charAt(1) == move1.charAt(1) && move2.charAt(1) == move3.charAt(1)) {
            return true;
        }
        return false;
    }
}
