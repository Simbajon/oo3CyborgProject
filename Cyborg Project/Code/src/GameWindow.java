//Game Version 15.7

import acm.graphics.*;
import acm.program.GraphicsProgram;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JFrame;

public class GameWindow extends GraphicsProgram {
    public static final int ORIGINAL_WIDTH = 1920;
    public static final int ORIGINAL_HEIGHT = 1024;
    public static final double SCALE_FACTOR = 0.85;
    private static final String BACKGROUND_IMAGE_PATH = "assets/WorldMap.jpg";
    private static final int MAX_GAUGE_HEIGHT = 300;
    private static final int MAX_GARBAGE_COUNT = 300;
    private static final String GAME_OVER_MESSAGE = "Garbage has devoured our world. Beware the consequences of neglect.";

    private GRect treeCountLevel;
    private GRect airQualityLevel;
    private GRect garbageCountLevel;
    private GRect treeCountGauge;
    private GRect airQualityGauge;
    private GRect garbageCountGauge;

    private GLabel treeCountLabel;
    private GLabel airQualityLabel;
    private GLabel garbageCountLabel;

    private GRect dialogueBox; // Rectangle for dialogue
    private GRect choice1;
    private GRect choice2;
    private GRect choice3;

    private GLabel eventDescriptionLabel; // Label for the event description
    private GLabel choiceLabel1;
    private GLabel choiceLabel2;
    private GLabel choiceLabel3;

    private static final int EVENT_DESCRIPTION_MAX_FONT_SIZE = 16;
    private static final int EVENT_DESCRIPTION_MIN_FONT_SIZE = 12;
    private static final int CHOICE_LABEL_FONT_SIZE = 16;
    private static final int DIALOGUE_BOX_WIDTH = 500;


    public void run() {
        int scaledWidth = (int) (ORIGINAL_WIDTH * SCALE_FACTOR * 0.8); // Adjusted to be 20% smaller
        int scaledHeight = (int) (ORIGINAL_HEIGHT * SCALE_FACTOR * 0.8); // Adjusted to be 20% smaller
        setSize(scaledWidth, scaledHeight);

        // Load and scale the background image
        GImage worldMapImage = new GImage(BACKGROUND_IMAGE_PATH);
        worldMapImage.setSize(scaledWidth, scaledHeight);
        add(worldMapImage);

        // Add the "Tree Count" level on the upper left part of the screen
        int levelWidth = 60;
        int levelHeight = (int) (MAX_GAUGE_HEIGHT * 0.2);
        int levelX = 20;
        int levelY = 20;

        treeCountLevel = new GRect(levelX, levelY, levelWidth, levelHeight);
        treeCountLevel.setFilled(true);
        treeCountLevel.setColor(Color.GREEN); // Set the color to green for Tree Count
        add(treeCountLevel);

        // Add the "Air Quality" level on the upper left part of the screen
        int airQualityX = levelX + levelWidth + 10;

        airQualityLevel = new GRect(airQualityX, levelY, levelWidth, levelHeight);
        airQualityLevel.setFilled(true);
        airQualityLevel.setColor(Color.BLUE); // Set the color to blue for Air Quality
        add(airQualityLevel);

        // Add the "Garbage Count" level on the upper left part of the screen
        int garbageCountX = airQualityX + levelWidth + 10;

        garbageCountLevel = new GRect(garbageCountX, levelY, levelWidth, levelHeight);
        garbageCountLevel.setFilled(true);
        garbageCountLevel.setColor(Color.BLACK); // Set the color to black for Garbage Count
        add(garbageCountLevel);

        // Add labels for the levels (vertical and white)
        treeCountLabel = new GLabel("Tree Count");
        treeCountLabel.setFont("Arial-Bold-12");
        treeCountLabel.setColor(Color.WHITE);
        treeCountLabel.setLocation(levelX, levelY + 15); // Position the label below the "Tree Count" level
        treeCountLabel.rotate(-90); // Rotate the label by -90 degrees (vertical)
        add(treeCountLabel);

        airQualityLabel = new GLabel("Air Quality");
        airQualityLabel.setFont("Arial-Bold-12");
        airQualityLabel.setColor(Color.WHITE);
        airQualityLabel.setLocation(airQualityX, levelY + 15); // Position the label below the "Air Quality" level
        airQualityLabel.rotate(-90); // Rotate the label by -90 degrees (vertical)
        add(airQualityLabel);

        garbageCountLabel = new GLabel("Garbage Count");
        garbageCountLabel.setFont("Arial-Bold-12");
        garbageCountLabel.setColor(Color.WHITE);
        garbageCountLabel.setLocation(garbageCountX, levelY + 15); // Position the label below the "Garbage Count" level
        garbageCountLabel.rotate(-90); // Rotate the label by -90 degrees (vertical)
        add(garbageCountLabel);

        // Set the levels to 20% of the max gauge height
        int gaugeWidth = 60;
        int gaugeHeight = 300;

        treeCountGauge = new GRect(levelX, levelY, gaugeWidth, gaugeHeight);
        treeCountGauge.setFilled(true);
        treeCountGauge.setColor(new Color(0, 255, 0, 51)); // Set the color to green (20% opaque)
        add(treeCountGauge);

        airQualityGauge = new GRect(airQualityX, levelY, gaugeWidth, gaugeHeight);
        airQualityGauge.setFilled(true);
        airQualityGauge.setColor(new Color(0, 0, 255, 51)); // Set the color to blue (20% opaque)
        add(airQualityGauge);

        garbageCountGauge = new GRect(garbageCountX, levelY, gaugeWidth, gaugeHeight);
        garbageCountGauge.setFilled(true);
        garbageCountGauge.setColor(new Color(0, 0, 0, 51)); // Set the color to black (20% opaque)
        add(garbageCountGauge);

        // Add the dialogue box on the bottom part of the screen
        int dialogueBoxWidth = scaledWidth / 2;
        int dialogueBoxHeight = scaledHeight / 6;
        int dialogueBoxX = (scaledWidth - dialogueBoxWidth) / 2;
        int dialogueBoxY = scaledHeight - dialogueBoxHeight - 60; // Raised by 20 pixels

        dialogueBox = new GRect(dialogueBoxX, dialogueBoxY, dialogueBoxWidth, dialogueBoxHeight);
        dialogueBox.setFilled(true);
        dialogueBox.setColor(new Color(128, 128, 128, 204)); // Set the color to gray with 20% less opacity (204 instead of 255)
        add(dialogueBox);

        // Add three buttons (Choices) on the bottom part of the dialogue box with gray color and 30 pixels spacing
        int buttonWidth = (dialogueBoxWidth - 4 * 30) / 3; // Adjusted for even spacing
        int buttonHeight = 40;
        int buttonY = dialogueBoxY + dialogueBoxHeight - buttonHeight - 10;

        choice1 = new GRect(dialogueBoxX + 30, buttonY, buttonWidth, buttonHeight);
        choice1.setFilled(true);
        choice1.setColor(Color.GRAY);
        add(choice1);

        choice2 = new GRect(dialogueBoxX + 2 * 30 + buttonWidth, buttonY, buttonWidth, buttonHeight);
        choice2.setFilled(true);
        choice2.setColor(Color.GRAY);
        add(choice2);

        choice3 = new GRect(dialogueBoxX + 3 * 30 + 2 * buttonWidth, buttonY, buttonWidth, buttonHeight);
        choice3.setFilled(true);
        choice3.setColor(Color.GRAY);
        add(choice3);

        // Add the event description to the dialogue box and adjust font size to fit
        String eventDescription = "Pollution, deforestation, and waste accumulation led to declining Tree Quality, \n" +
                "deteriorating Air Quality, and escalating Garbage count.";

        int fontSize = EVENT_DESCRIPTION_MAX_FONT_SIZE;
        eventDescriptionLabel = new GLabel(eventDescription, dialogueBox.getX() + 10, dialogueBox.getY() + 30);

        // Decrease font size until the event description fits within the dialogue box
        while (fontSize >= EVENT_DESCRIPTION_MIN_FONT_SIZE && eventDescriptionLabel.getWidth() > DIALOGUE_BOX_WIDTH - 20) {
            fontSize--;
            eventDescriptionLabel.setFont("Arial-" + fontSize);
        }

        eventDescriptionLabel.setColor(Color.BLACK);
        add(eventDescriptionLabel);

        // Labels for the choices
        choiceLabel1 = new GLabel("Clean Air Uprising");
        choiceLabel1.setFont("Arial-Bold-" + CHOICE_LABEL_FONT_SIZE);
        choiceLabel1.setColor(Color.WHITE);
        choiceLabel1.setLocation(choice1.getX() + 10, choice1.getY() + 20); // Adjusted the vertical position
        add(choiceLabel1);

        choiceLabel2 = new GLabel("Garbage Revolt");
        choiceLabel2.setFont("Arial-Bold-" + CHOICE_LABEL_FONT_SIZE);
        choiceLabel2.setColor(Color.WHITE);
        choiceLabel2.setLocation(choice2.getX() + 10, choice2.getY() + 20); // Adjusted the vertical position
        add(choiceLabel2);

        choiceLabel3 = new GLabel("Verdant Reforestation");
        choiceLabel3.setFont("Arial-Bold-" + CHOICE_LABEL_FONT_SIZE);
        choiceLabel3.setColor(Color.WHITE);
        choiceLabel3.setLocation(choice3.getX() + 10, choice3.getY() + 20); // Adjusted the vertical position
        add(choiceLabel3);

        // Add event listeners to the choices
        choice1.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
                // Action when choice 1 is clicked
                increaseLevels(0, 0.10, 0);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                choice1.setFillColor(Color.DARK_GRAY);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                choice1.setFillColor(Color.GRAY);
            }
        });

        choice2.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
                // Action when choice 2 is clicked
                increaseLevels(0, 0, 0.15);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                choice2.setFillColor(Color.DARK_GRAY);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                choice2.setFillColor(Color.GRAY);
            }
        });

        choice3.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
                // Action when choice 3 is clicked
                increaseLevels(0.12, 0.12, 0.0);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                choice3.setFillColor(Color.DARK_GRAY);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                choice3.setFillColor(Color.GRAY);
            }
        });
    }

    // Helper method to increase the gauge levels by the specified percentages
    private void increaseLevels(double treePercentage, double airPercentage, double garbagePercentage) {
        int newTreeHeight = (int) (treeCountLevel.getHeight() + (int) (MAX_GAUGE_HEIGHT * treePercentage));
        int newAirHeight = (int) (airQualityLevel.getHeight() + (int) (MAX_GAUGE_HEIGHT * airPercentage));
        int newGarbageHeight = (int) (garbageCountLevel.getHeight() + (int) (MAX_GAUGE_HEIGHT * garbagePercentage));

        if (newTreeHeight <= MAX_GAUGE_HEIGHT) {
            treeCountLevel.setSize(treeCountLevel.getWidth(), newTreeHeight);
        } else {
            treeCountLevel.setSize(treeCountLevel.getWidth(), MAX_GAUGE_HEIGHT);
        }

        if (newAirHeight <= MAX_GAUGE_HEIGHT) {
            airQualityLevel.setSize(airQualityLevel.getWidth(), newAirHeight);
        } else {
            airQualityLevel.setSize(airQualityLevel.getWidth(), MAX_GAUGE_HEIGHT);
        }

        if (newGarbageHeight <= MAX_GAUGE_HEIGHT) {
            garbageCountLevel.setSize(garbageCountLevel.getWidth(), newGarbageHeight);
        } else {
            garbageCountLevel.setSize(garbageCountLevel.getWidth(), MAX_GAUGE_HEIGHT);
        }

        int currentGarbageCount = (int) (garbageCountLevel.getHeight() / MAX_GAUGE_HEIGHT * MAX_GARBAGE_COUNT);
        if (currentGarbageCount >= MAX_GARBAGE_COUNT) {
            gameOver();
        }

        int currentTreeCount = (int) (treeCountLevel.getHeight() / MAX_GAUGE_HEIGHT * 300);
        int currentAirQuality = (int) (airQualityLevel.getHeight() / MAX_GAUGE_HEIGHT * 300);

        if (currentTreeCount >= 300 && currentAirQuality >= 300) {
            winGame();
        }
    }

    private void winGame() {
        removeAll(); // Remove all elements from the existing window

        // Create a new window for the win message and buttons
        int messageWidth = getWidth();
        int messageHeight = getHeight();

        // Create a blue background rectangle
        GRect background = new GRect(0, 0, messageWidth, messageHeight);
        background.setFilled(true);
        background.setColor(Color.BLUE);
        add(background);

        // Create the win message in white letters
        String winMessage = "Congratulations! Your unwavering dedication has revitalized the world. ";
        GLabel winLabel = new GLabel(winMessage);
        winLabel.setFont("Arial-Bold-24");
        winLabel.setColor(Color.WHITE);
        double labelX = (messageWidth - winLabel.getWidth()) / 2;
        double labelY = (messageHeight - winLabel.getHeight()) / 2 - 50;
        add(winLabel, labelX, labelY);

        // Add the win buttons
        addGameOverButtons(messageWidth, messageHeight);
    }
    private void gameOver() {
        removeAll(); // Remove all elements from the existing window

        // Create a new window for the game over message and buttons
        int messageWidth = getWidth();
        int messageHeight = getHeight();

        // Create a red background rectangle
        GRect background = new GRect(0, 0, messageWidth, messageHeight);
        background.setFilled(true);
        background.setColor(Color.RED);
        add(background);

        // Create the game over message in big red letters
        GLabel gameOverLabel = new GLabel(GAME_OVER_MESSAGE);
        gameOverLabel.setFont("Arial-Bold-24");
        gameOverLabel.setColor(Color.WHITE);
        double labelX = (messageWidth - gameOverLabel.getWidth()) / 2;
        double labelY = (messageHeight - gameOverLabel.getHeight()) / 2 - 50;
        add(gameOverLabel, labelX, labelY);

        // Add the game over buttons
        addGameOverButtons(messageWidth, messageHeight);
    }

    // Method to add game over buttons
    private void addGameOverButtons(int messageWidth, int messageHeight) {
        // Button dimensions and positions
        int buttonWidth = 150;
        int buttonHeight = 50;
        int spacing = 20;
        double buttonX = (messageWidth - buttonWidth * 2 - spacing) / 2;
        double buttonY = messageHeight / 2 + 20;

        // Button to return to the starting screen
        GRect returnButton = new GRect(buttonX, buttonY, buttonWidth, buttonHeight);
        returnButton.setFilled(true);
        returnButton.setColor(Color.GRAY);
        add(returnButton);

        GLabel returnLabel = new GLabel("Return to Starting Screen");
        returnLabel.setFont("Arial-Bold-12");
        returnLabel.setColor(Color.WHITE);
        double returnLabelX = buttonX + (buttonWidth - returnLabel.getWidth()) / 2;
        double returnLabelY = buttonY + (buttonHeight + returnLabel.getHeight()) / 2;
        add(returnLabel, returnLabelX, returnLabelY);

        returnButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
                // Action when the return button is clicked
                JFrame gameWindowFrame = (JFrame) getJFrame();
                gameWindowFrame.dispose(); // Close the game window

                // Open the StartingScreen class
                StartingScreen.main(new String[0]);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                returnButton.setFillColor(Color.DARK_GRAY);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                returnButton.setFillColor(Color.GRAY);
            }
        });

        // Button to try again
        double tryAgainButtonX = buttonX + buttonWidth + spacing;
        GRect tryAgainButton = new GRect(tryAgainButtonX, buttonY, buttonWidth, buttonHeight);
        tryAgainButton.setFilled(true);
        tryAgainButton.setColor(Color.GRAY);
        add(tryAgainButton);

        GLabel tryAgainLabel = new GLabel("Try Again");
        tryAgainLabel.setFont("Arial-Bold-12");
        tryAgainLabel.setColor(Color.WHITE);
        double tryAgainLabelX = tryAgainButtonX + (buttonWidth - tryAgainLabel.getWidth()) / 2;
        double tryAgainLabelY = buttonY + (buttonHeight + tryAgainLabel.getHeight()) / 2;
        add(tryAgainLabel, tryAgainLabelX, tryAgainLabelY);

        tryAgainButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
                // Action when the try again button is clicked
                removeAll();
                run(); // Restart the game by calling the run method
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                tryAgainButton.setFillColor(Color.DARK_GRAY);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                tryAgainButton.setFillColor(Color.GRAY);
            }
        });
    }

    public static void main(String[] args) {
        new GameWindow().start(args);
    }
}