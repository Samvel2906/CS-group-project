package monopoly.core;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import monopoly.core.boardSpaceTypes.*;
import monopoly.core.exceptions.NotEnoughMoneyToPayException;

public class MonopolyGUI {
    private JFrame frame;
    private JPanel boardPanel;
    private ArrayList<Player> players;
    private Player currentPlayer;
    private int currentPlayerIndex = 0;
    private Board board;
    private ArrayList<JLabel> playerLabels;
    private ArrayList<JPanel> spacePanels;
    private JButton rollDiceButton;
    private JLabel statusLabel;

    public MonopolyGUI() {
        String input = JOptionPane.showInputDialog(null, "Enter the number of players (2–6):");
        int numPlayers = 5;
        try {
            numPlayers = Integer.parseInt(input);
            if (numPlayers < 2 || numPlayers > 6) numPlayers = 5;
        } catch (NumberFormatException e) {
            numPlayers = 5;
        }

        board = Board.getBoard();
        players = new ArrayList<>();
        for (int i = 0; i < numPlayers; i++) {
            players.add(new Player("Player " + (i + 1)));
        }
        currentPlayer = players.get(0);

        playerLabels = new ArrayList<>();
        spacePanels = new ArrayList<>(40);
        for (int i = 0; i < 40; i++) spacePanels.add(null);

        frame = new JFrame("Monopoly Game");
        frame.setSize(1000, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        setupBoard();
        frame.setVisible(true);
    }

    private void setupBoard() {
        boardPanel = new JPanel(new BorderLayout());

        JPanel northPanel = new JPanel(new GridLayout(1, 10));
        JPanel southPanel = new JPanel(new GridLayout(1, 10));
        JPanel eastPanel = new JPanel(new GridLayout(10, 1));
        JPanel westPanel = new JPanel(new GridLayout(10, 1));
        JPanel centerPanel = new JPanel(new BorderLayout());

        statusLabel = new JLabel("Turn: " + currentPlayer.getName(), SwingConstants.CENTER);
        statusLabel.setFont(new Font("Arial", Font.BOLD, 16));
        centerPanel.add(statusLabel, BorderLayout.NORTH);

        for (int i = 0; i < 10; i++) {
            JPanel block = createBoardBlock(board.getSpace(i), "Space " + i);
            spacePanels.set(i, block);
            northPanel.add(block);
        }

        for (int i = 10; i < 20; i++) {
            JPanel block = createBoardBlock(board.getSpace(i), "Space " + i);
            spacePanels.set(i, block);
            eastPanel.add(block);
        }

        for (int i = 29; i >= 20; i--) {
            JPanel block = createBoardBlock(board.getSpace(i), "Space " + i);
            spacePanels.set(i, block);
            southPanel.add(block);
        }

        for (int i = 39; i >= 30; i--) {
            JPanel block = createBoardBlock(board.getSpace(i), "Space " + i);
            spacePanels.set(i, block);
            westPanel.add(block);
        }

        boardPanel.add(northPanel, BorderLayout.NORTH);
        boardPanel.add(southPanel, BorderLayout.SOUTH);
        boardPanel.add(westPanel, BorderLayout.WEST);
        boardPanel.add(eastPanel, BorderLayout.EAST);
        boardPanel.add(centerPanel, BorderLayout.CENTER);
        frame.add(boardPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        rollDiceButton = new JButton("Roll Dice");
        rollDiceButton.setFont(new Font("Arial", Font.BOLD, 14));
        rollDiceButton.addActionListener(e -> {
            try {
                handleRollDice(e);
            } catch (NotEnoughMoneyToPayException ex) {
                ex.printStackTrace();
            }
        });
        bottomPanel.add(rollDiceButton, BorderLayout.EAST);
        bottomPanel.add(statusLabel, BorderLayout.CENTER);
        frame.add(bottomPanel, BorderLayout.SOUTH);

        for (Player player : players) {
            JLabel label = new JLabel(player.getName());
            label.setFont(new Font("Arial", Font.PLAIN, 10));
            playerLabels.add(label);
            spacePanels.get(0).add(label);
        }

        frame.revalidate();
        frame.repaint();
    }

    private JPanel createBoardBlock(BoardSpace space, String defaultName) {
        JPanel panel = new JPanel(new FlowLayout());
        panel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        panel.setPreferredSize(new Dimension(80, 80));
        panel.setBackground(Color.WHITE);

        String name = (space != null) ? space.getName() : defaultName;
        JLabel nameLabel = new JLabel("<html><center>" + name + "</center></html>", SwingConstants.CENTER);
        nameLabel.setFont(new Font("Arial", Font.PLAIN, 10));
        nameLabel.setForeground(Color.BLACK);

        panel.add(nameLabel);
        return panel;
    }

    private void handleRollDice(ActionEvent e) throws NotEnoughMoneyToPayException {
        Player player = players.get(currentPlayerIndex);
        Dice dice = new Dice();
        int roll1 = dice.roll();
        int roll2 = dice.roll();
        int rollTotal = roll1 + roll2;

        JOptionPane.showMessageDialog(frame, player.getName() + " rolled " + roll1 + " & " + roll2 + ", total = " + rollTotal);

        int oldPos = player.getPosition();
        player.move(rollTotal);
        int newPos = player.getPosition();

        JOptionPane.showMessageDialog(frame, player.getName() + " moved to position " + newPos);

        BoardSpace space = board.getSpace(player.getPosition());

        if (space instanceof ChanceSpace) {
            handleChanceSpace((ChanceSpace) space);
        } else if (space instanceof PropertySpace prop) {
            handleProperty(prop);
        } else if (space instanceof GoToJailSpace) {
            player.goToJail();
            JOptionPane.showMessageDialog(frame, player.getName() + " was sent to jail.");
        }

        updatePlayerPosition(currentPlayerIndex, oldPos, newPos);

        JOptionPane.showMessageDialog(frame, player.getName() + "'s remaining balance: $" + player.getMoney());

        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
        currentPlayer = players.get(currentPlayerIndex);
        statusLabel.setText("Turn: " + currentPlayer.getName());
    }

    private void updatePlayerPosition(int playerIndex, int oldPos, int newPos) {
        JLabel label = playerLabels.get(playerIndex);
        JPanel oldPanel = spacePanels.get(oldPos);
        JPanel newPanel = spacePanels.get(newPos);

        oldPanel.remove(label);
        newPanel.add(label);

        oldPanel.revalidate();
        oldPanel.repaint();
        newPanel.revalidate();
        newPanel.repaint();
    }

    private void handleChanceSpace(ChanceSpace space) {
        JOptionPane.showMessageDialog(frame, "Player " + currentPlayer.getName() + " landed on a Chance space!");
        JOptionPane.showMessageDialog(frame, "Chance Card: " + space.getName());

        BoardSpace landedSpace = board.getSpace(currentPlayer.getPosition());
        if (landedSpace instanceof PropertySpace) {
            handleProperty((PropertySpace) landedSpace);
        }
    }

    private void handleProperty(PropertySpace property) {
        if (property.getOwner() == null) {
            int choice = JOptionPane.showConfirmDialog(
                    frame,
                    "Do you want to buy " + property.getName() + " for $" + property.getPrice() + "?",
                    "Property Purchase",
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.QUESTION_MESSAGE
            );

            if (choice == JOptionPane.OK_OPTION) {
                currentPlayer.buy(property, true);
                JOptionPane.showMessageDialog(frame, currentPlayer.getName() + " bought " + property.getName() + "!");
            } else {
                JOptionPane.showMessageDialog(frame, currentPlayer.getName() + " chose not to buy " + property.getName() + ".");
            }
        } else {
            JOptionPane.showMessageDialog(frame, "This property is owned by " + property.getOwner().getName() + ".");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MonopolyGUI::new);
    }

}
