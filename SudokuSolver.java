import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SudokuSolverGUI extends JFrame {
    private static final int SIZE = 9;
    private JTextField[][] cells = new JTextField[SIZE][SIZE];
    private JButton solveButton;
    private JButton resetButton;

    public SudokuSolverGUI() {
        setTitle("Sudoku Solver");
        setSize(450, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        
        JPanel gridPanel = new JPanel();
        gridPanel.setLayout(new GridLayout(SIZE, SIZE));

        
        for (int r = 0; r < SIZE; r++) {
            for (int c = 0; c < SIZE; c++) {
                cells[r][c] = new JTextField();
                cells[r][c].setHorizontalAlignment(JTextField.CENTER);
                cells[r][c].setFont(new Font("Arial", Font.PLAIN, 24));
                cells[r][c].setBorder(BorderFactory.createLineBorder(Color.BLACK));

                
                if ((r / 3 + c / 3) % 2 == 0) {
                    cells[r][c].setBackground(new Color(230, 230, 250)); // Lavender
                } else {
                    cells[r][c].setBackground(new Color(255, 255, 255)); // White
                }

                gridPanel.add(cells[r][c]);
            }
        }

        
        solveButton = new JButton("Solve");
        resetButton = new JButton("Reset");
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(solveButton);
        buttonPanel.add(resetButton);

        
        solveButton.addActionListener(new SolveButtonListener());
        resetButton.addActionListener(new ResetButtonListener());

        
        add(gridPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private class SolveButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int[][] grid = new int[SIZE][SIZE];

            
            for (int r = 0; r < SIZE; r++) {
                for (int c = 0; c < SIZE; c++) {
                    String text = cells[r][c].getText();
                    grid[r][c] = text.isEmpty() ? 0 : Integer.parseInt(text);
                }
            }

            
            if (solveSudoku(grid)) {
                
                for (int r = 0; r < SIZE; r++) {
                    for (int c = 0; c < SIZE; c++) {
                        cells[r][c].setText(grid[r][c] == 0 ? "" : String.valueOf(grid[r][c]));
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "No solution exists.");
            }
        }
    }

    private class ResetButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            
            for (int r = 0; r < SIZE; r++) {
                for (int c = 0; c < SIZE; c++) {
                    cells[r][c].setText("");
                }
            }
        }
    }

    
    private boolean isSafe(int[][] grid, int row, int col, int num) {
        for (int x = 0; x < SIZE; x++) {
            if (grid[row][x] == num || grid[x][col] == num) {
                return false;
            }
        }

        int startRow = row - row % 3, startCol = col - col % 3;
        for (int r = 0; r < 3; r++) {
            for (int d = 0; d < 3; d++) {
                if (grid[r + startRow][d + startCol] == num) {
                    return false;
                }
            }
        }
        return true;
    }

    // Backtracking method to solve the Sudoku
    private boolean solveSudoku(int[][] grid) {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if (grid[row][col] == 0) {
                    for (int num = 1; num <= SIZE; num++) {
                        if (isSafe(grid, row, col, num)) {
                            grid[row][col] = num;

                            if (solveSudoku(grid)) {
                                return true;
                            }

                            grid[row][col] = 0; 
                        }
                    }
                    return false; 
                }
            }
        }
        return true; 
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SudokuSolverGUI gui = new SudokuSolverGUI();
            gui.setVisible(true);
        });
    }
}
