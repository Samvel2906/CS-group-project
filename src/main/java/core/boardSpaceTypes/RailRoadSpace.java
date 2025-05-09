package core.boardSpaceTypes;

import core.Board;
import core.Player;

import java.util.Scanner;

public class RailRoadSpace extends BoardSpace {
    private static final int BASE_RENT = 25;
    private int price;
    private Player owner;

    public RailRoadSpace(String name, int position, int price, int rentIgnored) {
        super(name, position);
        this.price = price;
        this.owner = null;
    }

    public static void landOn(Player player, int position) {
        RailRoadSpace railroad = (RailRoadSpace) Board.getBoard().getSpace(position);
        System.out.println(player.getName() + " landed on " + railroad.name + " (Price: " + railroad.price + "$)");

        if (railroad.owner == null) {
            boolean wantsToBuy = askBuyConfirmation(player, railroad);
            player.buy(railroad, wantsToBuy);
        } else if (!railroad.owner.equals(player)) {
            int railroadsOwned = railroad.owner.getNumberOfRailroadsOwned();
            int rent = BASE_RENT * (int) Math.pow(2, railroadsOwned - 1);
            player.deductMoney(rent);
            railroad.owner.addMoney(rent);
            System.out.println(player.getName() + " paid $" + rent + " rent to " + railroad.owner.getName());
        } else {
            System.out.println(player.getName() + " landed on their own railroad " + railroad.name);
        }
    }



    public Player getOwner() {
        return owner;
    }

    public boolean isOwned() {
        return owner != null;
    }

    public int getPrice() {
        return price;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }

    private static boolean askBuyConfirmation(Player player, RailRoadSpace railRoadspace) {
        Scanner scanner = new Scanner(System.in);

        System.out.println(player.getName() + ", are you sure you want to buy " + railRoadspace.getName() + " for " + railRoadspace.getPrice() + "$? (Yes or No)");
        String response = scanner.nextLine();

        if (response.equalsIgnoreCase("Yes")) {
            return true;
        } else {
            return false;
        }
    }

}
