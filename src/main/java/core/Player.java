package core;

public class Player {
    private String name;
    private int position;
    private int money;
    private boolean inJail;
    private int diceRoll;

    public Player(String name) {
        this.name = name;
        this.position = 0;
        this.money = 1500;
        this.inJail = false;
    }

    public void move(int steps) {
        if (!inJail) {
            position = (position + steps) % 40;
            System.out.println(name + " moved to position " + position);
        } else {
            System.out.println(name + " is in Jail and can't move this turn.");
        }
    }

    public void goToJail() {
        this.position = 10;
        this.inJail = true;
        System.out.println(name + " has been sent to Jail.");
    }

    public void releaseFromJail() {
        this.inJail = false;
        System.out.println(name + " is released from Jail.");
    }

    public void rollDice(Dice dice) {
        this.diceRoll = dice.roll() + dice.roll();
        System.out.println(name + " rolled a " + diceRoll);
    }
    public int getDiceRoll() {
        return diceRoll;
    }


    public String getName() {
        return name;
    }

    public int getPosition() {
        return position;
    }
    public void addMoney(int amount) {
        this.money += amount;
    }
    public void deductMoney(int amount) {
        this.money -= amount;
    }
    public int getMoney() {
        return money;
    }

    public void setMoney(int amount) {
        this.money = amount;
    }
}
