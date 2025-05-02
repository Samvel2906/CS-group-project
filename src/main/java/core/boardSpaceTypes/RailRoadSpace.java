package core.boardSpaceTypes;

import core.Board;
import core.Player;

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
        RailRoadSpace railRoad =(RailRoadSpace) Board.getBoard().getSpace(position);
        if (railRoad.owner == null) {
            if (player.getMoney() >= railRoad.price) {
                player.deductMoney(railRoad.price);
                railRoad.owner = player;
                System.out.println(player.getName() + " bought railroad " + railRoad.name + " for " + railRoad.price + "$");
            } else {
                System.out.println(player.getName() + " can't afford railroad " + railRoad.name + " (" + railRoad.price + "$)");
            }
        } else if (!railRoad.owner.equals(player)) {
            int railroadsOwned = railRoad.owner.getNumberOfRailroadsOwned();
            int rent = BASE_RENT * (int) Math.pow(2, railroadsOwned - 1); // 25, 50, 100, 200
            player.deductMoney(rent);
            railRoad.owner.addMoney(rent);
            System.out.println(player.getName() + " paid " + rent + " $ rent to " + railRoad.owner.getName());
        } else {
            System.out.println(player.getName() + " landed on their own railroad " + railRoad.name);
        }
    }

    public Player getOwner() {
        return owner;
    }

    public boolean isOwned() {
        return owner != null;
    }
}
