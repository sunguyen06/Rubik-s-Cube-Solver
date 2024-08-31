package FRONTEND;

import java.awt.Font;

public class FontList {

    public static int frameSize;
    public static Font standardFont;
    public static Font titleFont;
    public static Font headerFont;
    public static Font subtitleFont;
    public static Font massiveTimerFont;

    public static Font getTitleFont() {
        return titleFont;
    }
    public static Font getStandardFont() {
        return standardFont;
    }

    public static void initializeFonts() {
        standardFont = new Font(Font.SANS_SERIF, Font.BOLD, resize(25, frameSize));
        titleFont = new Font(Font.SANS_SERIF, Font.BOLD, resize(80, frameSize));
        headerFont = new Font(Font.SANS_SERIF, Font.BOLD, resize(60, frameSize));
        subtitleFont = new Font(Font.SANS_SERIF, Font.BOLD, resize(40,frameSize));
        massiveTimerFont = new Font(Font.SANS_SERIF, Font.BOLD, resize(200,frameSize));

    }

    public static int resize(double initial, double frameSize) { // assume 1920
        double ratio = initial/1920;
        return (int) (ratio*frameSize);
    }

    public static void setFrameSize(int frameSize) {
        FontList.frameSize = frameSize;
    }

}
