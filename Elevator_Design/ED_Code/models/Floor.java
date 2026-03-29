package models;

import buttons.HallButton;
import enums.Direction;

public class Floor {
    private int id;
    private HallButton upButton;
    private HallButton downButton;

    public Floor(int id) {
        this.id = id;
        this.upButton = new HallButton(Direction.UP, id);
        this.downButton = new HallButton(Direction.DOWN, id);
    }

    public int getId() { return id; }
    public HallButton getUpButton() { return upButton; }
    public HallButton getDownButton() { return downButton; }
    
    public void pressUpButton() {
        upButton.press();
    }

    public void pressDownButton() {
        downButton.press();
    }
}
