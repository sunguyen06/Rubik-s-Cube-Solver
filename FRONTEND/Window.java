package FRONTEND;

import java.awt.Color;

import javax.swing.*;

public class Window {

    public static Color standardBackgroundColor = Color.getHSBColor((float) 0.608, (float) 0.39, (float) 0.99);
    
    protected JPanel window;
    protected String windowID;
    protected int height; 
    protected int width;
    protected DisplayElement[] elementList = new DisplayElement[128];

    protected int elementListSize = 0;

    public Window() {
        window = new JPanel();
        window.setLayout(null);
        window.setBackground(standardBackgroundColor);

    }

    public Window (String _windowID, int _height, int _width) {
        this();
        windowID = _windowID;
        height = _height;
        width = _width;
        window.setBounds(0, 0, height, width);
    }

    public JPanel getWindow() {
        return window;
    }
    
    public String getwindowID() {
        return windowID;
    }

    public void add(DisplayElement element) {
        window.add(element.getComponent());
        window.revalidate();
        window.repaint();
        elementList[elementListSize] = element;
        elementListSize++;
        window.repaint();
    }

    public DisplayElement[] getElementList() {
        return elementList;
    }

    public int getElementListSize() {
        return elementListSize;
    }
}
