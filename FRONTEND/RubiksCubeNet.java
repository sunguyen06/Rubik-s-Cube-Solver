package FRONTEND;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.swing.*;

public class RubiksCubeNet {
    protected static final int faceCount = 54;
    public static Color standardBackgroundColor = Color.getHSBColor((float) 0.608, (float) 0.39, (float) 0.99);
    public static String[] blankString = {""};
    public static String[] faceNameList = new String[] {
        "WOBCorner", "WBEdge", "WRBCorner", "WOEdge", "WCenter", "WREdge", "WOGCorner", "WGEdge", "WRGCorner",
        "RGWCorner", "RWEdge", "RBWCorner", "RGEdge", "RCenter", "RBEdge", "RGYCorner", "RYEdge", "RBYCorner",
        "GOWCorner", "GWEdge", "GRWCorner", "GOEdge", "GCenter", "GREdge", "GOYCorner", "GYEdge", "GRYCorner",
        "YOGCorner", "YGEdge", "YRGCorner", "YOEdge", "YCenter", "YREdge", "YOBCorner", "YBEdge", "YRBCorner",
        "OBWCorner", "OWEdge", "OGWCorner", "OBEdge", "OCenter", "OGEdge", "OBYCorner", "OYEdge", "OGYCorner",
        "BRWCorner", "BWEdge", "BOWCorner", "BREdge", "BCenter", "BOEdge", "BRYCorner", "BYEdge", "BOYCorner"};
    
    protected ActionListener application = null;
    protected String windowName;
    protected Window window;
    protected BlockFace[] cubletFaceList = new BlockFace[54];

    public RubiksCubeNet(ActionListener application, String windowName, Window window, boolean isEditable, int centerXCoord, int centerYCoord, int cubletWidth, int cubletHeight) {
        this.application = application;
        this.windowName = windowName;
        this.window = window;

        if (isEditable) {
            buildEditableCubeFace(window, "WHITE", Arrays.copyOfRange(faceNameList, 0, 9), centerXCoord, centerYCoord-(cubletHeight*3), cubletHeight, cubletWidth, windowName);
            buildEditableCubeFace(window, "RED", Arrays.copyOfRange(faceNameList, 9, 18), centerXCoord+(cubletWidth*3), centerYCoord, cubletHeight, cubletWidth, windowName);
            buildEditableCubeFace(window, "GREEN", Arrays.copyOfRange(faceNameList, 18, 27), centerXCoord, centerYCoord, cubletHeight, cubletWidth,  windowName);
            buildEditableCubeFace(window, "YELLOW", Arrays.copyOfRange(faceNameList, 27, 36), centerXCoord, centerYCoord+(cubletHeight*3), cubletHeight, cubletWidth,  windowName);
            buildEditableCubeFace(window, "ORANGE", Arrays.copyOfRange(faceNameList, 36, 45), centerXCoord-(cubletWidth*3), centerYCoord, cubletHeight, cubletWidth,  windowName);
            buildEditableCubeFace(window, "BLUE", Arrays.copyOfRange(faceNameList, 45, 54), centerXCoord+(cubletWidth*6), centerYCoord, cubletHeight, cubletWidth,  windowName);
        }
        else {
            buildNonEditableCubeFace(window, "WHITE", Arrays.copyOfRange(faceNameList, 0, 9), centerXCoord, centerYCoord-(cubletHeight*3), cubletHeight, cubletWidth, windowName);
            buildNonEditableCubeFace(window, "RED", Arrays.copyOfRange(faceNameList, 9, 18), centerXCoord+(cubletWidth*3), centerYCoord, cubletHeight, cubletWidth, windowName);
            buildNonEditableCubeFace(window, "GREEN", Arrays.copyOfRange(faceNameList, 18, 27), centerXCoord, centerYCoord, cubletHeight, cubletWidth,  windowName);
            buildNonEditableCubeFace(window, "YELLOW", Arrays.copyOfRange(faceNameList, 27, 36), centerXCoord, centerYCoord+(cubletHeight*3), cubletHeight, cubletWidth,  windowName);
            buildNonEditableCubeFace(window, "ORANGE", Arrays.copyOfRange(faceNameList, 36, 45), centerXCoord-(cubletWidth*3), centerYCoord, cubletHeight, cubletWidth,  windowName);
            buildNonEditableCubeFace(window, "BLUE", Arrays.copyOfRange(faceNameList, 45, 54), centerXCoord+(cubletWidth*6), centerYCoord, cubletHeight, cubletWidth,  windowName);
        }
    }

    public void buildEditableCubeFace(Window window, String initialColor, String[] id, int centerXCoord, int centerYCoord, int height, int width, String windowName) {
        windowName += " ";
        window.add(new BlockFace(windowName + id[0], centerXCoord-width, centerYCoord-height, height, width, 1, standardBackgroundColor, application, blankString, FontList.titleFont)); // LEFT UP
        window.add(new BlockFace(windowName + id[1], centerXCoord, centerYCoord-height, height, width, 1, standardBackgroundColor, application, blankString, FontList.titleFont)); // UP
        window.add(new BlockFace(windowName + id[2], centerXCoord+width, centerYCoord-height, height, width, 1, standardBackgroundColor, application, blankString, FontList.titleFont)); // RIGHT UP
        window.add(new BlockFace(windowName + id[3], centerXCoord-width, centerYCoord, height, width, 1, standardBackgroundColor, application, blankString, FontList.titleFont)); // LEFT
        window.add(new NonEditableBlockFace(windowName + id[4], centerXCoord, centerYCoord, height, width, 1, standardBackgroundColor, application, blankString, FontList.titleFont, initialColor)); // CENTER
        window.add(new BlockFace(windowName + id[5], centerXCoord+width, centerYCoord, height, width, 1, standardBackgroundColor, application, blankString, FontList.titleFont)); // RIGHT
        window.add(new BlockFace(windowName + id[6], centerXCoord-width, centerYCoord+height, height, width, 1, standardBackgroundColor, application, blankString, FontList.titleFont)); // LEFT DOWN
        window.add(new BlockFace(windowName + id[7], centerXCoord, centerYCoord+height, height, width, 1, standardBackgroundColor, application, blankString, FontList.titleFont)); // DOWN
        window.add(new BlockFace(windowName + id[8], centerXCoord+width, centerYCoord+height, height, width, 1, standardBackgroundColor, application, blankString, FontList.titleFont)); // RIGHT DOWN
    }

    public void buildNonEditableCubeFace(Window window, String initialColor, String[] id, int centerXCoord, int centerYCoord, int height, int width, String windowName) {
        windowName += " ";
        window.add(new NonEditableBlockFace(windowName + id[0], centerXCoord-width, centerYCoord-height, height, width, 1, standardBackgroundColor, application, blankString, FontList.titleFont, initialColor)); // LEFT UP
        window.add(new NonEditableBlockFace(windowName + id[1], centerXCoord, centerYCoord-height, height, width, 1, standardBackgroundColor, application, blankString, FontList.titleFont, initialColor)); // UP
        window.add(new NonEditableBlockFace(windowName + id[2], centerXCoord+width, centerYCoord-height, height, width, 1, standardBackgroundColor, application, blankString, FontList.titleFont, initialColor)); // RIGHT UP
        window.add(new NonEditableBlockFace(windowName + id[3], centerXCoord-width, centerYCoord, height, width, 1, standardBackgroundColor, application, blankString, FontList.titleFont,initialColor)); // LEFT
        window.add(new NonEditableBlockFace(windowName + id[4], centerXCoord, centerYCoord, height, width, 1, standardBackgroundColor, application, blankString, FontList.titleFont, initialColor)); // CENTER
        window.add(new NonEditableBlockFace(windowName + id[5], centerXCoord+width, centerYCoord, height, width, 1, standardBackgroundColor, application, blankString, FontList.titleFont, initialColor)); // RIGHT
        window.add(new NonEditableBlockFace(windowName + id[6], centerXCoord-width, centerYCoord+height, height, width, 1, standardBackgroundColor, application, blankString, FontList.titleFont, initialColor)); // LEFT DOWN
        window.add(new NonEditableBlockFace(windowName + id[7], centerXCoord, centerYCoord+height, height, width, 1, standardBackgroundColor, application, blankString, FontList.titleFont, initialColor)); // DOWN
        window.add(new NonEditableBlockFace(windowName + id[8], centerXCoord+width, centerYCoord+height, height, width, 1, standardBackgroundColor, application, blankString, FontList.titleFont, initialColor)); // RIGHT DOWN
    }
    
    public void loadCublets(HashMap<String, Object> objectDatabase) {
        for (int i = 0; i < faceCount; i++) {
            String id = windowName + " " + faceNameList[i];
            cubletFaceList[i] = (BlockFace) objectDatabase.get(id);
        }
    }

    public String toDataString() {
        String blockSequence = "";
        for (int i = 0; i < faceCount; i++) {
            blockSequence += convertToNum(cubletFaceList[i].getColor());
        }
        return blockSequence;
    }

    public void overwriteCube(String dataString) {
        for (int i = 0; i < faceCount; i++) {
            cubletFaceList[i].updateColor(convertToString(dataString.substring(i, i+1)));
        }
    }
    
    private String convertToNum(String color) {
        if (color == null) {
            return "0";
        }
        switch (color) {
            case "WHITE":
                return "1";
            case "RED":
                return "2";
            case "GREEN":
                return "3";
            case "YELLOW":
                return "4";
            case "ORANGE":
                return "5";
            case "BLUE":
                return "6";
            default:
                return "0";
        }
    }

    private String convertToString(String num) {
        if (num == "0") {
            return "GRAY";
        }
        switch (num) {
            case "1":
                return "WHITE";
            case "2":
                return "RED";
            case "3":
                return "GREEN";
            case "4":
                return "YELLOW";
            case "5":
                return "ORANGE";
            case "6":
                return "BLUE";
            default:
                return "GRAY";

        }
    }

    public static String convertLetterFaceToNum(String sequence) {
        String newString = "";
        for (int i = 0; i < sequence.length(); i++) {
            String character = sequence.substring(i, i+1);
            if (character.equals("U")) {newString += "1";}
            if (character.equals("R")) {newString += "2";}
            if (character.equals("F")) {newString += "3";}
            if (character.equals("D")) {newString += "4";}
            if (character.equals("L")) {newString += "5";}
            if (character.equals("B")) {newString += "6";}
        }
        return newString;
    }

    public void reset() {
        for (int i = 0; i < faceCount; i++) {
            if (i < 9) {
                cubletFaceList[i].updateColor("WHITE");
            }
            else if (i < 18) {
                cubletFaceList[i].updateColor("RED");
            }
            else if (i < 27) {
                cubletFaceList[i].updateColor("GREEN");
            }
            else if (i < 36) {
                cubletFaceList[i].updateColor("YELLOW");
            }
            else if (i < 45) {
                cubletFaceList[i].updateColor("ORANGE");
            }
            else {
                cubletFaceList[i].updateColor("BLUE");
            }
        }
    }
}

class ColorSwatch extends Button {
    protected String color;
    public ColorSwatch(String id, int xPosition, int yPosition, int height, int width, int depth, Color background,
            ActionListener actionListener, String[] textSource, Font font, String color) {
        super(id, xPosition, yPosition, height, width, depth, background, actionListener, textSource, font);
        this.color = color;
        this.type = "ColorSwatch";
        this.background = DisplayElement.StringToColor(color);
        button.setActionCommand("SWITCH CURRENT COLOR TO " + color);
        button.setBackground(this.background);
        button.setOpaque(true);
        button.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
        makeVisible();
    }
}

class BlockFace extends Button {
    protected String color;

    public BlockFace(String id, int xPosition, int yPosition, int height, int width, int depth, Color background,
            ActionListener actionListener, String[] textSource, Font font) {
        super(id, xPosition, yPosition, height, width, depth, background, actionListener, textSource, font);
        this.type = "BlockFace";
        this.color = null;
        this.background = DisplayElement.StringToColor(color);
        button.setBackground(this.background);
        button.setOpaque(true);
        button.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        button.setActionCommand("CHANGE CURRENT COLOR OF BLOCKFACE");
        makeVisible();
    }

    public void updateColor(String color) {
        this.color = color;
        this.background = DisplayElement.StringToColor(color);
        button.setBackground(this.background);
        makeVisible();
    }

    public String getColor() {
        return color;
    }

}

class NonEditableBlockFace extends BlockFace {

    public NonEditableBlockFace(String id, int xPosition, int yPosition, int height, int width, int depth, Color background,
            ActionListener actionListener, String[] textSource, Font font, String color) {
        super(id, xPosition, yPosition, height, width, depth, background, actionListener, textSource, font);
        this.type = "NonEditableBlockFace";
        this.color = color;
        this.background = DisplayElement.StringToColor(color);
        button.setBackground(this.background);
        button.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
        button.setActionCommand("DO NOT CHANGE CURRENT COLOR OF BLOCKFACE");
    }
}