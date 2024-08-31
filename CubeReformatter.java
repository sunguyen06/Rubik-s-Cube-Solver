package BACKEND;

import java.util.Scanner;

public class CubeReformatter {

    public static String finalCube(String[][][] input) {
        String output = processCubeNet(input);
        output = reformatString(output);
        return output;
    }

    public static String processCubeNet(String[][][] cubeNet) {
        StringBuilder output = new StringBuilder();

        for (int face = 0; face < 6; face++) {
            for (int index = 0; index < 3; index++) {
                for (int order = 0; order < 3; order++) {
                    output.append(cubeNet[face][index][order]);
                }
            }
        }
    
        return output.toString();
    }

    public static String swap(String str, int i, int j) {
        StringBuilder sb = new StringBuilder(str);
        int length = i;
        while (i < length + 3) {
            sb.setCharAt(i, str.charAt(j));
            sb.setCharAt(j, str.charAt(i));
            i++;
            j++;
        }

        return sb.toString();
    }

    public static String swapFace(String str, int i, int j) {
        StringBuilder sb = new StringBuilder(str);
        int length = i;
        while (i < length + 9) {
            sb.setCharAt(i, str.charAt(j));
            sb.setCharAt(j, str.charAt(i));
            i++;
            j++;
        }
        
        return sb.toString();
    }

    public static String reformatString(String input) {
        input = reformatCube(input);
        input = swap(input, 4*3, 7*3);
        input = swap(input, 5*3, 11*3);
        input = swap(input, 6*3, 12*3);
        input = swap(input, 6*3, 7*3);
        input = swap(input, 7*3, 8*3);
        input = swap(input, 9*3, 11*3);
        input = swap(input, 10*3, 13*3);
        input = swap(input, 10*3, 11*3);
        input = swapFace(input, 1*9, 3*9);
        input = swapFace(input, 3*9, 4*9);
        input = swapFace(input, 3*9, 5*9);
        return input;
    }

    public static String reformatCube(String cube) {
        String output = cube.replaceAll("1", "U"); 
        output = output.replaceAll("5", "B");
        output = output.replaceAll("3", "F");
        output = output.replaceAll("2", "L");
        output = output.replaceAll("6", "D");
        output = output.replaceAll("4", "R");   
        return output;  
    }
}
