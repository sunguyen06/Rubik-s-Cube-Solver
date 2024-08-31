package BACKEND;

public class RubiksCubeSolver {
    public static String findShortSolution(String scrambledCube) {
        //Find shorter solutions (try more probes even a solution has already been found)
        //In this example, we try AT LEAST 10000 phase2 probes to find shorter solutions.
        Search.init();
        String result = new Search().solution(scrambledCube, 21, 100000000, 10000, 0);
        return result;
    }

// Full initialization, which will take about 200ms.
// The solver will be about 5x~10x slower without full initialization.

/** prepare scrambledCube as
 *
 *             |************|
 *             |*U1**U2**U3*|
 *             |************|
 *             |*U4**U5**U6*|
 *             |************|
 *             |*U7**U8**U9*|
 *             |************|
 * ************|************|************|************|
 * *L1**L2**L3*|*F1**F2**F3*|*R1**R2**R3*|*B1**B2**B3*|
 * ************|************|************|************|
 * *L4**L5**L6*|*F4**F5**F6*|*R4**R5**R6*|*B4**B5**B6*|
 * ************|************|************|************|
 * *L7**L8**L9*|*F7**F8**F9*|*R7**R8**R9*|*B7**B8**B9*|
 * ************|************|************|************|
 *             |************|
 *             |*D1**D2**D3*|
 *             |************|
 *             |*D4**D5**D6*|
 *             |************|
 *             |*D7**D8**D9*|
 *             |************|
 *
 * -> U1 U2 ... U9 R1 ... R9 F1 ... F9 D1 ... D9 L1 ... L9 B1 ... B9
 */


/* Scanner scanner = new Scanner(System.in);
String[][][] cubeNet = new String[6][3][3];

// Reading the cube net input
for (int face = 0; face < 6; face++) {
    for (int i = 0; i < 3; i++) {
        for (int j = 0; j < 3; j++) {
            cubeNet[face][i][j] = scanner.next();
        }
    }
}

// Processing and displaying the cube net output
scanner.close();

String scrambledCube = CubeReformatter.finalCube(cubeNet);

System.out.println(findShortSolution(scrambledCube));*/

}
