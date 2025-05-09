package monopoly;

import monopoly.core.*;
import monopoly.core.boardSpaceTypes.BoardSpace;
import monopoly.core.boardSpaceTypes.OwnableBoardSpace;
import monopoly.core.boardSpaceTypes.PropertySpace;
import monopoly.core.exceptions.NotEnoughMoneyToPayException;

import java.util.*;

public class Game {
    private final Board board;
    private final Dice dice;
    private final ArrayList<Player> players;
    private int currentPlayerIndex;
    private int timesPlayed;

    public Game(ArrayList<String> playerNames) {
        if (playerNames.size() > 6) {
            throw new IllegalArgumentException("A maximum of 6 players is allowed.");
        }
        if (playerNames.size() < 2) {
            throw new IllegalArgumentException("A minimum of 2 players is required.");
        }
        this.board = Board.getBoard();
        this.dice = new Dice();
        this.players = new ArrayList<>();
        this.currentPlayerIndex = 0;
    }

    public void start() {
        System.out.println("Starting game");
        determineOrder();

        boolean gameIsRunning = true;
        Scanner scanner = new Scanner(System.in);
        while (gameIsRunning) {
            Player currentPlayer = players.get(currentPlayerIndex);
            if (timesPlayed == 3) {
                currentPlayer.goToJail();
            } else {
                try {
                    System.out.println("\nIt's " + currentPlayer.getName() + "'s turn.");
                    System.out.print("Press Enter to roll the dice.");
                    scanner.nextLine();
                    int roll = currentPlayer.rollDice(dice);
                    System.out.println(currentPlayer.getName() + " rolled " + currentPlayer.getFirstRoll() + " and " + currentPlayer.getSecondRoll() + ", " +
                            "together" + currentPlayer.getTotalRoll());
                    currentPlayer.move(roll);
                    BoardSpace space = board.getSpace(currentPlayer.getPosition());

                    System.out.println(currentPlayer.getName() + " landed on " + space.getName() + ".");
                    if (currentPlayer.getFirstRoll() == currentPlayer.getSecondRoll()) {
                        timesPlayed++;
                    } else {
                        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
                    }
                } catch (NotEnoughMoneyToPayException e) {
                    System.out.println("The player " + currentPlayer.getName() + " does not have enough money to pay, therefor he or she is eliminated");
                    this.eliminatePlayer(currentPlayer);
                    if (players.size() == 1) {
                        gameIsRunning = false;
                        System.out.println(players.get(0).getName() + " won the game.");
                    } else {
                        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
                    }
                }
            }
        }


    }

    public void determineOrder() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Each player rolls the dice to determine who goes first:");


        for (Player player : players) {
            System.out.print(player.getName() + ", press Enter to roll.");
            scanner.nextLine();

            int roll = dice.roll();
            player.setFirstRoll(roll);
            System.out.println(player.getName() + " rolled a " + roll + "\n");
        }


        for (int i = 0; i < players.size(); i++) {
            for (int j = i + 1; j < players.size(); j++) {
                if (players.get(i).getFirstRoll() < players.get(j).getFirstRoll()) {
                    Player temp = players.get(i);
                    players.set(i, players.get(j));
                    players.set(j, temp);
                }

            }
        }
    }

    public void eliminatePlayer(Player player) {


        for (BoardSpace space : board.getSpaces()) {
            if (space instanceof OwnableBoardSpace) {
                OwnableBoardSpace ownableBoardSpace = (OwnableBoardSpace) space;
                if (ownableBoardSpace.getOwner().equals(player)) {
                    ownableBoardSpace.setOwner(null);
                }
            }
        }

        players.remove(player);
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public Dice getDice() {
        return dice;
    }

    public Board getBoard() {
        return board;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<String> playerNames = new ArrayList<>();

        System.out.println("Welcome to Monopoly!");
        int numberOfPlayers = 0;
        while (true) {
            System.out.print("Enter number of players (2–6): ");
            try {
                numberOfPlayers = Integer.parseInt(scanner.nextLine());
                if (numberOfPlayers < 2 || numberOfPlayers > 6) {
                    System.out.println("Number of players must be between 2 and 6.");
                } else {
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
        for (int i = 0; i < numberOfPlayers; i++) {
            while (true) {
                System.out.print("Enter player name: ");
                String playerName = scanner.nextLine();
                if (playerNames.contains(playerName)) {
                    System.out.println("Player " + playerName + " is already in use.");
                } else if (playerName.trim().equals("")) {
                    System.out.println("Please name cannot be empty.");
                } else {
                    playerNames.add(playerName);
                    break;
                }
            }
        }
        Game game = new Game(playerNames);
        game.start();

    }
}