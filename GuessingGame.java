package GuessingGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.RoundRectangle2D;
import java.util.Random;

// Custom button class with rounded corners
class RoundedButton extends JButton {
    private int radius;

    public RoundedButton(String label) {
        super(label);
        this.radius = 20; // Radius for rounded corners
        setContentAreaFilled(false); // Make background transparent
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(getBackground());
        g2.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), radius, radius));
        super.paintComponent(g);
    }

    @Override
    public void setBackground(Color bg) {
        super.setBackground(bg);
        repaint();
    }
}

public class GuessingGame extends JFrame {
    private int randomNumber;
    private int attempts;
    private final int maxAttempts = 10;

    private JTextField guessField;
    private JLabel feedbackLabel;
    private JLabel attemptsLabel;
    private JProgressBar progressBar;

    public GuessingGame() {
        setTitle("Guess the Number!");
        setSize(500, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(224, 247, 250));

        // Initialize components
        initializeComponents();

        // Initialize game
        resetGame();
    }

    private void initializeComponents() {
        // Panel for title and instruction
        JPanel topPanel = new JPanel(new GridBagLayout());
        topPanel.setBackground(new Color(224, 247, 250));

        // Title Label
        JLabel titleLabel = new JLabel("Guess the Number!");
        titleLabel.setFont(new Font("Helvetica", Font.BOLD, 28));
        titleLabel.setForeground(new Color(0, 121, 107));
        topPanel.add(titleLabel);

        // Instruction Label
        JLabel instructionLabel = new JLabel("Guess a number between 1 and 100:");
        instructionLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        instructionLabel.setForeground(Color.BLACK);
        topPanel.add(instructionLabel, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(10, 0, 0, 0), 0, 0));

        // Panel for input and buttons
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setBackground(new Color(224, 247, 250));

        // Input Field
        guessField = new JTextField(10);
        guessField.setFont(new Font("Arial", Font.PLAIN, 16));
        guessField.setBackground(Color.WHITE);
        guessField.setForeground(Color.BLACK);
        guessField.setBorder(BorderFactory.createLineBorder(new Color(0, 121, 107), 2));
        centerPanel.add(guessField, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(20, 0, 10, 10), 0, 0));

        // Guess Button
        RoundedButton guessButton = new RoundedButton("Guess");
        guessButton.setFont(new Font("Arial", Font.BOLD, 16));
        guessButton.setBackground(Color.WHITE);
        guessButton.setForeground(Color.BLACK);
        guessButton.setPreferredSize(new Dimension(120, 40)); // Set preferred size
        guessButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleGuess();
            }
        });
        centerPanel.add(guessButton, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(20, 10, 10, 0), 0, 0));

        // Feedback Label
        feedbackLabel = new JLabel("");
        feedbackLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        feedbackLabel.setForeground(Color.BLACK);
        centerPanel.add(feedbackLabel, new GridBagConstraints(0, 1, 2, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(10, 0, 10, 0), 0, 0));

        // Panel for attempts and progress bar
        JPanel bottomPanel = new JPanel(new GridBagLayout());
        bottomPanel.setBackground(new Color(224, 247, 250));

        // Attempts Label
        attemptsLabel = new JLabel("Attempts left: " + maxAttempts);
        attemptsLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        attemptsLabel.setForeground(Color.BLACK);
        bottomPanel.add(attemptsLabel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(10, 20, 20, 0), 0, 0));

        // Progress Bar
        progressBar = new JProgressBar(0, maxAttempts);
        progressBar.setValue(maxAttempts);
        progressBar.setStringPainted(true);
        progressBar.setForeground(new Color(0, 121, 107));
        progressBar.setBackground(Color.WHITE);
        progressBar.setBorder(BorderFactory.createLineBorder(new Color(0, 121, 107), 2));
        bottomPanel.add(progressBar, new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0,
                GridBagConstraints.EAST, GridBagConstraints.HORIZONTAL, new Insets(10, 20, 20, 20), 0, 0));

        // Reset Button
        RoundedButton resetButton = new RoundedButton("Reset");
        resetButton.setFont(new Font("Arial", Font.BOLD, 16));
        resetButton.setBackground(Color.WHITE);
        resetButton.setForeground(Color.BLACK);
        resetButton.setPreferredSize(new Dimension(120, 40)); // Set preferred size
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetGame();
            }
        });
        bottomPanel.add(resetButton, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(10, 20, 20, 20), 0, 0));

        // Add panels to the main frame
        add(topPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private void resetGame() {
        randomNumber = new Random().nextInt(100) + 1; // Random number between 1 and 100
        attempts = 0;
        guessField.setText(""); // Clear the input field
        feedbackLabel.setText(""); // Clear feedback
        attemptsLabel.setText("Attempts left: " + maxAttempts); // Reset attempts display
        progressBar.setValue(maxAttempts); // Reset progress bar
    }

    private void handleGuess() {
        String guessText = guessField.getText();
        if (guessText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a number.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int guess;
        try {
            guess = Integer.parseInt(guessText);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter a valid number.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (guess < 1 || guess > 100) {
            JOptionPane.showMessageDialog(this, "Please enter a number between 1 and 100.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            return;
        }

        attempts++;
        int attemptsLeft = maxAttempts - attempts;
        progressBar.setValue(attemptsLeft); // Update progress bar

        if (guess < randomNumber) {
            feedbackLabel.setText(guess + " is too low!");
            feedbackLabel.setForeground(Color.BLUE);
        } else if (guess > randomNumber) {
            feedbackLabel.setText(guess + " is too high!");
            feedbackLabel.setForeground(Color.RED);
        } else {
            feedbackLabel.setText("Congratulations! " + guess + " is correct!");
            feedbackLabel.setForeground(new Color(0, 121, 107));
            JOptionPane.showMessageDialog(this, "You guessed the number in " + attempts + " attempts!", "You Win!", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        attemptsLabel.setText("Attempts left: " + attemptsLeft);

        if (attemptsLeft <= 0) {
            feedbackLabel.setText("Game Over! The number was: " + randomNumber);
            feedbackLabel.setForeground(Color.RED);
            JOptionPane.showMessageDialog(this, "Game Over! The number was: " + randomNumber, "Game Over", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GuessingGame game = new GuessingGame();
            game.setVisible(true);
        });
    }
}
