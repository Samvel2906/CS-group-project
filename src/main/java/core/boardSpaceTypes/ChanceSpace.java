package core.boardSpaceTypes;
import core.Player;
import java.util.Random;

public class ChanceSpace extends BoardSpace {
    private static final String[] chanceCards = {
            "Advance to GO (Collect $200)",
            "Go to Jail. Go directly to Jail, do not pass GO, do not collect $200.",
            "Pay $50 in taxes.",
            "Take a ride on the Reading Railroad, advance to Reading Railroad.",
            "Advance to the nearest Utility and pay rent.",
            "Pay each player $10."
    };

    public ChanceSpace(String name, int position) {
        super(name, position);
    }


    public void landOn(Player player) {
        System.out.println(player.getName() + " landed on a Chance space!");

        Random rand = new Random();
        int cardIndex = rand.nextInt(chanceCards.length);
        String card = chanceCards[cardIndex];
        System.out.println("Chance Card " + card);

        applyCardEffect(player, card);
    }

    private void applyCardEffect(Player player, String card) {
        switch (card) {
            case "Advance to GO (Collect $200)":
                // Implement logic of move to "GO"
                player.setMoney(player.getMoney() + 200);
                System.out.println(player.getName() + " collected $200 for landing on GO!");
                break;

            case "Go to Jail. Go directly to Jail, do not pass GO, do not collect $200.":
                player.goToJail();
                break;

            case "Pay $50 in taxes.":
                player.setMoney(player.getMoney() - 50);
                System.out.println(player.getName() + " paid $50 in taxes.");
                break;

            case "Take a ride on the Reading Railroad, advance to Reading Railroad.":
                // Implement logic of move to railroad position
                System.out.println(player.getName() + " advanced to the Reading Railroad.");
                break;

            case "Advance to the nearest Utility and pay rent.":
                // Implement logic of move to nearest utility space
                System.out.println(player.getName() + " advanced to the nearest Utility (Electric Company).");
                break;

            case "Pay each player $10.":
                // Implement logic of giving each player 10$
                player.setMoney(player.getMoney() - 10);
                System.out.println(player.getName() + " paid $10 to each player.");
                break;



        }
    }
}

