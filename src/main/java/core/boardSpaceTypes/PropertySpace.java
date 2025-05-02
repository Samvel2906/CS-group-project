package core.boardSpaceTypes;

import core.Board;
import core.Player;

public class PropertySpace extends BoardSpace {
    private int price;
    private int rent;
    private Player owner;

    public PropertySpace(String name, int position, int price, int rent) {
        super(name, position);
        this.price = price;
        this.rent = rent;
        this.owner = null;
    }


    public static void landOn(Player player, int position) {
        PropertySpace propertySpace = (PropertySpace) Board.getBoard().getSpace(position);
        if (propertySpace.owner == null) {
            if (player.getMoney() >= propertySpace.price) {

                player.deductMoney(propertySpace.price);
                propertySpace.owner = player;
                System.out.println(player.getName() + " bought property " + propertySpace.name + " for " + propertySpace.price + "$");
            } else {
                System.out.println(player.getName() + " can't afford property " + propertySpace.name + " (" + propertySpace.price + "$)");
            }
        } else if (propertySpace.owner != player) {
            player.deductMoney(propertySpace.rent);
            propertySpace.owner.addMoney(propertySpace.rent);
            System.out.println(player.getName() + " paid " + propertySpace.rent + " $ rent to " + propertySpace.owner.getName());
        } else {
            System.out.println(player.getName() + " landed on their own property " + propertySpace.name);
        }
    }
}
