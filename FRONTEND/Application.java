package FRONTEND;

import javax.swing.*;

import org.w3c.dom.Text;

import BACKEND.CubeScrambler;
import BACKEND.RubiksCubeSolver;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.HashMap;

public class Application implements ActionListener {
    /*
     * STUFF TO DO
     * 
     */
    public static final int windowCount = 8;

    public static JFrame frame;
    public static Window currentWindow;
    public static Window[] windowList = new Window[windowCount];
    public static int displayHeight;
    public static int displayWidth;

    public static Color standardBackgroundColor = Color.getHSBColor((float) 0.608, (float) 0.39, (float) 0.99);
    
    public static HashMap<String, Object> objectDatabase = new HashMap<String, Object>();

    public static String[] blankString = {""};

    public static String currentColor = null;
    
    public static RubiksCubeNet currentSolveScrambleBuild = null;
    public static RubiksCubeNet currentGenerateScrambleView = null;
    public static RubiksCubeNet currentDatabaseScrambleView = null;

    public static DatabaseView solveDatabase;
    public static HashMap<String, String> errorMessageDatabase;

    // -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

    public void runApplication() throws IOException {
        frame = new JFrame("Rubik's Cube App");
        initializeFrame(frame);

        // -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
        windowList[0] = new Window("Title Window", displayHeight, displayWidth);

        windowList[0].add(new TextDisplay("titleText", 450, 125, 100, 1025, 1, standardBackgroundColor, new String[] {"RUBIK'S CUBE CENTRAL"}, FontList.titleFont));
        windowList[0].add(new TextDisplay("subtitleText", 675, 240, 100, 575, 1, standardBackgroundColor, new String[] {"By Victor Li and Su Nguyen"}, FontList.subtitleFont));
        windowList[0].add(new WindowChangeButton("titleGoToSolver", 675, 350, 100, 525, 1, Color.WHITE, this, new String[] {"CUBE SOLVER"}, FontList.subtitleFont, "Solver Window"));
        windowList[0].add(new WindowChangeButton("titleGoToDatabase", 675, 460, 100, 525, 1, Color.WHITE, this, new String[] {"DATABASE"}, FontList.subtitleFont, "Database Window"));
        windowList[0].add(new WindowChangeButton("titleGoToScrambler", 675, 570, 100, 525, 1, Color.WHITE, this, new String[] {"SCRAMBLER"}, FontList.subtitleFont, "Scramble Generate Window"));
        windowList[0].add(new WindowChangeButton("titleGoToTimer", 675, 680, 100, 525, 1, Color.WHITE, this, new String[] {"TIMER"}, FontList.subtitleFont, "Timer Window"));
        windowList[0].add(new QuitButton("titleQuitApp", 675, 790, 100, 525, 1, Color.WHITE, this, new String[] {"QUIT"}, FontList.titleFont));

        windowList[0].add(new ImageContainer("rubiksCubeImage", 50, 350, 500, 500, 1, standardBackgroundColor, "rubikscube.png"));

        windowList[0].add(new TextDisplay("manualHeader", 1400, 350, 50, 350, 1, standardBackgroundColor, new String[] {"HOW TO READ"}, FontList.subtitleFont));
        windowList[0].add(new TextDisplay("manualHeader", 1375, 410, 50, 400, 1, standardBackgroundColor, new String[] {"MOVE NOTATION"}, FontList.subtitleFont));
        windowList[0].add(new WindowChangeButton("titleGoToManual", 1315, 460, 300, 500, 1, Color.WHITE, this, new String[] {"READ MANUAL"}, FontList.subtitleFont, "Manual Window"));
        // -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

        windowList[1] = new Window("Solver Window", displayHeight, displayWidth);

        windowList[1].add(new TextDisplay("titleText", 50, 50, 100, 1025, 1, standardBackgroundColor, new String[] {"RUBIKS CUBE SOLVER"}, FontList.titleFont));
        windowList[1].add(new TextDisplay("explanationText", 50, 160, 130, 1500, 1, standardBackgroundColor, new String[] {"Construct your current scramble by clicking on a color, and then clicking on the respective square where it is on the cube. When looking at the cube, make sure the white center piece is on top, the yellow on the bottom, the green facing you, and the orange facing the left. Ensure that the cube you construct is in a valid state, or an error message will pop up. Additionally, if the cube is not completely solved after performing the sequence, put in the cube again, as it is not guaranteed to provide a solution on the first try."}, FontList.standardFont));
        
        windowList[1].add(new ColorSwatch("redColorSwatch", 50, 300, 75, 275, 1, standardBackgroundColor, this, blankString, FontList.titleFont, "RED"));
        windowList[1].add(new ColorSwatch("greenColorSwatch", 360, 300, 75, 275, 1, standardBackgroundColor, this, blankString, FontList.titleFont, "GREEN"));
        windowList[1].add(new ColorSwatch("yellowColorSwatch", 670, 300, 75, 275, 1, standardBackgroundColor, this, blankString, FontList.titleFont, "YELLOW"));
        windowList[1].add(new ColorSwatch("whiteColorSwatch", 980, 300, 75, 275, 1, standardBackgroundColor, this, blankString, FontList.titleFont, "WHITE"));
        windowList[1].add(new ColorSwatch("orangeColorSwatch", 1290, 300, 75, 275, 1, standardBackgroundColor, this, blankString, FontList.titleFont, "ORANGE"));
        windowList[1].add(new ColorSwatch("blueColorSwatch", 1600, 300, 75, 275, 1, standardBackgroundColor, this, blankString, FontList.titleFont, "BLUE"));
        
        windowList[1].add(new TextDisplay("solutionDisplayText", 50, 510, 300, 585, 1, Color.WHITE, new String[] {"GENERATED SOLUTION\nGOES HERE"}, FontList.subtitleFont));
        currentSolveScrambleBuild = new RubiksCubeNet(this, "Solver Window", windowList[1], true, 1000, 650, 60, 60);

        windowList[1].add(new WindowChangeButton("solverGoToMain", 1475, 50, 100, 400, 1, Color.WHITE, this, new String[] {"BACK TO MAIN"}, FontList.subtitleFont, "Title Window"));
        windowList[1].add(new UploadNetButton("uploadRubiksNetButton", 1475, 900, 75, 400, 1, Color.WHITE, this, new String[] {"SOLVE"}, FontList.subtitleFont));
//---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

        windowList[2] = new Window("Timer Window", displayHeight, displayWidth);

        windowList[2].add(new TextDisplay("titleText", 50, 50, 100, 1025, 1, standardBackgroundColor, new String[] {"RUBIKS CUBE TIMER"}, FontList.titleFont));
        windowList[2].add(new TextDisplay("subtitleText", 50, 160, 100, 1500, 1, standardBackgroundColor, new String[] {"Place both hands on the mouse or trackpad that you will click the timer with, and then click the time on screen to start.\nClick the time on screen to stop when you have finished the solve.\nAfter finishing a run, you can upload it to the database or reset the timer for another run."}, FontList.standardFont));
        windowList[2].add(new WindowChangeButton("timerGoToMain", 1475, 50, 100, 400, 1, Color.WHITE, this, new String[] {"BACK TO MAIN"}, FontList.subtitleFont, "Title Window"));

        windowList[2].add(new TimerButton("timerButton", 190, 300, 400, 1500, 1, Color.WHITE, this, new String[] {"00:00:000"}, FontList.massiveTimerFont));
        windowList[2].add(new ResetTimerButton("resetTimerButton", 190, 750, 200, 700, 1, Color.WHITE, this, new String[] {"RESET TIMER"}, FontList.titleFont));
        windowList[2].add(new UploadTimeButton("UploadTimerButton", 990, 750, 200, 700, 1, Color.WHITE, this, new String[] {"UPLOAD TIME"}, FontList.titleFont));

//--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

        windowList[3] = new Window("Database Window", displayHeight, displayWidth) ;

        windowList[3].add(new TextDisplay("titleText", 50, 50, 100, 1300, 1, standardBackgroundColor, new String[] {"RUBIKS CUBE RUN DATABASE"}, FontList.titleFont)); 
        windowList[3].add(new TextDisplay("subtitleText", 50, 160, 100, 1400, 1, standardBackgroundColor, new String[] {"View your previous runs sorted by most recent or fastest time. Your top 5 runs in terms of time are shown as well.\nUpload new runs to the database with the button below."}, FontList.standardFont));
        windowList[3].add(new WindowChangeButton("databaseGoToMain", 1475, 50, 100, 400, 1, Color.WHITE, this, new String[] {"BACK TO MAIN"}, FontList.subtitleFont, "Title Window"));


        windowList[3].add(new TextDisplay("#Heading", 125, 275, 50, 30, 1, standardBackgroundColor, new String[] {"#"}, FontList.subtitleFont)); 
        windowList[3].add(new TextDisplay("timeHeading", 275, 275, 50, 125, 1, standardBackgroundColor, new String[] {"TIME"}, FontList.subtitleFont)); 
        windowList[3].add(new TextDisplay("dateRecordedHeading", 600, 275, 50, 125, 1, standardBackgroundColor, new String[] {"DATE RECORDED"}, FontList.subtitleFont));
        windowList[3].add(new TextDisplay("scrambleHeading", 895, 275, 50, 400, 1, standardBackgroundColor, new String[] {"GIVEN SCRAMBLE"}, FontList.subtitleFont));
        
        windowList[3].add(new TextDisplay("slot1Heading", 125, 385, 60, 100, 1, standardBackgroundColor, new String[] {"AAAAAAAAAAAAAAAAAAAAAAAAA"}, FontList.subtitleFont));
        windowList[3].add(new TextDisplay("slot2Heading", 125, 495, 60, 100, 1, standardBackgroundColor, new String[] {"AAAAAAAAAAAAAAAAAAAAAAAAA"}, FontList.subtitleFont)); 
        windowList[3].add(new TextDisplay("slot3Heading", 125, 605, 60, 100, 1, standardBackgroundColor, new String[] {"AAAAAAAAAAAAAAAAAAAAAAAAA"}, FontList.subtitleFont)); 
        windowList[3].add(new TextDisplay("slot4Heading", 125, 715, 60, 100, 1, standardBackgroundColor, new String[] {"AAAAAAAAAAAAAAAAAAAAAAAAA"}, FontList.subtitleFont)); 
        windowList[3].add(new TextDisplay("slot5Heading", 125, 825, 60, 100, 1, standardBackgroundColor, new String[] {"AAAAAAAAAAAAAAAAAAAAAAAAA"}, FontList.subtitleFont));  

        windowList[3].add(new TextDisplay("slot1DataDisplay", 245, 385, 60, 600, 1, standardBackgroundColor, new String[] {"AAAAAAAAAAAAAAAAAAAAAAAAA"}, FontList.subtitleFont));
        windowList[3].add(new TextDisplay("slot2DataDisplay", 245, 495, 60, 600, 1, standardBackgroundColor, new String[] {"AAAAAAAAAAAAAAAAAAAAAAAAA"}, FontList.subtitleFont)); 
        windowList[3].add(new TextDisplay("slot3DataDisplay", 245, 605, 60, 600, 1, standardBackgroundColor, new String[] {"AAAAAAAAAAAAAAAAAAAAAAAAA"}, FontList.subtitleFont)); 
        windowList[3].add(new TextDisplay("slot4DataDisplay", 245, 715, 60, 600, 1, standardBackgroundColor, new String[] {"AAAAAAAAAAAAAAAAAAAAAAAAA"}, FontList.subtitleFont)); 
        windowList[3].add(new TextDisplay("slot5DataDisplay", 245, 825, 60, 600, 1, standardBackgroundColor, new String[] {"AAAAAAAAAAAAAAAAAAAAAAAAA"}, FontList.subtitleFont));  

        windowList[3].add(new ScrambleViewButton("slot1ScrambleView", 895, 375, 60, 360, 1, Color.WHITE, this, new String[] {"VIEW SCRAMBLE"}, FontList.standardFont, 1, null));
        windowList[3].add(new ScrambleViewButton("slot2ScrambleView", 895, 485, 60, 360, 1, Color.WHITE, this, new String[] {"VIEW SCRAMBLE"}, FontList.standardFont, 2, null));
        windowList[3].add(new ScrambleViewButton("slot3ScrambleView", 895, 595, 60, 360, 1, Color.WHITE, this, new String[] {"VIEW SCRAMBLE"}, FontList.standardFont,  3, null));
        windowList[3].add(new ScrambleViewButton("slot4ScrambleView", 895, 705, 60, 360, 1, Color.WHITE, this, new String[] {"VIEW SCRAMBLE"}, FontList.standardFont, 4, null));
        windowList[3].add(new ScrambleViewButton("slot5ScrambleView", 895, 815, 60, 360, 1, Color.WHITE, this, new String[] {"VIEW SCRAMBLE"}, FontList.standardFont, 5, null));

        windowList[3].add(new DeleteDatabaseEntryButton("slot1DeleteEntry",50, 375, 60, 60, 1, standardBackgroundColor, this, new String[] {""}, FontList.standardFont, 1));
        windowList[3].add(new DeleteDatabaseEntryButton("slot1DeleteEntry", 50, 485, 60, 60, 1, standardBackgroundColor, this, new String[] {""}, FontList.standardFont, 2));
        windowList[3].add(new DeleteDatabaseEntryButton("slot1DeleteEntry", 50, 595, 60, 60, 1, standardBackgroundColor, this, new String[] {""}, FontList.standardFont, 3));
        windowList[3].add(new DeleteDatabaseEntryButton("slot1DeleteEntry", 50, 705, 60, 60, 1, standardBackgroundColor, this, new String[] {""}, FontList.standardFont, 4));
        windowList[3].add(new DeleteDatabaseEntryButton("slot1DeleteEntry", 50, 815, 60, 60, 1, standardBackgroundColor, this, new String[] {""}, FontList.standardFont, 5));

        windowList[3].add(new TextDisplay("averageHeading", 1350, 500, 100, 500, 1, standardBackgroundColor, new String[] {"TIME AVERAGE"}, FontList.headerFont));
        windowList[3].add(new TextDisplay("averageTimeDisplay", 1425, 600, 100, 350, 1, standardBackgroundColor, new String[] {"00:00:000"}, FontList.headerFont)); 

        windowList[3].add(new ChangeDatabasePageButton("databaseScrollBackwardsButton", 125, 900, 75, 200, 1, Color.WHITE, this, new String[] {"<-"}, FontList.titleFont, false));
        windowList[3].add(new ChangeDatabasePageButton("databaseScrollForwardsButton", 1055, 900, 75, 200, 1, Color.WHITE, this, new String[] {"->"}, FontList.titleFont, true));
        windowList[3].add(new ChangeDatabaseSortTypeButton("sortByTimeButton", 375, 900, 75, 300, 1, Color.WHITE, this, new String[] {"SORT BY TIME"}, FontList.standardFont, "TIME"));
        windowList[3].add(new ChangeDatabaseSortTypeButton("sortByDateButton", 695, 900, 75, 300, 1, Color.WHITE, this, new String[] {"SORT BY DATE"}, FontList.standardFont, "DATE"));
        windowList[3].add(new WindowChangeButton("databaseGoToUpload", 1350, 800, 100, 450, 1, Color.WHITE, this, new String[] {"UPLOAD NEW RUN"}, FontList.subtitleFont, "Database Upload Window"));
        //----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
        
        windowList[4] = new Window("Scramble View Window", displayHeight, displayWidth);
        windowList[4].add(new TextDisplay("titleText", 50, 50, 100, 1100, 1, standardBackgroundColor, new String[] {"SCRAMBLE VIEW"}, FontList.titleFont)); 
        windowList[4].add(new TextDisplay("subtitleText", 50, 160, 100, 1400, 1, standardBackgroundColor, new String[] {"This will display the scramble generated and the final cube state after the scramble. If no scramble was generated, the cube will be in the default state."}, FontList.standardFont));
        windowList[4].add(new WindowChangeButton("scrambleViewGoToDatabase", 1275, 50, 100, 600, 1, Color.WHITE, this, new String[] {"BACK TO DATABASE"}, FontList.subtitleFont, "Database Window"));

        windowList[4].add(new TextDisplay("scrambleViewTextDisplay", 1250, 360, 300, 600, 1, Color.WHITE, new String[] {"SCRAMBLE LISTED GOES HERE"}, FontList.subtitleFont));
        currentDatabaseScrambleView = new RubiksCubeNet(this, "Scramble View Window", windowList[4], false, 450, 600, 70, 70);
//----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

        windowList[5] = new Window("Database Upload Window", displayHeight, displayWidth);

        windowList[5].add(new TextDisplay("titleText", 50, 50, 100, 1100, 1, standardBackgroundColor, new String[] {"UPLOAD NEW RUN"}, FontList.titleFont)); 
        windowList[5].add(new TextDisplay("subtitleText", 50, 160, 100, 1400, 1, standardBackgroundColor, new String[] {"Fill out the run below with the time (minutes:seconds:milliseconds), date (dd/mm/yyyy), and given scramble.\nIf you have a time and/or scramble currently generated, it will autofill into the fields."}, FontList.standardFont));
        windowList[5].add(new WindowChangeButton("uploadDatabaseGoToDatabase", 1275, 50, 100, 600, 1, Color.WHITE, this, new String[] {"BACK TO DATABASE"}, FontList.subtitleFont, "Database Window"));

        windowList[5].add(new TextDisplay("timeHeading", 150, 300, 100, 250, 1, standardBackgroundColor, new String[] {"TIME"}, FontList.titleFont));
        windowList[5].add(new EditableTextDisplay("minuteInput", 600, 300, 80, 200, 1, Color.WHITE, new String[] {"00"}, FontList.titleFont, 1, 2));
        windowList[5].add(new TextDisplay("Colon", 825, 300, 100, 25, 1, standardBackgroundColor, new String[] {":"}, FontList.titleFont));
        windowList[5].add(new EditableTextDisplay("secondInput", 875, 300, 80, 200, 1, Color.WHITE, new String[] {"00"}, FontList.titleFont, 1, 2));
        windowList[5].add(new TextDisplay("Colon", 1100, 300, 100, 25, 1, standardBackgroundColor, new String[] {":"}, FontList.titleFont));
        windowList[5].add(new EditableTextDisplay("millisecondInput", 1150, 300, 80, 250, 1, Color.WHITE, new String[] {"000"}, FontList.titleFont, 1, 3));

        windowList[5].add(new TextDisplay("dateHeading", 150, 450, 100, 400, 1, standardBackgroundColor, new String[] {"DATE"}, FontList.titleFont));
        windowList[5].add(new EditableTextDisplay("dayInput", 600, 450, 80, 200, 1, Color.WHITE, new String[] {"00"}, FontList.titleFont, 1, 2));
        windowList[5].add(new TextDisplay("Slash", 825, 450, 80, 25, 1, standardBackgroundColor, new String[] {"/"}, FontList.titleFont));
        windowList[5].add(new EditableTextDisplay("monthInput", 875, 450, 80, 200, 1, Color.WHITE, new String[] {"00"}, FontList.titleFont, 1, 2));
        windowList[5].add(new TextDisplay("Slash", 1100, 450, 80, 25, 1, standardBackgroundColor, new String[] {"/"}, FontList.titleFont));
        windowList[5].add(new EditableTextDisplay("yearInput", 1150, 450, 80, 250, 1, Color.WHITE, new String[] {"0000"}, FontList.titleFont, 1, 4));

        windowList[5].add(new TextDisplay("scrambleHeading", 150, 600, 100, 500, 1, standardBackgroundColor, new String[] {"SCRAMBLE"}, FontList.titleFont));
        windowList[5].add(new EditableTextDisplay("moveInput", 800, 600, 80, 600, 1, Color.WHITE, new String[] {"Format like L R\' B2, with spaces in between each move. Delete and leave blank if you do not know the scramble."}, FontList.standardFont, 6, 30));

        windowList[5].add(new UploadRunButton("runUploadButton", 450, 750, 150, 700, 1, Color.WHITE, this, new String[] {"UPLOAD"}, FontList.subtitleFont));
// ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

        windowList[6] = new Window("Scramble Generate Window", displayHeight, displayWidth);
        windowList[6].add(new TextDisplay("titleText", 50, 50, 100, 1300, 1, standardBackgroundColor, new String[] {"SCRAMBLER"}, FontList.titleFont)); 
        windowList[6].add(new TextDisplay("subtitleText", 50, 160, 100, 1400, 1, standardBackgroundColor, new String[] {"Click the button to generate a random scramble for your runs.\nThe app will provide you with the move notation and an image of the final cube state.\nPress the Time Run Button to begin recording your time for this scramble. It will be saved and automatically be filled when you upload your run."}, FontList.standardFont));
        windowList[6].add(new WindowChangeButton("scrambleGenerateGoToMain", 1475, 50, 100, 400, 1, Color.WHITE, this, new String[] {"BACK TO MAIN"}, FontList.subtitleFont, "Title Window"));

        windowList[6].add(new TextDisplay("scrambleHeader", 1325, 300, 50, 500, 1, standardBackgroundColor, new String[] {"CURRENT SCRAMBLE"}, FontList.subtitleFont));
        windowList[6].add(new TextDisplay("scrambleGenerateTextDisplay", 1250, 360, 300, 600, 1, Color.WHITE, new String[] {"SCRAMBLE GENERATED HERE"}, FontList.subtitleFont));

        currentGenerateScrambleView = new RubiksCubeNet(this, "Scramble Generate Window", windowList[6], false, 450, 600, 70, 70);
        windowList[6].add(new ScrambleGeneratorButton("generateScrambleButton", 1250, 700, 125, 600, 1, Color.WHITE, this, new String[] {"GENERATE SCRAMBLE"}, FontList.subtitleFont));
        windowList[6].add(new UploadScrambleButton("UploadScrambleButton", 1250, 850, 125, 600, 1, Color.WHITE, this, new String[] {"TIME RUN"}, FontList.subtitleFont));
//---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

        windowList[7] = new Window("Manual Window", displayHeight, displayWidth);
        windowList[7].add(new TextDisplay("titleText", 50, 50, 100, 1300, 1, standardBackgroundColor, new String[] {"READING MOVE NOTATION"}, FontList.titleFont)); 
        windowList[7].add(new WindowChangeButton("manualGoToMain", 1475, 50, 100, 400, 1, Color.WHITE, this, new String[] {"BACK TO MAIN"}, FontList.subtitleFont, "Title Window"));
        windowList[7].add(new ImageContainer("manualImage", 50, 160, 800, 1600, 1, standardBackgroundColor, "manual.png"));

//-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
//--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
        solveDatabase = new DatabaseView(DatabaseIO.loadSolves());
        loadObjectDatabase();

        currentSolveScrambleBuild.loadCublets(objectDatabase);
        currentGenerateScrambleView.loadCublets(objectDatabase);
        currentDatabaseScrambleView.loadCublets(objectDatabase);
        loadDatabasePage();

        errorMessageDatabase = DatabaseIO.loadErrorMessages();
        changeWindow("Title Window");
    } 
    
    public static void initializeFrame(JFrame frame) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        displayHeight = screenSize.height;
        displayWidth = screenSize.width;
        DisplayElement.setFrameSizeVertical(displayHeight);
        DisplayElement.setFrameSizeHorizontal(displayWidth);
        FontList.setFrameSize(displayWidth);
        FontList.initializeFonts();
        frame.setSize(displayWidth, displayHeight);
        System.out.println(displayHeight + " " + displayWidth);
        frame.setVisible(true);//making the frame visible  
    }

    public static void refresh() {
        frame.getContentPane().removeAll();
        frame.validate();
    }
    
    public static void loadObjectDatabase() {
        for (int i = 0; i < windowCount; i++) {
            DisplayElement[] elementList = windowList[i].getElementList();
            for (int j = 0; j < windowList[i].getElementListSize(); j++) {
                objectDatabase.put(elementList[j].getId(), elementList[j]);
            }
        }
    }

    public static void loadDatabasePage() {
        String[] pageData = solveDatabase.getCurrentPageInfo();
        String[] scrambleData = solveDatabase.getCurrentPageScrambles();
        int databaseIndex = solveDatabase.getCurrentIndex();
        String averageTime = solveDatabase.getAverageTime();
        for (int i = 1; i <= 5; i++) {
            String slotId = "slot" + String.valueOf(i) + "DataDisplay";
            TextDisplay slotDisplay = (TextDisplay) objectDatabase.get(slotId);
            slotDisplay.updateTextDisplay(pageData[i-1]);

            String headerId = "slot" + String.valueOf(i) + "Heading";
            TextDisplay headerDisplay = (TextDisplay) objectDatabase.get(headerId);
            headerDisplay.updateTextDisplay(String.valueOf(i+databaseIndex));

            String scrambleId = "slot" + String.valueOf(i) + "ScrambleView";
            ScrambleViewButton scrambleViewButton = (ScrambleViewButton) objectDatabase.get(scrambleId);
            scrambleViewButton.setScramble(scrambleData[i-1]);

            TextDisplay timeAverage = (TextDisplay) objectDatabase.get("averageTimeDisplay");
            timeAverage.updateTextDisplay(averageTime);
        }
    }

    public static void changeWindow(String windowID) {
        
        Window bufferWindow = null;

        for (int i = 0; i < windowList.length; i++) {
            if (windowList[i].getwindowID().equals(windowID)) {
                bufferWindow = windowList[i];
                break;
            }
        }

        if (bufferWindow == null) {
            System.out.println("NO WINDOW WITH THIS ID COULD BE FOUND");
            return;
        }
        refresh();
        frame.add(bufferWindow.getWindow());
        frame.revalidate();
        frame.repaint();
        currentWindow = bufferWindow;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if (command.contains("CHANGE WINDOW ")) {
            command = command.replace("CHANGE WINDOW ", "");
            changeWindow(command);
        }

        else if (command.contains("QUIT")) {
            try {
                DatabaseIO.saveSolves(solveDatabase);
            } catch (IOException e1) {
                System.out.println("SAVING NOT SUCCESSFUL");
                e1.printStackTrace();
            }
            System.exit(0);
        }

        else if (command.contains("SWITCH CURRENT COLOR TO ")) {
            command = command.replace("SWITCH CURRENT COLOR TO ", "");
            currentColor = command;
            System.out.println("CURRENT COLOR IS " + currentColor);
        }
        
        else if (command.contains("CHANGE CURRENT COLOR OF BLOCKFACE")) { // assuming currently on Solver Window
            DisplayElement[] elementList = currentWindow.getElementList();
            Object sourceObject = e.getSource();

            for (int i = 0; i < currentWindow.getElementListSize(); i++) {
                DisplayElement currentElement = elementList[i];

                if (currentElement.getType().equals("BlockFace")) {
                    BlockFace currentBlockFace = (BlockFace) currentElement;
                    if (currentBlockFace.getButton().equals(sourceObject)) {
                        currentBlockFace.updateColor(currentColor);
                        break;
                    }
                }
            }
        }

        else if (command.contains("DO NOT CHANGE CURRENT COLOR OF BLOCKFACE")) {
            return;
        }

        else if (command.contains("RESET TIMER")) {
            TimerButton button = (TimerButton) objectDatabase.get("timerButton");
            button.resetTimer();
        }

        else if (command.contains("UPLOAD NET")) {
            String sequence = RubiksCubeSolver.findShortSolution(currentSolveScrambleBuild.toDataString());
            System.out.println(sequence);
            TextDisplay solutionDisplayText = (TextDisplay) objectDatabase.get("solutionDisplayText");
            if (sequence.contains("ERROR CODE ")) {
                displayErrorMessage(sequence);
                return;
            }
            else {
                solutionDisplayText.updateTextDisplay(sequence);
                return;
            }
        }

        else if (command.contains("UPLOAD RUN")) {
            EditableTextDisplay minutes = (EditableTextDisplay) objectDatabase.get("minuteInput");
            EditableTextDisplay seconds = (EditableTextDisplay) objectDatabase.get("secondInput");
            EditableTextDisplay milliseconds = (EditableTextDisplay) objectDatabase.get("millisecondInput");

            if (!InputValidation.timeIsValid(minutes.getText(), seconds.getText(), milliseconds.getText())) {
                displayErrorMessage("ERROR CODE 20");
                return;
            }

            String time = minutes.getText() + ":" + seconds.getText() + ":" + milliseconds.getText();

            EditableTextDisplay day = (EditableTextDisplay) objectDatabase.get("dayInput");
            EditableTextDisplay month = (EditableTextDisplay) objectDatabase.get("monthInput");
            EditableTextDisplay year = (EditableTextDisplay) objectDatabase.get("yearInput");
            
            String date = day.getText() + "/" + month.getText() + "/" + year.getText();

            if (!InputValidation.dateIsValid(day.getText() + month.getText() + year.getText())) {
                displayErrorMessage("ERROR CODE 21");
                return;
            }

            EditableTextDisplay scrambleInput = (EditableTextDisplay) objectDatabase.get("moveInput");
            String scrambleString = scrambleInput.getText();

            if (!InputValidation.isValidMoveSequence(scrambleString) && !scrambleString.equals("")) {
                displayErrorMessage("ERROR CODE 26");
                return;
            }

            if (solveDatabase.isFull()) {
                displayErrorMessage("ERROR CODE 22");
                return;
            }

            solveDatabase.addToList(time, date, scrambleString);
            seconds.updateTextDisplay("00");
            minutes.updateTextDisplay("00");
            milliseconds.updateTextDisplay("000");
            day.updateTextDisplay("00");
            month.updateTextDisplay("00");
            year.updateTextDisplay("0000");
            scrambleInput.updateTextDisplay("Format like L R\' B2, with spaces in between each move. Delete and leave blank if you do not know the scramble.");
            loadDatabasePage();
            changeWindow("Database Window");
        }

        else if (command.contains("DELETE ENTRY ")) {
            command = command.replace("DELETE ENTRY ", "");
            TextDisplay slotDisplay = (TextDisplay) objectDatabase.get("slot" + command + "DataDisplay");
            if (slotDisplay.getText().contains("ENTRY DOES NOT EXIST")) {
                displayErrorMessage("ERROR CODE 25");
                return;
            }
            else {
                solveDatabase.deleteFromList(Integer.valueOf(command));
                loadDatabasePage();
            }
        }

        else if (command.contains("SCROLL DATABASE PAGE FORWARD")) {
            solveDatabase.updateCurrentIndex(true);
            loadDatabasePage();
        }

        else if (command.contains("SCROLL DATABASE PAGE BACKWARDS")) {
            solveDatabase.updateCurrentIndex(false);
            loadDatabasePage();
        }
        else if (command.contains("CHANGE SORT TO ")) {
            command = command.replace("CHANGE SORT TO ", "");
            solveDatabase.updateSortType(command);
            loadDatabasePage();
        }
        
        else if (command.contains("LOAD SCRAMBLE VIEW ")) {
            command = command.replace("LOAD SCRAMBLE VIEW ", "");
            TextDisplay slotDisplay = (TextDisplay) objectDatabase.get("slot" + command.substring(0, 1) + "DataDisplay");
            if (slotDisplay.getText().contains("ENTRY DOES NOT EXIST")) {
                displayErrorMessage("ERROR CODE 27");
                return;
            }
            command = command.replace(command.substring(0, 1), "");
            command = command.replace(" WITH SCRAMBLE ", "");
            String newScramble = command;
            TextDisplay scrambleTextDisplay = (TextDisplay) objectDatabase.get("scrambleViewTextDisplay");
            if (!newScramble.equals("")) {
                String newCube = RubiksCubeNet.convertLetterFaceToNum(CubeScrambler.getScrambledNet(newScramble));
                currentDatabaseScrambleView.overwriteCube(newCube);
                scrambleTextDisplay.updateTextDisplay(newScramble);
            }
            else {
                currentDatabaseScrambleView.reset();
                scrambleTextDisplay.updateTextDisplay("NO SCRAMBLE ASSOCIATED WITH RUN");
            }

            changeWindow("Scramble View Window");
        }
        else if (command.contains("GENERATE SCRAMBLE")) { // WORKS
            String newScramble = CubeScrambler.getScramble();
            String newCube = RubiksCubeNet.convertLetterFaceToNum(CubeScrambler.getScrambledNet(newScramble));
            currentGenerateScrambleView.overwriteCube(newCube);
            TextDisplay scrambleTextDisplay = (TextDisplay) objectDatabase.get("scrambleGenerateTextDisplay");
            scrambleTextDisplay.updateTextDisplay(newScramble);
        }
        else if (command.contains("UPLOAD TIME FOR RUN")) {
            TimerButton button = (TimerButton) objectDatabase.get("timerButton");
            String[] numbers = button.databaseUploadMillisecondTimeFormatter();
            if (numbers[0].equals("00") && numbers[1].equals("00") && numbers[2].equals("000")) {
                displayErrorMessage("ERROR CODE 24");
                return;
            }
            EditableTextDisplay minutes = (EditableTextDisplay) objectDatabase.get("minuteInput");
            EditableTextDisplay seconds = (EditableTextDisplay) objectDatabase.get("secondInput");
            EditableTextDisplay milliseconds = (EditableTextDisplay) objectDatabase.get("millisecondInput");

            minutes.updateTextDisplay(numbers[0]);
            seconds.updateTextDisplay(numbers[1]);
            milliseconds.updateTextDisplay(numbers[2]);
            button.stopTimer();
            changeWindow("Database Upload Window");
        }
        else if (command.contains("UPLOAD SCRAMBLE FOR RUN")) {
            TextDisplay scramble = (TextDisplay) objectDatabase.get("scrambleGenerateTextDisplay");
            String scrambleString = scramble.getText();
            if (scrambleString.contains("SCRAMBLE GENERATED HERE")) { // check if scrmable has been generated yet
                displayErrorMessage("ERROR CODE 23");
                return;
            }
            else {
                EditableTextDisplay scrambleInput = (EditableTextDisplay) objectDatabase.get("moveInput");
                scrambleInput.updateTextDisplay(scrambleString);
                changeWindow("Timer Window");
                return;
            }
        }
        
    }

    public void displayErrorMessage (String errorCode) {
        errorCode = errorCode.replace("ERROR CODE ", "");
        JOptionPane.showMessageDialog(frame, errorMessageDatabase.get(errorCode), "ERROR", JOptionPane.ERROR_MESSAGE);
    }

}


