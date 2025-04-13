package core.boardSpaceTypes;

import core.Player;

public class UtilitySpace extends BoardSpace {
    private int baseRent;

    public UtilitySpace(String name, int position, int baseRent) {
        super(name, position);
        this.baseRent = baseRent;
    }


    public void landOn(Player player) {
        System.out.println(player.getName() + " landed on " + name);
        int rentToPay = calculateRent(player);
        System.out.println(player.getName() + " pays $" + rentToPay + " in rent.");
        player.setMoney(player.getMoney() - rentToPay);
    }

    private int calculateRent(Player player) {
        int roll = player.getDiceRoll();
        int rent = baseRent * roll;
        return rent;
    }
}

