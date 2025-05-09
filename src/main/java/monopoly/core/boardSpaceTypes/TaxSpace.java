package monopoly.core.boardSpaceTypes;

import monopoly.core.Board;
import monopoly.core.Player;
import monopoly.core.exceptions.NotEnoughMoneyToPayException;

public class TaxSpace extends BoardSpace {
    private int tax;

    public TaxSpace(String name, int position, int tax) {
        super(name, position);
        this.tax = tax;
    }


    public static void landOn(Player player, int position) throws NotEnoughMoneyToPayException {
        TaxSpace taxSpace = (TaxSpace) Board.getBoard().getSpace(position);
        player.pay(taxSpace.tax);
        System.out.println(player.getName() + " paid " + taxSpace.tax + "$ in taxes at " + taxSpace.name);
    }
}
