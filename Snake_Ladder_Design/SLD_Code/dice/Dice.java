package dice;

public class Dice {
    private int faces;

    public Dice(int f) {
        this.faces = f;
    }

    public int roll() {
        return (int)(Math.random() * faces) + 1;
    }
}