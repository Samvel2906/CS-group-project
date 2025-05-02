package core.boardSpaceTypes;

import core.Player;

public class FreeParkingSpace extends BoardSpace {
    public FreeParkingSpace(int position) {
        super("Free Parking", position);
    }


    public static void landOn(Player player, int position) {
        System.out.println(player.getName() + " landed on Free Parking. Nothing happens.");
    }
}
