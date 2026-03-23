package strategy;

import board.*;

import java.util.*;

public class CustomCountBoardSetupStrategy implements BoardSetupStrategy {

    private int numSnakes;
    private int numLadders;
    private boolean randomPositions;

    private List<Pair<Integer, Integer>> snakePositions = new ArrayList<>();
    private List<Pair<Integer, Integer>> ladderPositions = new ArrayList<>();

    private Random random = new Random();

    // Generic Pair class
    private static class Pair<T, U> {
        T first;
        U second;

        Pair(T f, U s) {
            this.first = f;
            this.second = s;
        }
    }

    public CustomCountBoardSetupStrategy(int snakes, int ladders, boolean randomPositions) {
        this.numSnakes = snakes;
        this.numLadders = ladders;
        this.randomPositions = randomPositions;
    }

    public void addSnakePosition(int start, int end) {
        snakePositions.add(new Pair<>(start, end));
    }

    public void addLadderPosition(int start, int end) {
        ladderPositions.add(new Pair<>(start, end));
    }

    @Override
    public void setupBoard(Board board) {

        int size = board.getBoardSize();

        if (randomPositions) {

            int snakesAdded = 0;
            while (snakesAdded < numSnakes) {
                int start = random.nextInt(size - 10) + 10;
                int end = random.nextInt(start - 1) + 1;

                if (board.canAddEntity(start)) {
                    board.addBoardEntity(new Snake(start, end));
                    snakesAdded++;
                }
            }

            int laddersAdded = 0;
            while (laddersAdded < numLadders) {
                int start = random.nextInt(size - 10) + 1;
                int end = random.nextInt(size - start) + start + 1;

                if (board.canAddEntity(start) && end < size) {
                    board.addBoardEntity(new Ladder(start, end));
                    laddersAdded++;
                }
            }

        } else {

            for (Pair<Integer, Integer> p : snakePositions) {
                if (board.canAddEntity(p.first)) {
                    board.addBoardEntity(new Snake(p.first, p.second));
                }
            }

            for (Pair<Integer, Integer> p : ladderPositions) {
                if (board.canAddEntity(p.first)) {
                    board.addBoardEntity(new Ladder(p.first, p.second));
                }
            }
        }
    }
}