package core.boardSpaceTypes;

import core.Board;
import java.util.Scanner;
import core.Player;

public class UtilitySpace extends BoardSpace {
    private int baseRent;
    private int price;
    private Player owner;

    public UtilitySpace(String name, int position, int price, int baseRent) {
        super(name, position);
        this.baseRent = baseRent;
        this.price = price;
        this.owner = null;
    }


//    public static void landOn(Player player, int position, boolean wantsToBuy) {
//        UtilitySpace utilitySpace = (UtilitySpace) Board.getBoard().getSpace(position);
//        System.out.println(player.getName() + " landed on " + utilitySpace.name);
//        if (utilitySpace.getOwner() == null) {
//            player.buy(utilitySpace, wantsToBuy);
//        } else if (!utilitySpace.getOwner().equals(player)) {
//            int rentToPay = utilitySpace.calculateRent(player);
//            System.out.println(player.getName() + " pays $" + rentToPay + " in rent to " + utilitySpace.getOwner().getName());
//            player.deductMoney(rentToPay);
//            utilitySpace.getOwner().addMoney(rentToPay);
//        } else {
//            System.out.println(player.getName() + " landed on their own utility space.");
//        }
//    }
    public static void landOn(Player player, int position) {
        UtilitySpace utilitySpace = (UtilitySpace) Board.getBoard().getSpace(position);
        if (utilitySpace.getOwner() == null) {
            System.out.println(player.getName() + " landed on " + utilitySpace.name + " (Price: " + utilitySpace.getPrice() + "$)");

            // Ask if the player wants to buy the space
            boolean wantsToBuy = askBuyConfirmation(player, utilitySpace);

            if (wantsToBuy) {
                if (player.getMoney() >= utilitySpace.getPrice()) {
                    player.deductMoney(utilitySpace.getPrice());
                    utilitySpace.setOwner(player);
                    System.out.println(player.getName() + " bought " + utilitySpace.name + " for " + utilitySpace.getPrice() + "$");
                } else {
                    System.out.println(player.getName() + " can't afford " + utilitySpace.name + " (" + utilitySpace.getPrice() + "$)");
                }
            } else {
                System.out.println(player.getName() + " decided not to buy " + utilitySpace.name);
            }

        } else if (utilitySpace.getOwner() != player) {
            int rent = utilitySpace.calculateRent(player);
            player.deductMoney(rent);
            utilitySpace.getOwner().addMoney(rent);
            System.out.println(player.getName() + " paid " + rent + "$ rent to " + utilitySpace.getOwner().getName());
        } else {
            System.out.println(player.getName() + " landed on their own " + utilitySpace.name);
        }
    }
    private int calculateRent(Player player) {
        int roll = player.getDiceRoll();
        int rent = baseRent * roll;
        return rent;
    }
    public int getPrice() {
        return price;
    }


    public Player getOwner() {
        return owner;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }

    private static boolean askBuyConfirmation(Player player, UtilitySpace utilitySpace) {
        Scanner scanner = new Scanner(System.in);

        System.out.println(player.getName() + ", are you sure you want to buy " + utilitySpace.getName() + " for " + utilitySpace.getPrice() + "$? (Yes or No)");
        String response = scanner.nextLine();

        if (response.equalsIgnoreCase("Yes")) {
            return true;
        } else {
            return false;
        }
    }
}

