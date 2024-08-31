package FRONTEND;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

public class InputValidation {

    protected static final String[] validMoveList = new String[] {"U", "U\'", "U2", "R", "R\'", "R2", 
    "F", "F\'", "F2", "L", "L\'", "L2", "D", "D\'", "D2", "B", "B\'", "B2"};

    public static boolean dateIsValid(String date) {
        if (!isValidNumber(date)) {
            return false;
        }
        if (!isValidLength(date, 8)) {
            return false;
        }
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("ddMMuuuu").withResolverStyle(ResolverStyle.STRICT);
        try {
            LocalDate.parse(date, dateTimeFormatter);
        } catch (DateTimeParseException e) {
            return false;
        }
        return true;
    }

    public static boolean timeIsValid(String minutes, String seconds, String milliseconds) {
        String time = minutes + seconds + milliseconds;
        if (!isValidNumber(time)) {
            return false;
        }        
        if (!isValidLength(time, 7)) {
            return false;
        }
        if (Integer.parseInt(minutes) >= 60 || Integer.parseInt(seconds) >= 60 || Integer.parseInt(milliseconds) >= 1000) {
            return false;
        }
        return true;
    }

    private static boolean isValidNumber(String string) {
        if (!string.matches("[0-9]+")) {
            return false;
        }
        for (int i = 0; i < string.length(); i++) {
            if (Integer.parseInt(string.substring(i)) != 0) {
                return true;
            } 
        }
        return false;
    }

    private static boolean isValidLength(String string, int length) {
        if (string.length() == length) {
            return true;
        }
        else {
            return false;
        }
    }

    private static boolean isValidMove(String move) {
        for (int i = 0; i < validMoveList.length; i++) {
            if (validMoveList[i].equals(move)) {
                return true;
            }
        }
        return false;
    }
    
    public static boolean isValidMoveSequence(String string) {
        String[] moveSequence = string.split(" ");
        for (int i = 0; i < moveSequence.length; i++) {
            if (!isValidMove(moveSequence[i])) {
                return false;
            }
        }
        return true;
    }
}
