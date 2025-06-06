package monopoly.core.boardSpaceTypes;

import monopoly.core.Player;

public class GoToJailSpace extends BoardSpace {
    public GoToJailSpace(int position) {
        super("Go to Jail", position);
    }

    public static void landOn(Player player, int position) {
        player.goToJail();
    }
}
