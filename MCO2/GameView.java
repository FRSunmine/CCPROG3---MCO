import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GameView extends JFrame {
    private Board board;
    private JLabel sunLabel, timerLabel;
    private JButton sunflowerBtn, peashooterBtn;
    private JButton[][] gridButtons;
    private JPanel boardPanel;

    public GameView(Board board) {
        this.board = board;
        setTitle("Plants vs Zombies - School Project");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Board panel with grid of buttons
        boardPanel = new JPanel(new GridLayout(board.getRows(), board.getCols()));
        gridButtons = new JButton[board.getRows()][board.getCols()];
        for (int r = 0; r < board.getRows(); r++) {
            for (int c = 0; c < board.getCols(); c++) {
                JButton btn = new JButton();
                btn.setPreferredSize(new Dimension(60, 60));
                btn.setFocusPainted(false);
                gridButtons[r][c] = btn;
                boardPanel.add(btn);
            }
        }
        add(boardPanel, BorderLayout.CENTER);

        // Controls
        JPanel controls = new JPanel();
        sunflowerBtn = new JButton("Add Sunflower");
        peashooterBtn = new JButton("Add Peashooter");
        controls.add(sunflowerBtn);
        controls.add(peashooterBtn);

        sunLabel = new JLabel("Sun: 50");
        controls.add(sunLabel);

        timerLabel = new JLabel("Time: 00:00");
        controls.add(timerLabel);

        add(controls, BorderLayout.SOUTH);
    }

    // For controller to add listeners to plant buttons
    public void addSunflowerButtonListener(ActionListener l) {
        sunflowerBtn.addActionListener(l);
    }
    public void addPeashooterButtonListener(ActionListener l) {
        peashooterBtn.addActionListener(l);
    }

    // For controller to add listeners to grid buttons
    public void addGridButtonListener(int row, int col, ActionListener l) {
        gridButtons[row][col].addActionListener(l);
    }

    public void setSun(int sun) {
        sunLabel.setText("Sun: " + sun);
    }

    public void setTimer(int seconds) {
        int min = seconds / 60, sec = seconds % 60;
        timerLabel.setText(String.format("Time: %02d:%02d", min, sec));
    }

    // Update the grid display based on board state
    public void refresh() {
        for (int r = 0; r < board.getRows(); r++) {
            for (int c = 0; c < board.getCols(); c++) {
                JButton btn = gridButtons[r][c];
                Zombie z = board.getZombieAt(r, c);
                Plant p = board.getPlantAt(r, c);
                if (z != null) {
                    btn.setText("Z");
                    btn.setBackground(Color.LIGHT_GRAY);
                } else if (p != null) {
                    btn.setText(p.getType().substring(0, 1));
                    btn.setBackground(p instanceof Sunflower ? Color.YELLOW : Color.GREEN);
                } else {
                    btn.setText("");
                    btn.setBackground(null);
                }
            }
        }
    }
}