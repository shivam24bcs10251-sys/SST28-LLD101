import game.*;
import observer.*;
import player.*;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        SnakeAndLadderGame game = SnakeAndLadderGameFactory.createGame();

        game.addObserver(new SnakeAndLadderConsoleNotifier());

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter players: ");
        int n = sc.nextInt();
        sc.nextLine();

        for (int i = 0; i < n; i++) {
            System.out.print("Name: ");
            String name = sc.nextLine();
            game.addPlayer(new SnakeAndLadderPlayer(i, name));
        }

        game.play();
    }
}