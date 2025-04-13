package core.boardSpaceTypes;

import core.Player;

public class RailRoadSpace extends BoardSpace {
    private int rent;

    public RailRoadSpace(String name, int position, int rent) {
        super(name, position);
        this.rent = rent;
    }


    public void landOn(Player player) {
        System.out.println(player.getName() + " landed on " + name + ". Rent is $" + rent);
        // Implement logic for rent payment or purchase
    }
}
