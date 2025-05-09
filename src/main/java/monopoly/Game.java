package monopoly;

import monopoly.core.*;
import monopoly.core.boardSpaceTypes.BoardSpace;
import monopoly.core.boardSpaceTypes.OwnableBoardSpace;
import monopoly.core.exceptions.NotEnoughMoneyToPayException;

import java.util.*;

public class Game {
    private final Board board;
    private final Dice dice;
    private int currentPlayerIndex;
    private int timesPlayed;

    public Game(ArrayList<Player> players) {
        if (players.size() > 6) {
            throw new IllegalArgumentException("A maximum of 6 players is allowed.");
        }
        if (players.size() < 2) {
            throw new IllegalArgumentException("A minimum of 2 players is required.");
        }
        this.board = Board.getBoard();
        this.dice = new Dice();
        this.currentPlayerIndex = 0;
        board.setPlayers(players);
    }

    public int getCurrentPlayerIndex() {
        return currentPlayerIndex;
    }

    public void setCurrentPlayerIndex(int index) {
        this.currentPlayerIndex = index;
    }

    public void start() {
        System.out.println("Starting game");
        determineOrder();

        boolean gameIsRunning = true;
        Scanner scanner = new Scanner(System.in);
        while (gameIsRunning) {
            Player currentPlayer = board.getPlayers().get(currentPlayerIndex);
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
                        currentPlayerIndex = (currentPlayerIndex + 1) % board.getPlayers().size();
                    }
                } catch (NotEnoughMoneyToPayException e) {
                    System.out.println("The player " + currentPlayer.getName() + " does not have enough money to pay, therefor he or she is eliminated");
                    this.eliminatePlayer(currentPlayer);
                    if (board.getPlayers().size() == 1) {
                        gameIsRunning = false;
                        System.out.println(board.getPlayers().get(0).getName() + " won the game.");
                    } else {
                        currentPlayerIndex = (currentPlayerIndex + 1) % board.getPlayers().size();
                    }
                }
            }
        }


    }

    public void determineOrder() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Each player rolls the dice to determine who goes first:");


        for (Player player : board.getPlayers()) {
            System.out.print(player.getName() + ", press Enter to roll.");
            scanner.nextLine();

            int roll = dice.roll();
            player.setFirstRoll(roll);
            System.out.println(player.getName() + " rolled a " + roll + "\n");
        }


        for (int i = 0; i < board.getPlayers().size(); i++) {
            for (int j = i + 1; j < board.getPlayers().size(); j++) {
                if (board.getPlayers().get(i).getFirstRoll() < board.getPlayers().get(j).getFirstRoll()) {
                    Player temp = board.getPlayers().get(i);
                    board.getPlayers().set(i, board.getPlayers().get(j));
                    board.getPlayers().set(j, temp);
                }

            }
        }
        int number = 1;
        for (Player player : board.getPlayers()) {
            System.out.println(number + ". " + player.getName());
            number++;
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

        board.getPlayers().remove(player);
    }

    public ArrayList<Player> getPlayers() {
        return board.getPlayers();
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
        ArrayList<Player> players = new ArrayList<>();
        for (int i = 0; i < numberOfPlayers; i++) {
            players.add(new Player(playerNames.get(i)));
        }
        Game game = new Game(players);
        game.start();

    }
}