package strategy;

import board.*;

public class StandardSnakeAndLadderRules implements SnakeAndLadderRules {

    @Override
    public boolean isValidMove(int currentPos, int dice, int size) {
        return currentPos + dice <= size;
    }

    @Override
    public int calculateNewPosition(int currentPos, int dice, Board board) {
        int newPos = currentPos + dice;
        BoardEntity entity = board.getEntity(newPos);
        return entity != null ? entity.getEnd() : newPos;
    }

    @Override
    public boolean checkWinCondition(int pos, int size) {
        return pos == size;
    }
}