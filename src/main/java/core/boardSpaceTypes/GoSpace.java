package core.boardSpaceTypes;

import core.Player;

public class GoSpace extends BoardSpace {
    private static final int GO_REWARD = 200;

    public GoSpace(int position) {
        super("GO", position);
    }

    public static void landOn(Player player, int position) {
        player.addMoney(GO_REWARD);
        System.out.println(player.getName() + " landed on GO and collected $" + GO_REWARD);
    }
}
