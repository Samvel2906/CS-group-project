package core.boardSpaceTypes;
import core.Player;
public class TaxSpaces extends BoardSpace{
    private int taxAmount;

    public TaxSpaces(String name, int position, int taxAmount) {
        super(name, position);
        this.taxAmount = taxAmount;
    }

    public void landOn(Player player) {
        System.out.println(player.getName() + " must pay $" + taxAmount + " in taxes!");
        player.deductMoney(taxAmount);
    }
}
