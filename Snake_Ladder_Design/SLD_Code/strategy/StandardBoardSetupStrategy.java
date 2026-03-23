package strategy;

import board.*;

public class StandardBoardSetupStrategy implements BoardSetupStrategy {

    @Override
    public void setupBoard(Board board) {
        board.addBoardEntity(new Snake(99, 54));
        board.addBoardEntity(new Ladder(2, 38));
    }
}