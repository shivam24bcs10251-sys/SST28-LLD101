package player;

public class SnakeAndLadderPlayer {

    private int id;
    private String name;
    private int position;

    public SnakeAndLadderPlayer(int id, String name) {
        this.id = id;
        this.name = name;
        this.position = 0;
    }

    public String getName() {
        return name;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int pos) {
        this.position = pos;
    }
}