package core;

import core.boardSpaceTypes.BoardSpace;
import core.boardSpaceTypes.RailRoadSpace;


import java.util.ArrayList;

public class Player {
    private static final ArrayList<String> allPlayersUniqueNames = new ArrayList<>();

    private String name;
    private int position;
    private int money;
    private boolean inJail;
    private int firstRoll;
    private int secondRoll;
    private int totalRoll;

    private int turnsInJail;

    public Player(String name) {
        if (allPlayersUniqueNames.contains(name)) {
            throw new IllegalArgumentException("Player name \"" + name + "\" is not unique.");
        }
        allPlayersUniqueNames.add(name);
        this.name = name;
        this.position = 0;
        this.money = 1500;
        this.inJail = false;
        this.turnsInJail = 0;
    }

    public void move(int steps) {
        if (!inJail) {
            position = (position + steps) % 40;
            System.out.println(name + " moved to position " + position);
        } else {
            System.out.println(name + " is in Jail and can't move this turn.");
        }
    }

    public void goToJail() {
        this.position = 10;
        this.inJail = true;
        this.turnsInJail = 0;
        System.out.println(name + " has been sent to Jail.");
    }

    public boolean isInJail() {
        return this.inJail;
    }

    public void releaseFromJail() {
        if (turnsInJail >= 3) {
            this.inJail = false;
            this.turnsInJail = 0;
            System.out.println(name + " has served their time and is released from Jail.");
        } else {
            System.out.println(name + " is still in Jail for " + (3 - turnsInJail) + " more turn(s).");
        }
    }

    public void rollDice(Dice dice) {
        this.firstRoll = dice.roll();
        this.secondRoll = dice.roll();
        this.totalRoll = firstRoll + secondRoll;

        System.out.println(name + " rolled " + firstRoll + " & " + secondRoll + ", total = " + totalRoll);

        if (this.inJail && firstRoll == secondRoll) {
            releaseFromJail();
        }
    }

    public int getDiceRoll() {
        return totalRoll;
    }


    public String getName() {
        return name;
    }

    public int getPosition() {
        return position;
    }

    public void addMoney(int amount) {
        this.money += amount;
    }

    public void deductMoney(int amount) {
        this.money -= amount;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int amount) {
        this.money = amount;
    }

    public int getTurnsInJail() {
        return turnsInJail;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getNumberOfRailroadsOwned() {
        int[] railroadPositions = {5, 15, 25, 35};
        int count = 0;

        for (int pos : railroadPositions) {
            BoardSpace space = Board.getBoard().getSpace(pos);
            if (space instanceof RailRoadSpace railroad && this.equals(railroad.getOwner())) {
                count++;
            }
        }

        return count;
    }

    public boolean equals(Object obj) {
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Player other = (Player) obj;
        return name.equals(other.name);
    }

}
