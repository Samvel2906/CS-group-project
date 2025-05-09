package monopoly.core.boardSpaceTypes;

import monopoly.core.Board;
import monopoly.core.Player;
import monopoly.core.exceptions.NotEnoughMoneyToPayException;


import java.util.ArrayList;


public class CommunityChestSpace extends BoardSpace {
    private static final String[] communityChestCards = {
            "Bank error in your favor. Collect $200.",
            "Doctor's fees. Pay $50.",
            "Advance to GO (Collect $200).",
            "Go to Jail. Do not pass GO, do not collect $200.",
            "It's your birthday. Collect $10 from each player.",
            "Pay hospital fees of $100."
    };

    public CommunityChestSpace(String name, int position) {
        super(name, position);
    }


    public static void landOn(Player player, int position) throws NotEnoughMoneyToPayException {
        System.out.println(player.getName() + " landed on Community Chest.");
        String card = communityChestCards[(int) (Math.random() * communityChestCards.length)];
        System.out.println("Community Chest: " + card);
        CommunityChestSpace communityChestSpace = (CommunityChestSpace) Board.getBoard().getSpace(position);
        communityChestSpace.applyCardEffect(player, card);
    }

    private void applyCardEffect(Player player, String card) throws NotEnoughMoneyToPayException {
        switch (card) {
            case "Bank error in your favor. Collect $200.":
                player.addMoney(200);
                System.out.println(player.getName() + " collected $200.");
                break;
            case "Doctor's fees. Pay $50.":
                player.pay(50);
                System.out.println(player.getName() + " deducts $50.");
                break;
            case "Advance to GO (Collect $200).":
                player.setPosition(0);
                player.addMoney(200);
                System.out.println(player.getName() + " advanced to GO and collected $200.");
                break;
            case "Go to Jail. Do not pass GO, do not collect $200.":
                player.goToJail();
                System.out.println(player.getName() + " go to Jail.");
                break;
            case "It's your birthday. Collect $10 from each player.":
                CommunityChestSpace.collectBirthdayMoney(player);
                System.out.println(player.getName() + " collected $10 from each player.");
                break;
            case "Pay hospital fees of $100.":
                player.pay(100);
                System.out.println(player.getName() + " deducts $100.");
                break;
        }
    }


    private static void collectBirthdayMoney(Player birthdayPlayer) {
        ArrayList<Player> players = Board.getBoard().getPlayers();
        for (Player player : players) {
            if (!player.equals(birthdayPlayer)) {
                player.deductMoney(10);
                birthdayPlayer.addMoney(10);
                System.out.println(player.getName() + " paid $10 to " + birthdayPlayer.getName());
            }
        }


    }
}
