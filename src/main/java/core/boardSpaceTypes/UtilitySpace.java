package core.boardSpaceTypes;

import core.Board;
import core.Player;

public class UtilitySpace extends BoardSpace {
    private int baseRent;
    private int price;

    public UtilitySpace(String name, int position, int price, int baseRent) {
        super(name, position);
        this.baseRent = baseRent;
    }


    public static void landOn(Player player, int position) {
        UtilitySpace utilitySpace = (UtilitySpace) Board.getBoard().getSpace(position);
        System.out.println(player.getName() + " landed on " + utilitySpace.name);
        int rentToPay = utilitySpace.calculateRent(player);
        System.out.println(player.getName() + " pays $" + rentToPay + " in rent.");
        player.setMoney(player.getMoney() - rentToPay);
    }

    private int calculateRent(Player player) {
        int roll = player.getDiceRoll();
        int rent = baseRent * roll;
        return rent;
    }
}

