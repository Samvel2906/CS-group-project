package core.boardSpaceTypes;

import core.Board;
import core.Player;

import java.util.ArrayList;


public class ChanceSpace extends BoardSpace {
    private static final String[] chanceCards = {
            "Advance to GO (Collect $200)",
            "Go to Jail. Go directly to Jail, do not pass GO, do not collect $200.",
            "Pay $50 in taxes.",
            "Take a ride on the Reading Railroad, advance to Reading Railroad.",
            "Advance to the nearest Utility and pay rent if owned by another player.",
            "Pay each player $10.",
            "Advance to the nearest Railroad and pay rent if owned by another player."
    };

    private static final int READING_RAILROAD_POSITION = 5;


    public ChanceSpace(String name, int position) {
        super(name, position);
    }


    public static void landOn(Player player, int position) {
        System.out.println(player.getName() + " landed on a Chance space!");
        String card = chanceCards[(int) (Math.random() * chanceCards.length)];
        System.out.println("Chance Card: " + card);

        ChanceSpace chanceSpace = (ChanceSpace) Board.getBoard().getSpace(position);
        chanceSpace.applyCardEffect(player, card);
    }

    private void applyCardEffect(Player player, String card) {
        switch (card) {
            case "Advance to GO (Collect $200)":
                player.setPosition(0);
                player.addMoney(200);
                System.out.println(player.getName() + " advanced to GO and collected $200.");
                break;

            case "Go to Jail. Go directly to Jail, do not pass GO, do not collect $200.":
                player.goToJail();
                System.out.println(player.getName() + " went to Jail.");
                break;

            case "Pay $50 in taxes.":
                player.deductMoney(50);
                System.out.println(player.getName() + " paid $50 in taxes.");
                break;

            case "Take a ride on the Reading Railroad, advance to Reading Railroad.":
                RailRoadSpace.landOn(player, READING_RAILROAD_POSITION);
                System.out.println(player.getName() + " took a ride on the Reading Railroad.");
                break;

            case "Advance to the nearest Utility and pay rent if owned by another player.":
                int utilityPos = findNearest(player.getPosition(), new int[]{12, 28});
                UtilitySpace.landOn(player, utilityPos);
                System.out.println(player.getName() + " went to the nearest Utility.");
                break;

            case "Advance to the nearest Railroad and pay rent if owned by another player.":
                int railroadPos = findNearest(player.getPosition(), new int[]{5, 15, 25, 35});
                RailRoadSpace.landOn(player, railroadPos);
                System.out.println(player.getName() + " went to the nearest Railroad.");
                break;

            case "Pay each player $10.":
                ChanceSpace.payEachPlayer(player);
                System.out.println(player.getName() + " paid $10 to each player.");
                break;
        }
    }

    private int findNearest(int currentPos, int[] positions) {
        int minDistance = 40;
        int nearest = positions[0];
        for (int pos : positions) {
            int distance = Math.abs(pos - currentPos + 40);
            if (distance < minDistance) {
                minDistance = distance;
                nearest = pos;
            }
        }
        return nearest;
    }

    private static void payEachPlayer(Player playerThatPays) {
        ArrayList<Player> players = Board.getBoard().getPlayers();
        for (Player player : players) {
            if (!player.equals(playerThatPays)) {
                player.addMoney(10);
                playerThatPays.deductMoney(10);
                System.out.println(playerThatPays.getName() + " paid $10 to " + player.getName());
            }
        }


    }


}

