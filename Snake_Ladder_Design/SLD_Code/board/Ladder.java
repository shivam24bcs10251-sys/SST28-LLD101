package board;

public class Ladder extends BoardEntity {

    public Ladder(int start, int end) {
        super(start, end);
    }

    @Override
    public void display() {
        System.out.println("Ladder: " + startPosition + " -> " + endPosition);
    }

    @Override
    public String name() {
        return "LADDER";
    }
}