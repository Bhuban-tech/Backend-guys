import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TicTacToeSwing extends JFrame implements ActionListener {
    private JButton[][] buttons = new JButton[3][3];
    private char currentPlayer = 'X';
    private JLabel statusLabel;

    public TicTacToeSwing() {
        setTitle("Tic Tac Toe - Java Swing");
        setSize(400, 450);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Status panel
        statusLabel = new JLabel("Player X's turn");
        statusLabel.setFont(new Font("Arial", Font.BOLD, 16));
        statusLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(statusLabel, BorderLayout.NORTH);

        // Game grid
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 3));
        Font font = new Font("Arial", Font.BOLD, 60);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j] = new JButton("");
                buttons[i][j].setFont(font);
                buttons[i][j].setFocusPainted(false);
                buttons[i][j].addActionListener(this);
                panel.add(buttons[i][j]);
            }
        }

        add(panel, BorderLayout.CENTER);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton clicked = (JButton) e.getSource();

        if (!clicked.getText().equals("")) {
            return; // already clicked
        }

        clicked.setText(String.valueOf(currentPlayer));

        if (checkWin()) {
            statusLabel.setText("Player " + currentPlayer + " wins!");
            disableAllButtons();
        } else if (isBoardFull()) {
            statusLabel.setText("It's a draw!");
        } else {
            currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
            statusLabel.setText("Player " + currentPlayer + "'s turn");
        }
    }

    private boolean checkWin() {
        // Check rows and columns
        for (int i = 0; i < 3; i++) {
            if (same(buttons[i][0], buttons[i][1], buttons[i][2])) return true;
            if (same(buttons[0][i], buttons[1][i], buttons[2][i])) return true;
        }

        // Diagonals
        return same(buttons[0][0], buttons[1][1], buttons[2][2]) ||
                same(buttons[0][2], buttons[1][1], buttons[2][0]);
    }

    private boolean same(JButton b1, JButton b2, JButton b3) {
        String s1 = b1.getText(), s2 = b2.getText(), s3 = b3.getText();
        return !s1.equals("") && s1.equals(s2) && s2.equals(s3);
    }

    private boolean isBoardFull() {
        for (JButton[] row : buttons) {
            for (JButton b : row) {
                if (b.getText().equals("")) return false;
            }
        }
        return true;
    }

    private void disableAllButtons() {
        for (JButton[] row : buttons) {
            for (JButton b : row) {
                b.setEnabled(false);
            }
        }
    }

    public static void main(String[] args) {
        new TicTacToeSwing();
    }
}
