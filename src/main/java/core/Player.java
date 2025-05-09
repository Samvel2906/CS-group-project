package core;

import core.boardSpaceTypes.*;


import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;

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
    private Color color;

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
            if (position > 40) {
                this.addMoney(200);
            }
            position = (position + steps) % 40;
            System.out.println(name + " moved to position " + position);
            if (Board.getBoard().getSpace(position) instanceof RailRoadSpace) {
                RailRoadSpace.landOn(this, position);

            } else if (Board.getBoard().getSpace(position) instanceof PropertySpace) {
                PropertySpace.landOn(this, position);

            } else if (Board.getBoard().getSpace(position) instanceof ChanceSpace) {
                ChanceSpace.landOn(this, position);

            } else if (Board.getBoard().getSpace(position) instanceof CommunityChestSpace) {
                CommunityChestSpace.landOn(this, position);

            } else if (Board.getBoard().getSpace(position) instanceof FreeParkingSpace) {
                FreeParkingSpace.landOn(this, position);

            } else if (Board.getBoard().getSpace(position) instanceof GoSpace) {
                GoSpace.landOn(this, position);

            } else if (Board.getBoard().getSpace(position) instanceof GoToJailSpace) {
                GoToJailSpace.landOn(this, position);

            } else if (Board.getBoard().getSpace(position) instanceof JailSpace) {
                JailSpace.landOn(this, position);

            } else if (Board.getBoard().getSpace(position) instanceof TaxSpace) {
                TaxSpace.landOn(this, position);

            } else if (Board.getBoard().getSpace(position) instanceof UtilitySpace) {
                UtilitySpace.landOn(this, position);

            }


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

    public int rollDice(Dice dice) {
        this.firstRoll = dice.roll();
        this.secondRoll = dice.roll();
        this.totalRoll = firstRoll + secondRoll;

        System.out.println(name + " rolled " + firstRoll + " & " + secondRoll + ", total = " + totalRoll);

        if (this.inJail && firstRoll == secondRoll) {
            releaseFromJail();
        }
        return firstRoll + secondRoll;
    }

//    public void buy(BoardSpace space, boolean wantsToBuy) {
//        if (space instanceof OwnableSpace ownable) {
//            if (ownable.getOwner() != null) {
//                System.out.println(ownable.getName() + " is already owned by " + ownable.getOwner().getName() + ".");
//                return;
//            }
//
//            if (wantsToBuy) {
//                if (this.money >= ownable.getCost()) {
//                    this.deductMoney(ownable.getCost());
//                    ownable.setOwner(this);
//                    System.out.println(name + " bought " + ownable.getName() + " for $" + ownable.getCost());
//                } else {
//                    System.out.println(name + " wanted to buy " + ownable.getName() + " but doesn't have enough money.");
//                }
//            } else {
//                System.out.println(name + " chose not to buy " + ownable.getName());
//            }
//        } else {
//            System.out.println("This space is not ownable.");
//        }
//    }


    public int getDiceRoll() {
        return totalRoll;
    }

    public int getFirstRoll() {
        return firstRoll;
    }

    public void setFirstRoll(int firstRoll) {
        this.firstRoll = firstRoll;
    }

    public int getSecondRoll() {
        return secondRoll;
    }

    public void setSecondRoll(int secondRoll) {
        this.secondRoll = secondRoll;
    }

    public int getTotalRoll() {
        return totalRoll;
    }

    public void setTotalRoll(int totalRoll) {
        this.totalRoll = totalRoll;
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

    private void handlePropertyPurchase(PropertySpace property, boolean wantsToBuy) {
        if (property.getOwner() != null) {
            System.out.println(property.getName() + " is already owned.");
            return;
        }

        if (wantsToBuy) {
            if (money >= property.getPrice()) {
                deductMoney(property.getPrice());
                property.setOwner(this);
                System.out.println(name + " bought " + property.getName() + " for $" + property.getPrice());
            } else {
                System.out.println(name + " can't afford " + property.getName());
            }
        } else {
            System.out.println(name + " chose not to buy " + property.getName());
        }
    }

    private void handleRailroadPurchase(RailRoadSpace railroad, boolean wantsToBuy) {
        if (railroad.getOwner() != null) {
            System.out.println(railroad.getName() + " is already owned.");
            return;
        }

        if (wantsToBuy) {
            if (money >= railroad.getPrice()) {
                deductMoney(railroad.getPrice());
                railroad.setOwner(this);
                System.out.println(name + " bought " + railroad.getName() + " for $" + railroad.getPrice());
            } else {
                System.out.println(name + " can't afford " + railroad.getName());
            }
        } else {
            System.out.println(name + " chose not to buy " + railroad.getName());
        }
    }

    private void handleUtilityPurchase(UtilitySpace utility, boolean wantsToBuy) {
        if (utility.getOwner() != null) {
            System.out.println(utility.getName() + " is already owned.");
            return;
        }

        if (wantsToBuy) {
            if (money >= utility.getPrice()) {
                deductMoney(utility.getPrice());
                utility.setOwner(this);
                System.out.println(name + " bought " + utility.getName() + " for $" + utility.getPrice());
            } else {
                System.out.println(name + " can't afford " + utility.getName());
            }
        } else {
            System.out.println(name + " chose not to buy " + utility.getName());
        }
    }

    public void buy(BoardSpace space, boolean wantsToBuy) {
        if (space instanceof PropertySpace property) {
            handlePropertyPurchase(property, wantsToBuy);
        } else if (space instanceof RailRoadSpace railroad) {
            handleRailroadPurchase(railroad, wantsToBuy);
        } else if (space instanceof UtilitySpace utility) {
            handleUtilityPurchase(utility, wantsToBuy);
        } else {
            System.out.println("This space is not ownable.");
        }
    }

    public void move() {

    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }




//    public Collection<Object> getProperties() {
//        return getProperties();
//    }
}
