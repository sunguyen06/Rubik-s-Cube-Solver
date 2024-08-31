package FRONTEND;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Arrays;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.text.*;

public class DisplayElement {

    public static final boolean onMac = true;
    public static int frameSizeHorizontal;
    public static int frameSizeVertical;

    protected JComponent component;

    protected String type;
    protected String id;

    protected int xPosition;
    protected int yPosition;
    protected int height;
    protected int width;
    protected int depth;

    protected Color background = Color.WHITE;
    public static Color standardBackgroundColor = Color.getHSBColor((float) 0.608, (float) 0.39, (float) 0.99);

    public boolean isVisible() {
        return isVisible;
    }

    protected boolean isVisible;

    public DisplayElement(String id, int xPosition, int yPosition, int height, int width, int depth, Color background) {
        this.id = id;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.height = height;
        this.width = width;
        this.depth = depth;
        this.background = background;
    }

    public void display() {
        component.setBounds(resizeHorizontal(xPosition, frameSizeHorizontal), resizeVertical(yPosition, frameSizeVertical), resizeHorizontal(width, frameSizeHorizontal), resizeVertical(height, frameSizeVertical));
        component.validate();
        component.setVisible(isVisible);
    }

    public int resizeHorizontal(double initial, double frameSize) { // RESIZE ACCORDING TO 1920 
        double ratio = initial/1920;
        return (int) (ratio*frameSize);
    }

    public int resizeVertical(double initial, double frameSize) { // RESIZE ACCORDING TO 1080
        double ratio = initial/1080;
        return (int) (ratio*frameSize);
    } 

    public void makeVisible() {
        isVisible = true;
        display();
    }

    public void makeInvisible() {
        isVisible = false;
        display();
    }
    
    public JComponent getComponent() {
        return component;
    }

    public String getType() {
        return type;
    }

    public String getId() {
        return id;
    }

    public int getxPosition() {
        return xPosition;
    }

    public int getyPosition() {
        return yPosition;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public int getDepth() {
        return depth;
    }

    public void setBackground(Color background) {
        this.background = background;
    }  

    public static Color StringToColor(String color) {
        if (color == null) {
            return Color.GRAY;
        }
        switch (color) {
            case "WHITE":
                return Color.WHITE;
            case "RED":
                return Color.RED;
            case "GREEN":
                return Color.GREEN;
            case "YELLOW":
                return Color.YELLOW;
            case "ORANGE":
                return Color.getHSBColor((float) 0.077, 1, 1);
            case "BLUE":
                return Color.BLUE;
            default:
                System.out.println("COLOR INPUT NOT VALID");
                return Color.GRAY;
        }
    }

    public static void setFrameSizeHorizontal(int frameSizeHorizontal) {
        DisplayElement.frameSizeHorizontal = frameSizeHorizontal;
    }

    public static void setFrameSizeVertical(int frameSizeVertical) {
        DisplayElement.frameSizeVertical = frameSizeVertical;
    }
}

class TextDisplay extends DisplayElement {
    protected JTextArea textArea;
    protected String[] textSource;
    protected Font font;

    public TextDisplay(String id, int xPosition, int yPosition, int height, int width, int depth, Color background, String[] textSource, Font font) {
        super(id, xPosition, yPosition, height, width, depth, background);
        this.type = "TextDisplay";
        this.textSource = textSource;
        this.font = font;
        this.textArea = new JTextArea(textSource[0]);
        this.textArea.setEditable(false);
        this.textArea.setFont(font);
        this.textArea.setBackground(background);
        this.textArea.setLineWrap(true);
        this.textArea.setWrapStyleWord(true);
        this.component = textArea;
        makeVisible();
    }

    public String getText() {
        return textArea.getText();
    }
    public Font getFont() {
        return font;
    }

    public void updateTextDisplay(String newText) {
        this.textSource[0] = newText;
        this.textArea.setText(newText);
        makeVisible();
    }

    @Override
    public String toString() {
        return "TextDisplay [textArea=" + textArea + ", textSource=" + Arrays.toString(textSource) + ", font=" + font
                + "]";
    }
}

class TextFieldLimiter extends PlainDocument {
    protected int charLimit;

    public TextFieldLimiter(int charLimit) {
        super();
        this.charLimit = charLimit;
    }

   public void insertString(int offset, String input, AttributeSet attributeSet) throws BadLocationException {
      if (input == null)
         return;
      if ((getLength() + input.length()) <= charLimit) { // if existing text field length + new input textfield length  less than max, allow insertion
        super.insertString(offset, input, attributeSet);
      }
      else {
      }
   }
}

class EditableTextDisplay extends TextDisplay {

    protected int rows;
    protected int columns;
    public EditableTextDisplay(String id, int xPosition, int yPosition, int height, int width, int depth,
            Color background, String[] textSource, Font font, int rows, int columns) {
        super(id, xPosition, yPosition, height, width, depth, background, textSource, font);
        this.textArea.setEditable(true);
        this.textArea.setAlignmentX(JTextArea.CENTER_ALIGNMENT);
        this.rows = rows;
        this.columns = columns;
        this.textArea.setRows(rows);
        this.textArea.setColumns(columns);
        this.textArea.setLineWrap(true);

        this.textArea.setDocument(new TextFieldLimiter(rows*columns));
        this.textArea.setText(textSource[0]);
        makeVisible();
    }

    public String getText() {
        return textArea.getText();
    }

}

class ImageContainer extends DisplayElement {

    protected JLabel image;
    public static final String libraryPath = "images/";

    public ImageContainer(String id, int xPosition, int yPosition, int height, int width, int depth, Color background, String filePath) throws IOException {
        super(id, xPosition, yPosition, height, width, depth, background);
        this.type = "ImageContainer";
        this.image = new JLabel(createImage(filePath));
        this.component = image;
        makeVisible();
    }

    public ImageIcon createImage(String filePath) throws IOException {
        BufferedImage tempImage = ImageIO.read(getClass().getResource(libraryPath + filePath));
        Image resizedImage = tempImage.getScaledInstance(resizeHorizontal(width, frameSizeHorizontal), resizeVertical(height, frameSizeVertical), Image.SCALE_SMOOTH);
        ImageIcon newImage = new ImageIcon(resizedImage);
        return newImage;
    }
}

class InteractableObject extends DisplayElement {

    protected ActionListener actionListener;

    public InteractableObject(String id, int xPosition, int yPosition, int height, int width, int depth, Color background, ActionListener actionListener) {
        super(id, xPosition, yPosition, height, width, depth, background);
        this.type = "InteractableObject";
        this.actionListener = actionListener;
    }

}

class InteractableTextField extends InteractableObject {

    protected String[] textSource;
    protected Font font;

    public InteractableTextField(String id, int xPosition, int yPosition, int height, int width, int depth, Color background, ActionListener actionListener,
            String[] textSource, Font font) {
        super(id, xPosition, yPosition, height, width, depth, background, actionListener);
        this.type = "InteractableTextField";
        this.textSource = textSource;
        this.font = font;
    }

}

class Button extends InteractableTextField {

    protected JButton button;

    public Button(String id, int xPosition, int yPosition, int height, int width, int depth, Color background, ActionListener actionListener,
            String[] textSource, Font font) {
        super(id, xPosition, yPosition, height, width, depth, background, actionListener, textSource, font);

        this.type = "Button";
        this.button = new JButton(textSource[0]);
        button.setBackground(background);
        button.setFont(font);
        button.addActionListener(actionListener);
        button.setOpaque(true);
        this.component = button;
        makeVisible();
    }

    public JButton getButton() {
        return button;
    }
}

class WindowChangeButton extends Button {
    protected String targetWindow;

    public WindowChangeButton(String id, int xPosition, int yPosition, int height, int width, int depth, Color background, ActionListener actionListener,
            String[] textSource, Font font, String targetWindow) {
        super(id, xPosition, yPosition, height, width, depth, background, actionListener, textSource, font);
        this.type = "WindowChangeButton";
        this.targetWindow = targetWindow;
        button.setActionCommand("CHANGE WINDOW " + targetWindow);
        makeVisible();
    }

}

class ScrambleViewButton extends Button {

    protected String scramble;
    protected int entryPosition;

    public ScrambleViewButton(String id, int xPosition, int yPosition, int height, int width, int depth,
            Color background, ActionListener actionListener, String[] textSource, Font font, int entryPosition, String scramble) {
        super(id, xPosition, yPosition, height, width, depth, background, actionListener, textSource, font);
        this.type = "ScrambleViewButton";
        this.scramble = scramble;
        this.entryPosition = entryPosition;
        button.setActionCommand("LOAD SCRAMBLE VIEW " + String.valueOf(entryPosition) + " WITH SCRAMBLE " + scramble);
        makeVisible();
    }

    public String getScramble() {
        return scramble;
    }

    public void setScramble(String scramble) {
        this.scramble = scramble;
        button.setActionCommand("LOAD SCRAMBLE VIEW " + String.valueOf(entryPosition) + " WITH SCRAMBLE " + scramble);
    }
}

class QuitButton extends Button {

    public QuitButton(String id, int xPosition, int yPosition, int height, int width, int depth, Color background,
            ActionListener actionListener, String[] textSource, Font font) {
        super(id, xPosition, yPosition, height, width, depth, background, actionListener, textSource, font);
        this.type = "QuitButton";
        button.setActionCommand("QUIT");
        makeVisible();
    }

}

class UploadNetButton extends Button {

    public UploadNetButton(String id, int xPosition, int yPosition, int height, int width, int depth,
            Color background, ActionListener actionListener, String[] textSource, Font font) {
        super(id, xPosition, yPosition, height, width, depth, background, actionListener, textSource, font);
        this.type = "UploadNetButton";
        button.setActionCommand("UPLOAD NET");
    }

}

class UploadRunButton extends Button {

    public UploadRunButton(String id, int xPosition, int yPosition, int height, int width, int depth, Color background,
            ActionListener actionListener, String[] textSource, Font font) {
        super(id, xPosition, yPosition, height, width, depth, background, actionListener, textSource, font);
        this.type = "UploadRunButton";
        button.setActionCommand("UPLOAD RUN");
    }

}

class ScrambleGeneratorButton extends Button {

    public ScrambleGeneratorButton(String id, int xPosition, int yPosition, int height, int width, int depth,
            Color background, ActionListener actionListener, String[] textSource, Font font) {
        super(id, xPosition, yPosition, height, width, depth, background, actionListener, textSource, font);
        this.type = "ScrambleGeneratorButton";
        button.setActionCommand("GENERATE SCRAMBLE");
    }

}

class ChangeDatabasePageButton extends Button {
    
    protected boolean isScrollForward;

    public ChangeDatabasePageButton(String id, int xPosition, int yPosition, int height, int width, int depth,
            Color background, ActionListener actionListener, String[] textSource, Font font, boolean isScrollForward) {
        super(id, xPosition, yPosition, height, width, depth, background, actionListener, textSource, font);
        this.type = "ChangeDatabasePageButton";
        this.isScrollForward = isScrollForward;
        if (isScrollForward) {
            button.setActionCommand("SCROLL DATABASE PAGE FORWARD");
        }
        else {
            button.setActionCommand("SCROLL DATABASE PAGE BACKWARDS");
        }
    }
}

class ChangeDatabaseSortTypeButton extends Button {

    protected String sortType;

    public ChangeDatabaseSortTypeButton(String id, int xPosition, int yPosition, int height, int width, int depth,
            Color background, ActionListener actionListener, String[] textSource, Font font, String sortType) {
        super(id, xPosition, yPosition, height, width, depth, background, actionListener, textSource, font);
        this.type = "ChangeDatabaseSortTypeButton";
        this.sortType = sortType;
        button.setActionCommand("CHANGE SORT TO " + sortType);
    }
}

class DeleteDatabaseEntryButton extends Button {

    protected int entryPosition;

    public DeleteDatabaseEntryButton(String id, int xPosition, int yPosition, int height, int width, int depth,
            Color background, ActionListener actionListener, String[] textSource, Font font, int entryPosition) throws IOException {
        super(id, xPosition, yPosition, height, width, depth, background, actionListener, textSource, font);
        this.entryPosition = entryPosition;
        BufferedImage tempImage = ImageIO.read(getClass().getResource(ImageContainer.libraryPath + "X.png"));
        Image resizedImage = tempImage.getScaledInstance(resizeHorizontal(width, frameSizeHorizontal), resizeVertical(height, frameSizeVertical), Image.SCALE_SMOOTH);
        ImageIcon newImage = new ImageIcon(resizedImage);
        button.setActionCommand("DELETE ENTRY " + String.valueOf(entryPosition));
        button.setBackground(background);
        button.setIcon(newImage);
    }
}

class UploadTimeButton extends Button {

    public UploadTimeButton(String id, int xPosition, int yPosition, int height, int width, int depth, Color background,
            ActionListener actionListener, String[] textSource, Font font) {
        super(id, xPosition, yPosition, height, width, depth, background, actionListener, textSource, font);
        this.type = "UploadTimeButton";
        button.setActionCommand("UPLOAD TIME FOR RUN");
    }
}

class UploadScrambleButton extends Button {

    public UploadScrambleButton(String id, int xPosition, int yPosition, int height, int width, int depth,
            Color background, ActionListener actionListener, String[] textSource, Font font) {
        super(id, xPosition, yPosition, height, width, depth, background, actionListener, textSource, font);
        this.type = "UploadScrambleButton";
        button.setActionCommand("UPLOAD SCRAMBLE FOR RUN");
    }

}

