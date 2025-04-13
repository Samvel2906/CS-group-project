package core.boardSpaceTypes;

import core.Player;

public class CommunityChestSpaces extends BoardSpace {
    public CommunityChestSpaces(String name, int position) {
        super(name, position);
    }


    public void landOn(Player player) {
        System.out.println(player.getName() + " landed on Community Chest.");
        // Implement Community Chest logic just like done in chance card
    }
}
