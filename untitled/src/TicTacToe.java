import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TicTacToe extends JFrame implements ActionListener {
    private JButton[][] buttons = new JButton[3][3];
    private char currentPlayer = 'X';
    private JLabel statusLabel;

    public TicTacToe() {
        setTitle("Tic-Tac-Toe Game");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Status display
        statusLabel = new JLabel("Player X's Turn", SwingConstants.CENTER);
        statusLabel.setFont(new Font("Arial", Font.BOLD, 18));
        add(statusLabel, BorderLayout.NORTH);

        // Grid panel
        JPanel gridPanel = new JPanel(new GridLayout(3, 3));
        Font btnFont = new Font("Arial", Font.BOLD, 48);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j] = new JButton("");
                buttons[i][j].setFont(btnFont);
                buttons[i][j].setFocusPainted(false);
                buttons[i][j].addActionListener(this);
                gridPanel.add(buttons[i][j]);
            }
        }

        add(gridPanel, BorderLayout.CENTER);

        // Restart Button
        JButton restartBtn = new JButton("Restart Game");
        restartBtn.setFont(new Font("Arial", Font.PLAIN, 16));
        restartBtn.addActionListener(e -> resetGame());
        add(restartBtn, BorderLayout.SOUTH);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton clicked = (JButton) e.getSource();
        if (!clicked.getText().equals("")) return;

        clicked.setText(String.valueOf(currentPlayer));
        clicked.setForeground(currentPlayer == 'X' ? Color.BLUE : Color.RED);

        if (checkWin()) {
            statusLabel.setText("Player " + currentPlayer + " Wins!");
            highlightWinningCombo();
            disableAll();
        } else if (checkDraw()) {
            statusLabel.setText("It's a Draw!");
        } else {
            currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
            statusLabel.setText("Player " + currentPlayer + "'s Turn");
        }
    }

    private boolean checkWin() {
        // Rows, columns, diagonals
        for (int i = 0; i < 3; i++) {
            if (same(buttons[i][0], buttons[i][1], buttons[i][2])) return true;
            if (same(buttons[0][i], buttons[1][i], buttons[2][i])) return true;
        }
        if (same(buttons[0][0], buttons[1][1], buttons[2][2])) return true;
        if (same(buttons[0][2], buttons[1][1], buttons[2][0])) return true;
        return false;
    }

    private boolean same(JButton b1, JButton b2, JButton b3) {
        return !b1.getText().equals("") &&
                b1.getText().equals(b2.getText()) &&
                b2.getText().equals(b3.getText());
    }

    private boolean checkDraw() {
        for (JButton[] row : buttons)
            for (JButton btn : row)
                if (btn.getText().equals("")) return false;
        return true;
    }

    private void highlightWinningCombo() {
        for (int i = 0; i < 3; i++) {
            if (same(buttons[i][0], buttons[i][1], buttons[i][2])) {
                buttons[i][0].setBackground(Color.GREEN);
                buttons[i][1].setBackground(Color.GREEN);
                buttons[i][2].setBackground(Color.GREEN);
                return;
            }
            if (same(buttons[0][i], buttons[1][i], buttons[2][i])) {
                buttons[0][i].setBackground(Color.GREEN);
                buttons[1][i].setBackground(Color.GREEN);
                buttons[2][i].setBackground(Color.GREEN);
                return;
            }
        }
        if (same(buttons[0][0], buttons[1][1], buttons[2][2])) {
            buttons[0][0].setBackground(Color.GREEN);
            buttons[1][1].setBackground(Color.GREEN);
            buttons[2][2].setBackground(Color.GREEN);
        } else if (same(buttons[0][2], buttons[1][1], buttons[2][0])) {
            buttons[0][2].setBackground(Color.GREEN);
            buttons[1][1].setBackground(Color.GREEN);
            buttons[2][0].setBackground(Color.GREEN);
        }
    }

    private void disableAll() {
        for (JButton[] row : buttons)
            for (JButton btn : row)
                btn.setEnabled(false);
    }

    private void resetGame() {
        currentPlayer = 'X';
        statusLabel.setText("Player X's Turn");
        for (JButton[] row : buttons)
            for (JButton btn : row) {
                btn.setText("");
                btn.setEnabled(true);
                btn.setBackground(null);
            }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TicTacToe::new);
    }
}
