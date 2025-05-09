package core;
import core.boardSpaceTypes.*;
import java.util.ArrayList;

public class Board {
    private static final int BOARD_SIZE = 40;
    private static Board board;
    private final BoardSpace[] spaces;
    private ArrayList<Player> players;


    Board() {
        spaces = new BoardSpace[BOARD_SIZE];
        initialiseSpaces();
    }

    public static Board getBoard() {
        if (board == null) {
            board = new Board();
        }
        return board;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    private void initialiseSpaces() {
        spaces[0] = new GoSpace(0);
        spaces[1] = new PropertySpace("Mediterranean Avenue", 1, 60, 2);
        spaces[2] = new CommunityChestSpace("Community Chest", 2);
        spaces[3] = new PropertySpace("Baltic Avenue", 3, 60, 4);
        spaces[4] = new TaxSpace("Income Tax", 4, 200);
        spaces[5] = new RailRoadSpace("Reading Railroad", 5, 200, 25);
        spaces[6] = new PropertySpace("Oriental Avenue", 6, 100, 6);
        spaces[7] = new ChanceSpace("Chance", 7);
        spaces[8] = new PropertySpace("Vermont Avenue", 8, 100, 6);
        spaces[9] = new PropertySpace("Connecticut Avenue", 9, 120, 8);
        spaces[10] = new JailSpace(10);
        spaces[11] = new PropertySpace("St. Charles Place", 11, 140, 10);
        spaces[12] = new UtilitySpace("Electric Company", 12, 150, 10);
        spaces[13] = new PropertySpace("States Avenue", 13, 140, 10);
        spaces[14] = new PropertySpace("Virginia Avenue", 14, 160, 12);
        spaces[15] = new RailRoadSpace("Pennsylvania Railroad", 15, 200, 25);
        spaces[16] = new PropertySpace("St. James Place", 16, 180, 14);
        spaces[17] = new CommunityChestSpace("Community Chest", 17);
        spaces[18] = new PropertySpace("Tennessee Avenue", 18, 180, 14);
        spaces[19] = new PropertySpace("New York Avenue", 19, 200, 16);
        spaces[20] = new FreeParkingSpace(20);
        spaces[21] = new PropertySpace("Kentucky Avenue", 21, 220, 18);
        spaces[22] = new ChanceSpace("Chance", 22);
        spaces[23] = new PropertySpace("Indiana Avenue", 23, 220, 18);
        spaces[24] = new PropertySpace("Illinois Avenue", 24, 240, 20);
        spaces[25] = new RailRoadSpace("B&O Railroad", 25, 200, 25);
        spaces[26] = new PropertySpace("Atlantic Avenue", 26, 260, 22);
        spaces[27] = new PropertySpace("Ventnor Avenue", 27, 260, 22);
        spaces[28] = new UtilitySpace("Water Works", 28, 150, 10);
        spaces[29] = new PropertySpace("Marvin Gardens", 29, 280, 24);
        spaces[30] = new GoToJailSpace(30);
        spaces[31] = new PropertySpace("Pacific Avenue", 31, 300, 26);
        spaces[32] = new PropertySpace("North Carolina Avenue", 32, 300, 26);
        spaces[33] = new CommunityChestSpace("Community Chest", 33);
        spaces[34] = new PropertySpace("Pennsylvania Avenue", 34, 320, 28);
        spaces[35] = new RailRoadSpace("Short Line", 35, 200, 25);
        spaces[36] = new ChanceSpace("Chance", 36);
        spaces[37] = new PropertySpace("Park Place", 37, 350, 35);
        spaces[38] = new TaxSpace("Luxury Tax", 38, 100);
        spaces[39] = new PropertySpace("Boardwalk", 39, 400, 50);
    }

    public BoardSpace getSpace(int position) {
        return spaces[position];
    }

    public BoardSpace[] getSpaces() {
        return spaces;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }
}