package monopoly.core.boardSpaceTypes;


public abstract class BoardSpace {
    protected String name;
    protected int position;

    public BoardSpace(String name, int position) {
        this.name = name;
        this.position = position;
    }
    public String getName() {
        return name;
    }


}
