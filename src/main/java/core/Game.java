package core;

import core.boardSpaceTypes.BoardSpace;

import java.util.*;

public class Game {
    private final Board board;
    private final Bank bank;
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
        this.bank = new Bank();
        this.dice = new Dice();
        this.players = new ArrayList<>();
        this.currentPlayerIndex = 0;
        for (String name : playerNames) {
            boolean isDuplicate = false;
            for (Player p : players) {
                if (p.getName().equals(name)) {
                    isDuplicate = true;
                    break;
                }
            }

            if (isDuplicate) {
                throw new IllegalArgumentException("The name " + name + " is already in use.");
            }

            players.add(new Player(name));
        }

        this.currentPlayerIndex = 0;
    }

    public void start() {
        System.out.println("Starting game");
        determineOrder();

        boolean gameIsRunning = true;
        Scanner scanner = new Scanner(System.in);
        while (gameIsRunning) {
            Player currentPlayer = players.get(currentPlayerIndex);

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
            }

            currentPlayerIndex = (currentPlayerIndex + 1) % players.size();

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

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public Dice getDice() {
        return dice;
    }

    public Board getBoard() {
        return board;
    }
}