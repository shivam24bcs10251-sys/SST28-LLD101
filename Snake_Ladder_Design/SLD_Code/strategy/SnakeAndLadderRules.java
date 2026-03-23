package strategy;

import board.Board;

public interface SnakeAndLadderRules {
    boolean isValidMove(int currentPos, int dice, int boardSize);
    int calculateNewPosition(int currentPos, int dice, Board board);
    boolean checkWinCondition(int pos, int boardSize);
}