package core.boardSpaceTypes;

import java.util.Scanner;

import core.Board;
import core.Player;

public class PropertySpace extends BoardSpace {
    private int price;
    private int rent;
    private Player owner;
    private PropertyColor color;


    public PropertySpace(String name, int position, int price, int rent, PropertyColor color) {
        super(name, position);
        this.price = price;
        this.rent = rent;
        this.color = color;
        this.owner = null;
    }

    public static void landOn(Player player, int position) {
        PropertySpace propertySpace = (PropertySpace) Board.getBoard().getSpace(position);
        System.out.println(player.getName() + " landed on " + propertySpace.name + " (Price: " + propertySpace.price + "$)");

        if (propertySpace.owner == null) {
            boolean wantsToBuy = askBuyConfirmation(player, propertySpace);
            player.buy(propertySpace, wantsToBuy);
        } else if (!propertySpace.owner.equals(player)) {
            if (propertySpace.owner.ownsAllOfColorProperties(propertySpace.color)) {
                int doubledRent = propertySpace.rent * 2;
                player.deductMoney(doubledRent);
                propertySpace.owner.addMoney(doubledRent);
                System.out.println(player.getName() + " paid double rent of " + doubledRent + "$ to " + propertySpace.owner.getName());
            } else {
                player.deductMoney(propertySpace.rent);
                propertySpace.owner.addMoney(propertySpace.rent);
                System.out.println(player.getName() + " paid " + propertySpace.rent + "$ rent to " + propertySpace.owner.getName());
            }
        } else {
            System.out.println(player.getName() + " landed on their own property " + propertySpace.name);
        }
    }

    public int getPrice() {
        return price;
    }

    public int getRent() {
        return rent;
    }

    public Player getOwner() {
        return owner;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }

    private static boolean askBuyConfirmation(Player player, PropertySpace propertySpace) {
        Scanner scanner = new Scanner(System.in);


        System.out.println(player.getName() + ", are you sure you want to buy " + propertySpace.getName() + " for " + propertySpace.getPrice() + "$? (Yes or No)");
        String response = scanner.nextLine();


        if (response.equalsIgnoreCase("Yes")) {
            return true;
        } else {
            return false;
        }
    }

    public PropertyColor getColor() {
        return color;
    }

}
