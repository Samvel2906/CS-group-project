package monopoly.core.boardSpaceTypes;

import monopoly.core.Player;

public abstract class OwnableBoardSpace extends BoardSpace {
    protected Player owner;

    public OwnableBoardSpace(String name, int position) {
        super(name, position);
        this.owner = null;
    }

    public Player getOwner() {
        return owner;
    }

    public void setOwner(Player player) {
        this.owner = player;
    }
}

