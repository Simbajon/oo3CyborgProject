import acm.graphics.*;
import acm.program.*;
import java.awt.Color;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.JFrame;

public class StartingScreen extends GraphicsProgram {
    public static final int SCREEN_WIDTH = 800;
    public static final int SCREEN_HEIGHT = 600;
    private GRect startButton;
    private GRect exitButton;

    public void run() {
        setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
        setBackground(Color.BLUE);

        String titleText = "Atmos_FEAR";
        GLabel titleLabel = new GLabel(titleText);
        titleLabel.setFont("Arial-Bold-48");
        titleLabel.setColor(Color.BLACK);
        titleLabel.setLocation((SCREEN_WIDTH - titleLabel.getWidth()) / 2, (SCREEN_HEIGHT - titleLabel.getHeight()) / 3);

        GLabel outlineLabel = new GLabel(titleText);
        outlineLabel.setFont("Arial-Bold-48");
        outlineLabel.setColor(Color.WHITE);
        outlineLabel.setLocation(titleLabel.getX() - 2, titleLabel.getY() - 2);

        add(outlineLabel);
        add(titleLabel);

        startButton = new GRect(150, 50);
        GLabel startLabel = new GLabel("Start");
        startLabel.setFont("Arial-Bold-20");
        startLabel.setColor(Color.BLACK);
        double startX = (SCREEN_WIDTH - startButton.getWidth()) / 2 - 80;
        double startY = titleLabel.getY() + titleLabel.getHeight() + 20;
        startButton.setFilled(true);
        startButton.setFillColor(Color.WHITE);
        startButton.setLocation(startX, startY);
        startLabel.setLocation(startX + (startButton.getWidth() - startLabel.getWidth()) / 2,
                startY + (startButton.getHeight() + startLabel.getAscent()) / 2);

        exitButton = new GRect(150, 50);
        GLabel exitLabel = new GLabel("Exit");
        exitLabel.setFont("Arial-Bold-20");
        exitLabel.setColor(Color.BLACK);
        double exitX = (SCREEN_WIDTH - exitButton.getWidth()) / 2 + 80;
        double exitY = startY;
        exitButton.setFilled(true);
        exitButton.setFillColor(Color.WHITE);
        exitButton.setLocation(exitX, exitY);
        exitLabel.setLocation(exitX + (exitButton.getWidth() - exitLabel.getWidth()) / 2,
                exitY + (exitButton.getHeight() + exitLabel.getAscent()) / 2);

        add(startButton);
        add(startLabel);
        add(exitButton);
        add(exitLabel);

        addMouseListeners();
    }

    public void mouseClicked(MouseEvent e) {
        double mouseX = e.getX();
        double mouseY = e.getY();

        if (isClicked(startButton, mouseX, mouseY)) {
            openGameWindow();
        }

        if (isClicked(exitButton, mouseX, mouseY)) {
            System.exit(0);
        }
    }

    private boolean isClicked(GRect button, double mouseX, double mouseY) {
        return mouseX >= button.getX() && mouseX <= button.getX() + button.getWidth()
                && mouseY >= button.getY() && mouseY <= button.getY() + button.getHeight();
    }

    private void openGameWindow() {
        JFrame startingScreenFrame = (JFrame) getJFrame();
        startingScreenFrame.dispose(); // Close the StartingScreen window

        SwingUtilities.invokeLater(() -> {
            new GameWindow().start();
        });
    }

    public static void main(String[] args) {
        new StartingScreen().start(args);
    }
}
