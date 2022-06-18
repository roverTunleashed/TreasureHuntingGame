package ca.sfu.cmpt213.a2;

import ca.sfu.cmpt213.a2.model.Maze;
import ca.sfu.cmpt213.a2.model.Game;
import ca.sfu.cmpt213.a2.textui.UI;

/**
 * Main class to start the game and print the intro
 */

public class Main {
    public static void main(String[] args) {
        UI test = new UI();
        int length = 20;
        int width = 16;
        Game game;
        game = new Game(new Maze(length, width));
        test.intro();
        game.start();
    }
}
