package strategy;

import board.*;

import java.util.Random;

public class RandomBoardSetupStrategy implements BoardSetupStrategy {

    public enum Difficulty {
        EASY,
        MEDIUM,
        HARD
    }

    private Difficulty difficulty;
    private Random random = new Random();

    public RandomBoardSetupStrategy(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    @Override
    public void setupBoard(Board board) {
        double snakeProbability;

        switch (difficulty) {
            case EASY:
                snakeProbability = 0.3;
                break;
            case HARD:
                snakeProbability = 0.7;
                break;
            default:
                snakeProbability = 0.5;
        }

        int boardSize = board.getBoardSize();
        int totalEntities = boardSize / 10;

        for (int i = 0; i < totalEntities; i++) {

            if (random.nextDouble() < snakeProbability) {
                addSnake(board, boardSize);
            } else {
                addLadder(board, boardSize);
            }
        }
    }

    private void addSnake(Board board, int size) {
        int attempts = 0;

        while (attempts < 50) {
            int start = random.nextInt(size - 10) + 10;
            int end = random.nextInt(start - 1) + 1;

            if (board.canAddEntity(start)) {
                board.addBoardEntity(new Snake(start, end));
                return;
            }
            attempts++;
        }
    }

    private void addLadder(Board board, int size) {
        int attempts = 0;

        while (attempts < 50) {
            int start = random.nextInt(size - 10) + 1;
            int end = random.nextInt(size - start) + start + 1;

            if (board.canAddEntity(start) && end < size) {
                board.addBoardEntity(new Ladder(start, end));
                return;
            }
            attempts++;
        }
    }
}