package game;

import board.*;
import dice.Dice;
import observer.IObserver;
import player.SnakeAndLadderPlayer;
import strategy.*;

import java.util.*;

public class SnakeAndLadderGame {

    private Board board;
    private Dice dice;
    private Deque<SnakeAndLadderPlayer> players = new ArrayDeque<>();
    private List<IObserver> observers = new ArrayList<>();
    private SnakeAndLadderRules rules = new StandardSnakeAndLadderRules();

    public SnakeAndLadderGame(Board board, Dice dice) {
        this.board = board;
        this.dice = dice;
    }

    public void addPlayer(SnakeAndLadderPlayer player) {
        players.add(player);
    }

    public void addObserver(IObserver observer) {
        observers.add(observer);
    }

    private void notifyObservers(String msg) {
        for (IObserver o : observers) {
            o.update(msg);
        }
    }

    public void play() {
        Scanner sc = new Scanner(System.in);

        while (true) {
            SnakeAndLadderPlayer player = players.poll();

            System.out.println(player.getName() + " press enter");
            sc.nextLine();

            int diceValue = dice.roll();
            int curr = player.getPosition();

            if (rules.isValidMove(curr, diceValue, board.getBoardSize())) {
                int newPos = rules.calculateNewPosition(curr, diceValue, board);
                player.setPosition(newPos);

                notifyObservers(player.getName() + " moved to " + newPos);

                if (rules.checkWinCondition(newPos, board.getBoardSize())) {
                    System.out.println(player.getName() + " wins!");
                    break;
                }
            }

            players.add(player);
        }
    }
}