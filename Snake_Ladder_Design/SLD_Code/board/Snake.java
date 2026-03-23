package board;

public class Snake extends BoardEntity {

    public Snake(int start, int end) {
        super(start, end);
    }

    @Override
    public void display() {
        System.out.println("Snake: " + startPosition + " -> " + endPosition);
    }

    @Override
    public String name() {
        return "SNAKE";
    }
}