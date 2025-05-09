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

//    public static void landOn(Player player, int position, boolean wantsToBuy) {
//        RailRoadSpace railRoad =(RailRoadSpace) Board.getBoard().getSpace(position);
//        if (railRoad.owner == null) {
//            if (player.getMoney() >= railRoad.price) {
//                player.deductMoney(railRoad.price);
//                railRoad.owner = player;
//                System.out.println(player.getName() + " bought railroad " + railRoad.name + " for " + railRoad.price + "$");
//            } else {
//                System.out.println(player.getName() + " can't afford railroad " + railRoad.name + " (" + railRoad.price + "$)");
//            }
//        } else if (!railRoad.owner.equals(player)) {
//            int railroadsOwned = railRoad.owner.getNumberOfRailroadsOwned();
//            int rent = BASE_RENT * (int) Math.pow(2, railroadsOwned - 1); // 25, 50, 100, 200
//            player.deductMoney(rent);
//            railRoad.owner.addMoney(rent);
//            System.out.println(player.getName() + " paid " + rent + " $ rent to " + railRoad.owner.getName());
//        } else {
//            System.out.println(player.getName() + " landed on their own railroad " + railRoad.name);
//        }
//    }
public static void landOn(Player player, int position) {
    RailRoadSpace railRoadSpace = (RailRoadSpace) Board.getBoard().getSpace(position);
    if (railRoadSpace.owner == null) {
        System.out.println(player.getName() + " landed on " + railRoadSpace.name + " (Price: " + railRoadSpace.getPrice() + "$)");


        boolean wantsToBuy = askBuyConfirmation(player, railRoadSpace);

        if (wantsToBuy) {
            if (player.getMoney() >= railRoadSpace.getPrice()) {
                player.deductMoney(railRoadSpace.getPrice());
                railRoadSpace.setOwner(player);
                System.out.println(player.getName() + " bought " + railRoadSpace.name + " for " + railRoadSpace.getPrice() + "$");
            } else {
                System.out.println(player.getName() + " can't afford " + railRoadSpace.name + " (" + railRoadSpace.getPrice() + "$)");
            }
        } else {
            System.out.println(player.getName() + " decided not to buy " + railRoadSpace.name);
        }

    } else if (!railRoadSpace.owner.equals(player)) {
            int railroadsOwned = railRoadSpace.owner.getNumberOfRailroadsOwned();
            int rent = BASE_RENT * (int) Math.pow(2, railroadsOwned - 1); // 25, 50, 100, 200
            player.deductMoney(rent);
            railRoadSpace.owner.addMoney(rent);
            System.out.println(player.getName() + " paid " + rent + " $ rent to " + railRoadSpace.owner.getName());
        } else {
            System.out.println(player.getName() + " landed on their own railroad " + railRoadSpace.name);
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
