package monopoly.core.boardSpaceTypes;

import monopoly.core.Board;
import monopoly.core.Player;

public class JailSpace extends BoardSpace {
    private static final int maximumTurns = 3;
    private static final int getOutFee = 50;
    public JailSpace(int position) {
        super("Jail", position);
    }

    public static void landOn(Player player, int position) {
      JailSpace jailSpace = (JailSpace) Board.getBoard().getSpace(position);
        if (!player.isInJail()) {
            System.out.println(player.getName() + " has passed through Jail.");
        } else {
            if (player.getTurnsInJail() < maximumTurns) {
                System.out.println(player.getName() + " is in Jail. They need to roll doubles or pay " + getOutFee + " to get out.");

                if (player.getMoney() >= getOutFee) {
                    System.out.println(player.getName() + " chooses to pay " + getOutFee + " to get out of Jail.");
                    jailSpace.payToGetOut(player);
                } else {
                    System.out.println(player.getName() + " doesn't have enough money to pay.");
                }
            } else {
                System.out.println(player.getName() + " has been in Jail for " + maximumTurns + " turns. They need to pay " + getOutFee + " or roll doubles to get out.");
            }
        }
    }

    public void payToGetOut(Player player) {
        if (player.getMoney() >= getOutFee) {
            player.deductMoney(getOutFee);
            player.releaseFromJail();
            System.out.println(player.getName() + " has paid " + getOutFee + "$ to get out of Jail.");
        } else {
            System.out.println(player.getName() + " does not have enough money to pay the get out fee.");
        }
    }
}
