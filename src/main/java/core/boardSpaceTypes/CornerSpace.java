package core.boardSpaceTypes;

import core.Player;

public class CornerSpace extends BoardSpace {
    private CornerType type;

    public CornerSpace(String name, int position, CornerType type) {
        super(name, position);
        this.type = type;
    }


    public void landOn(Player player) {
        switch (type) {
            case START:
                System.out.println(player.getName() + " landed on START. Collect $200!");
                player.addMoney(200);
                break;
            case JAIL:
                System.out.println(player.getName() + " is just visiting JAIL.");
                break;
            case FREE_PARKING:
                System.out.println(player.getName() + " landed on FREE PARKING.");
                break;
            case GO_TO_JAIL:
                System.out.println(player.getName() + " is going directly to JAIL!");
                player.goToJail();
                break;
        }
    }

    public CornerType getType() {
        return type;
    }





    public enum CornerType {
        START,
        JAIL,
        FREE_PARKING,
        GO_TO_JAIL
    }
}
