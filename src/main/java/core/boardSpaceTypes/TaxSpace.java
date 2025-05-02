package core.boardSpaceTypes;

import core.Board;
import core.Player;

public class TaxSpace extends BoardSpace {
    private int tax;

    public TaxSpace(String name, int position, int tax) {
        super(name, position);
        this.tax = tax;
    }


    public static void landOn(Player player, int position) {
        TaxSpace taxSpace = (TaxSpace) Board.getBoard().getSpace(position);
        player.deductMoney(taxSpace.tax);
        System.out.println(player.getName() + " paid " + taxSpace.tax + "$ in taxes at " + taxSpace.name);
    }
}
