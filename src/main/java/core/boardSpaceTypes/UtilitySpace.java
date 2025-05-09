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


    public static void landOn(Player player, int position) {
        UtilitySpace utilitySpace = (UtilitySpace) Board.getBoard().getSpace(position);
        System.out.println(player.getName() + " landed on " + utilitySpace.name + " (Price: " + utilitySpace.price + "$)");

        if (utilitySpace.owner == null) {
            boolean wantsToBuy = askBuyConfirmation(player, utilitySpace);
            player.buy(utilitySpace, wantsToBuy);
        } else if (!utilitySpace.owner.equals(player)) {
            int rentToPay = utilitySpace.calculateRent(player);
            player.deductMoney(rentToPay);
            utilitySpace.owner.addMoney(rentToPay);
            System.out.println(player.getName() + " paid $" + rentToPay + " rent to " + utilitySpace.owner.getName());
        } else {
            System.out.println(player.getName() + " landed on their own utility " + utilitySpace.name);
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

