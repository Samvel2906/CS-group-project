package core.boardSpaceTypes;

import core.Player;

public class PropertySpace extends BoardSpace {
    private int price;

    public PropertySpace(String name, int position, int price) {
        super(name, position);
        this.price = price;
    }

    public void landOn(Player player) {
        System.out.println(player.getName() + " landed on property" + name + " " + price + "$");
        // add buy or pay(if owned)
    }
}