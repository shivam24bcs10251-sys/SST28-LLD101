package game;

import board.Board;
import dice.Dice;
import strategy.*;

public class SnakeAndLadderGameFactory {

    public static SnakeAndLadderGame createGame() {
        Board board = new Board(10);
        board.setupBoard(new StandardBoardSetupStrategy());

        Dice dice = new Dice(6);

        return new SnakeAndLadderGame(board, dice);
    }
}