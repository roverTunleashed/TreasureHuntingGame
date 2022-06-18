package ca.sfu.cmpt213.a2.textui;


/**
 * UI class that prints the intro the screen and does not use any classes from model.
 */

public class UI {
    public void intro() {
        String title = "DIRECTIONS:\n" +
                "Collect 3 relics!\n" +
                "LEGEND:\n" +
                "#: Wall\n" +
                "@: You (the treasure hunter)\n" +
                "!: Guardian\n" +
                "^: Relic\n" +
                ".: Unexplored space\n" +
                "MOVES:\n" +
                "Use W (up), A (left), S (down) and D (right) to move.\n" +
                "(You must press enter after each move).";
        System.out.println(title);
    }


}
