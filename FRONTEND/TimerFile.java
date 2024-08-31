package FRONTEND;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class TimerFile {
    public TimerFile() {

    }   
}

class TimerButton extends Button {

    protected long startTime = Integer.MIN_VALUE;
    protected long endTime = Integer.MIN_VALUE;
    protected long timeElapsed = 0;
    protected long pausedTimeElapsed = 0;
    protected Timer timer;
    protected boolean timerIsRunning = false;

    public TimerButton(String id, int xPosition, int yPosition, int height, int width, int depth, Color background,
            ActionListener actionListener, String[] textSource, Font font) {
        super(id, xPosition, yPosition, height, width, depth, background, actionListener, textSource, font);
        this.type = "StartTimerButton"; 
        button.removeActionListener(actionListener);
        this.timer = new Timer(1, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timeElapsed = System.currentTimeMillis()-startTime+pausedTimeElapsed;
                button.setText(timerMillisecondTimeFormatter(timeElapsed));
            }
        });
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!timerIsRunning) {
                    startTime = System.currentTimeMillis();
                    timer.start();
                    timerIsRunning = true;
                }
                else {
                    timer.stop();
                    endTime = timeElapsed;
                    pausedTimeElapsed = endTime;
                    timerIsRunning = false;
                }

            }
        });
        makeVisible();
    }
    
    public static String timerMillisecondTimeFormatter(long milliseconds) { // IN FORMAT 00:00:000
        long minutes = Math.floorDiv(milliseconds,60000);
        milliseconds-= minutes*60000;
        long seconds = Math.floorDiv(milliseconds,1000);
        milliseconds -= seconds*1000;
        String timeString = "";
        if (minutes > 10) {
            timeString += (String.valueOf(minutes) + ":");
        }
        else {
            timeString += ("0" + String.valueOf(minutes) + ":");
        }
        if (seconds > 10) {
            timeString += (String.valueOf(seconds) + ":");
        }
        else {
            timeString += ("0" + String.valueOf(seconds) + ":");
        }
        if (milliseconds > 100) {
            timeString += (String.valueOf(milliseconds));
        }
        else if (milliseconds > 10) {
            timeString += ("0" + String.valueOf(milliseconds));
        }
        else {
            timeString += ("00" + String.valueOf(milliseconds));
        }
        return timeString;
    }

    public String[] databaseUploadMillisecondTimeFormatter() { // contains minutes, seconds, and milliseconds in an array
        String[] elements = new String[3];
        String formattedString = timerMillisecondTimeFormatter(timeElapsed);
        elements[0] = formattedString.substring(0,2);
        elements[1] = formattedString.substring(3,5);
        elements[2] = formattedString.substring(6,9);
        return elements;
    }

    public void resetTimer() {
        startTime = Integer.MIN_VALUE;
        endTime = Integer.MIN_VALUE;
        timeElapsed = 0;
        pausedTimeElapsed = 0;
        timer.restart();
        timer.stop();
        timerIsRunning = false;
        button.setText("00:00:000");
    }

    public void stopTimer() {
        timer.stop();
        endTime = timeElapsed;
        pausedTimeElapsed = endTime;
        timerIsRunning = false;
    }
}

class ResetTimerButton extends Button {

    public ResetTimerButton(String id, int xPosition, int yPosition, int height, int width, int depth, Color background,
            ActionListener actionListener, String[] textSource, Font font) {
        super(id, xPosition, yPosition, height, width, depth, background, actionListener, textSource, font);
        this.type = "ResetTimerButton";
        button.setActionCommand("RESET TIMER");
        makeVisible();
    }

}