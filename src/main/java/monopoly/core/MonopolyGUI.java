package monopoly.core;
import javax.swing.*;
import java.awt.*;
import java.util.Random;

    public class MonopolyGUI extends JFrame {
        private JButton[] tiles = new JButton[40];
        private JLabel messageLabel;
        private JLabel diceLabel;
        private JLabel centerLabel = new JLabel("", SwingConstants.CENTER);
        private int[] playerPositions;
        private String[] playerIcons;
        private int currentPlayer = 0;

        private final String[] tileNames = {
                "GO", "Mediterranean Avenue", "Community Chest", "Baltic Avenue", "Income Tax",
                "Reading Railroad", "Oriental Avenue", "Chance", "Vermont Avenue", "Connecticut Avenue",
                "Jail / Just Visiting", "St. Charles Place", "Electric Company", "States Avenue", "Virginia Avenue",
                "Pennsylvania Railroad", "St. James Place", "Community Chest", "Tennessee Avenue", "New York Avenue",
                "Free Parking", "Kentucky Avenue", "Chance", "Indiana Avenue", "Illinois Avenue",
                "B&O Railroad", "Atlantic Avenue", "Ventnor Avenue", "Water Works", "Marvin Gardens",
                "Go to Jail", "Pacific Avenue", "North Carolina Avenue", "Community Chest", "Pennsylvania Avenue",
                "Short Line Railroad", "Chance", "Park Place", "Luxury Tax", "Boardwalk"
        };

        private final String[] tileTypes = {
                "Start", "Property", "Card", "Property", "Tax",
                "Railroad", "Property", "Card", "Property", "Property",
                "Jail", "Property", "Utility", "Property", "Property",
                "Railroad", "Property", "Card", "Property", "Property",
                "Free Parking", "Property", "Card", "Property", "Property",
                "Railroad", "Property", "Property", "Utility", "Property",
                "Go to Jail", "Property", "Property", "Card", "Property",
                "Railroad", "Card", "Property", "Tax", "Property"
        };

        private final Integer[] tilePrices = {
                null, 60, null, 60, null,
                200, 100, null, 100, 120,
                null, 140, 150, 140, 160,
                200, 180, null, 180, 200,
                null, 220, null, 220, 240,
                200, 260, 260, 150, 280,
                null, 300, 300, null, 320,
                200, null, 350, null, 400
        };

        public MonopolyGUI() {
            String input = JOptionPane.showInputDialog(this, "Enter number of players (2-5):");
            int numPlayers = 2;
            try {
                numPlayers = Integer.parseInt(input);
                if (numPlayers < 2 || numPlayers > 5) {
                    throw new NumberFormatException();
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Invalid input! Defaulting to 2 players.");
            }

            playerPositions = new int[numPlayers];
            playerIcons = new String[numPlayers];


            for (int i = 0; i < numPlayers; i++) {
                playerIcons[i] = "Player" + (i + 1);
            }

            setTitle("Monopoly Board");
            setSize(900, 900);
            setDefaultCloseOperation(EXIT_ON_CLOSE);
            setLayout(new BorderLayout());

            JPanel boardPanel = new JPanel(new GridLayout(11, 11));

            for (int row = 0; row < 11; row++) {
                for (int col = 0; col < 11; col++) {
                    boolean isTop = row == 0 && col < 10;
                    boolean isRight = col == 10 && row < 10;
                    boolean isBottom = row == 10 && col > 0;
                    boolean isLeft = col == 0 && row > 0;
                    boolean isCenter = row == 5 && col == 5;

                    if (isTop || isRight || isBottom || isLeft) {
                        JButton tile = new JButton();
                        tile.setFont(new Font("Arial", Font.PLAIN, 9));
                        tile.setMargin(new Insets(2, 2, 2, 2));

                        int tileNumber = 0;
                        if (isTop) tileNumber = col + 1;
                        else if (isRight) tileNumber = 10 + row;
                        else if (isBottom) tileNumber = 30 - col;
                        else if (isLeft) tileNumber = 40 - row;

                        int index = tileNumber - 1;
                        tile.setText("<html><center>" + tileNames[index] + "</center></html>");

                        int finalIndex = index;
                        tile.addActionListener(e -> {
                            String name = tileNames[finalIndex];
                            String type = tileTypes[finalIndex];
                            String priceInfo = (tilePrices[finalIndex] != null)
                                    ? ("Price: $" + tilePrices[finalIndex])
                                    : "Not for sale";


                            String imagePath = "images/" + name.toLowerCase().replaceAll("[ /]", "_") + ".png";
                            ImageIcon icon = new ImageIcon(imagePath);
                            JLabel label = new JLabel("<html>" + name + "<br>Type: " + type + "<br>" + priceInfo + "</html>");
                            label.setIcon(icon);
                            label.setHorizontalTextPosition(SwingConstants.RIGHT);
                            JOptionPane.showMessageDialog(this, label, "Tile Info", JOptionPane.INFORMATION_MESSAGE);
                        });

                        tiles[index] = tile;
                        boardPanel.add(tile);
                    }
                    else if (row == 5 && col == 5) {
                        centerLabel.setFont(new Font("Arial", Font.BOLD, 26));
                        centerLabel.setOpaque(true);
                        centerLabel.setBackground(new Color(255, 255, 200));
                        centerLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
                        centerLabel.setPreferredSize(new Dimension(150, 100));
                        centerLabel.setText("Free Parking");
                        boardPanel.add(centerLabel);
                    }
                    else if ((row == 5 && col == 4) || (row == 5 && col == 6)) {
                        JButton tile = new JButton(getRandomJailCard());
                        tile.setFont(new Font("Arial", Font.PLAIN, 9));
                        tile.setMargin(new Insets(2, 2, 2, 2));
                        tile.addActionListener(e -> showJailCardMessage());
                        boardPanel.add(tile);
                    }
                    else {
                        boardPanel.add(new JLabel());
                    }
                }
            }

            JButton rollButton = new JButton("Roll Dice");
            diceLabel = new JLabel("🎲 🎲", SwingConstants.CENTER);
            messageLabel = new JLabel("Click Roll to move Player " + (currentPlayer + 1) + " (" + playerIcons[currentPlayer] + ")");
            JPanel controlPanel = new JPanel(new BorderLayout());
            controlPanel.add(rollButton, BorderLayout.WEST);
            controlPanel.add(diceLabel, BorderLayout.CENTER);
            controlPanel.add(messageLabel, BorderLayout.EAST);

            rollButton.addActionListener(e -> rollDiceAndMove());

            add(boardPanel, BorderLayout.CENTER);
            add(controlPanel, BorderLayout.SOUTH);
            setVisible(true);
            updateBoard();
        }

        private void rollDiceAndMove() {
            int die1 = (int)(Math.random() * 6) + 1;
            int die2 = (int)(Math.random() * 6) + 1;
            int roll = die1 + die2;
            diceLabel.setText("🎲 " + getDieFace(die1) + " " + getDieFace(die2));

            playerPositions[currentPlayer] = (playerPositions[currentPlayer] + roll) % 40;

            centerLabel.setText("<html><center>Rolled:<br>" + die1 + " + " + die2 + " = " + roll + "</center></html>");
            messageLabel.setText("Player " + (currentPlayer + 1) + " moved to " +
                    tileNames[playerPositions[currentPlayer]] + ". Next: Player " + (((currentPlayer + 1) % playerPositions.length) + 1));

            currentPlayer = (currentPlayer + 1) % playerPositions.length;
            updateBoard();
        }

        private String getDieFace(int num) {
            return switch (num) {
                case 1 -> "⚀";
                case 2 -> "⚁";
                case 3 -> "⚂";
                case 4 -> "⚃";
                case 5 -> "⚄";
                case 6 -> "⚅";
                default -> "?";
            };
        }

        private void updateBoard() {
            for (int i = 0; i < tiles.length; i++) {
                StringBuilder label = new StringBuilder("<html><center>");
                label.append(tileNames[i]);

                for (int p = 0; p < playerPositions.length; p++) {
                    if (playerPositions[p] == i) {
                        label.append("<br>").append(playerIcons[p]);
                    }
                }

                label.append("</center></html>");
                tiles[i].setText(label.toString());
            }
        }

        private String getRandomJailCard() {
            String[] jailTasks = {"Pick a Chance Card", "Pick a Community Chest Card"};
            Random random = new Random();
            return jailTasks[random.nextInt(jailTasks.length)];
        }

        private void showJailCardMessage() {
            String[] cards = {"Advance to GO (Collect $200)", "Go to Jail", "Pay $50 in taxes", "Take a ride on the Reading Railroad"};
            Random random = new Random();
            String card = cards[random.nextInt(cards.length)];

            JOptionPane.showMessageDialog(this, "Card Drawn: " + card, "Jail Card", JOptionPane.INFORMATION_MESSAGE);
        }

        public static void main(String[] args) {
            new MonopolyGUI();
        }
    }




