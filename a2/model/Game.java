package ca.sfu.cmpt213.a2.model;


import java.util.Random;
import java.util.Scanner;

/**
 * Game class that deals with the game logic, gets user input and generates the Guardians/Relics
 */

public class Game {
    Guardian firstGuardian;
    TreasureHunter treasureHunter;
    Guardian secondGuardian;
    Guardian thirdGuardian;
    Maze maze;
    int relicsLeft;
    int relicsCollected;
    boolean gameOn = true;

    final Random rand = new Random(); //random generator

    //getUserInput input = new getUserInput();
    public Game(Maze maze) {
        this.maze = maze;
        this.relicsLeft = 3;// 3 to start the game
        this.relicsCollected = 0;//0 to start the game
    }

    public void revealedMap() {
        maze.initialGeneration();
        maze.primsAlgorithm();
        firstGuardian = new Guardian(18, 14, "!");
        maze.getMaze()[18][14].setStatus(firstGuardian.getStatus());
        secondGuardian = new Guardian(18, 1, "!");
        maze.getMaze()[18][1].setStatus(secondGuardian.getStatus());
        thirdGuardian = new Guardian(1, 14, "!");
        maze.getMaze()[1][14].setStatus(thirdGuardian.getStatus());
        treasureHunter = new TreasureHunter(1, 1, "@");
        maze.getMaze()[1][1].setStatus(treasureHunter.getStatus());
        generateRandomRelics();
        maze.print();
    }


    public void start() {
        revealedMap();
        Scanner reader = new Scanner(System.in);
        System.out.println("Total number of relics to be collected: " + relicsLeft +
                "\nNumber of relics currently in possession: " + relicsCollected);
        System.out.println("Enter your move: [WASD]");
        while (gameOn) {
            String input;
            input = reader.nextLine();
            while (!input.equals("W") && !input.equals("w") && !input.equals("A") && !input.equals("a")
                    && !input.equals("S") && !input.equals("s") && !input.equals("D") && !input.equals("d")
                    && !input.equals("?") && !input.equals("M") && !input.equals("m") && !input.equals("C")
                    && !input.equals("c")) {
                System.out.println("Invalid move: please try again!\n" +
                        "Enter your move [WASD?]:");
                input = reader.nextLine();
            }

            //Special Cases
            if (input.equals("?")) {
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
                        "(You must press enter after each move).\n" +
                        "Enter your move [WASD]";
                System.out.println(title);
            }
            if (input.equals("M") || input.equals("m")) {
                maze.print();
                System.out.println("Total number of relics to be collected: " + relicsLeft);
                System.out.println("Number of relics in possession: " + relicsCollected);
                System.out.println("Enter your move: [WASD]");
            }
            if (input.equals("C") || input.equals("c")) {
                relicsLeft = 1;
                System.out.println("Enter your move: [WASD]");
            }
            //WASD
            if (input.equals("W") || input.equals("w")) {
                int up = treasureHunter.getX() - 1;
                if (maze.getMaze()[up][treasureHunter.getY()].getStatus().equals(maze.wallStatus)) {
                    System.out.println("Invalid move: you cannot move through walls!");
                    System.out.println("Enter your move [WASD?]:");
                }
                if (maze.getMaze()[up][treasureHunter.getY()].getStatus().equals(maze.passageStatus)) {
                    maze.getMaze()[treasureHunter.getX()][treasureHunter.getY()].setStatus(" ");
                    treasureHunter.setX(up);//update the current position
                    maze.getMaze()[up][treasureHunter.getY()].setStatus("@");
                    moveGuardians(firstGuardian);
                    moveGuardians(secondGuardian);
                    moveGuardians(thirdGuardian);
                    maze.print();
                    System.out.println("Total number of relics to be collected: " + relicsLeft);
                    System.out.println("Number of relics in possession: " + relicsCollected);
                    System.out.println("Enter your move: [WASD]");
                }
                //collecting a relic
                if (maze.getMaze()[up][treasureHunter.getY()].getStatus().equals("^")) {
                    relicsCollected++;
                    relicsLeft--;
                    maze.getMaze()[treasureHunter.getX()][treasureHunter.getY()].setStatus(" ");
                    treasureHunter.setX(up);
                    maze.getMaze()[up][treasureHunter.getY()].setStatus("@");
                    if (relicsLeft == 0) {
                        System.out.println("Congratulations! You Won!");
                        maze.print();
                        System.out.println("Total number of relics to be collected: " + relicsLeft);
                        System.out.println("Number of relics in possession: " + relicsCollected);
                        gameOn = false;
                        break;// no need to run the rest of the loop as the game is now over
                    }
                    moveGuardians(firstGuardian);
                    moveGuardians(secondGuardian);
                    moveGuardians(thirdGuardian);
                    generateRandomRelics();//generates a new relic
                    maze.print();
                    System.out.println("Total number of relics to be collected: " + relicsLeft);
                    System.out.println("Number of relics in possession: " + relicsCollected);
                    System.out.println("Enter your move: [WASD]");
                }
                //getting killed by guardian
                if (maze.getMaze()[up][treasureHunter.getY()].getStatus().equals("!")) {
                    maze.getMaze()[treasureHunter.getX()][treasureHunter.getY()].setStatus(" ");
                    treasureHunter.setX(up);
                    maze.getMaze()[up][treasureHunter.getY()].setStatus("X");
                    System.out.println("Oh no! The hunter has been killed!");
                    maze.print();
                    System.out.println("Total number of relics to be collected: " + relicsLeft);
                    System.out.println("Number of relics in possession: " + relicsCollected);
                    System.out.println("GAME OVER... please try again.");
                    gameOn = false;
                    break;
                }
            }
            if (input.equals("A") || input.equals("a")) {
                int left = treasureHunter.getY() - 1;
                if (maze.getMaze()[treasureHunter.getX()][left].getStatus().equals(maze.wallStatus)) {
                    System.out.println("Invalid move: you cannot move through walls!");
                    System.out.println("Enter your move [WASD?]:");
                }
                if (maze.getMaze()[treasureHunter.getX()][left].getStatus().equals(maze.passageStatus)) {
                    maze.getMaze()[treasureHunter.getX()][treasureHunter.getY()].setStatus(" ");
                    treasureHunter.setY(left);
                    maze.getMaze()[treasureHunter.getX()][left].setStatus("@");
                    moveGuardians(firstGuardian);
                    moveGuardians(secondGuardian);
                    moveGuardians(thirdGuardian);
                    maze.print();
                    System.out.println("Total number of relics to be collected: " + relicsLeft);
                    System.out.println("Number of relics in possession: " + relicsCollected);
                    System.out.println("Enter your move: [WASD]");
                }
                //Collecting relic
                if (maze.getMaze()[treasureHunter.getX()][left].getStatus().equals("^")) {
                    relicsCollected++;
                    relicsLeft--;
                    maze.getMaze()[treasureHunter.getX()][treasureHunter.getY()].setStatus(" ");
                    treasureHunter.setY(left);
                    maze.getMaze()[treasureHunter.getX()][left].setStatus("@");
                    if (relicsLeft == 0) {
                        System.out.println("Congratulations! You Won!");
                        maze.print();
                        System.out.println("Total number of relics to be collected: " + relicsLeft);
                        System.out.println("Number of relics in possession: " + relicsCollected);
                        gameOn = false;
                        break;//game is over
                    }
                    moveGuardians(firstGuardian);
                    moveGuardians(secondGuardian);
                    moveGuardians(thirdGuardian);
                    generateRandomRelics();
                    maze.print();
                    System.out.println("Total number of relics to be collected: " + relicsLeft);
                    System.out.println("Number of relics in possession: " + relicsCollected);
                    System.out.println("Enter your move: [WASD]");
                }
                //Getting killed by the guardian
                if (maze.getMaze()[treasureHunter.getX()][left].getStatus().equals("!")) {
                    maze.getMaze()[treasureHunter.getX()][treasureHunter.getY()].setStatus(" ");
                    treasureHunter.setY(left);
                    maze.getMaze()[treasureHunter.getX()][left].setStatus("X");
                    System.out.println("Oh no! The hunter has been killed!");
                    maze.print();
                    System.out.println("Total number of relics to be collected: " + relicsLeft);
                    System.out.println("Number of relics in possession: " + relicsCollected);
                    System.out.println("GAME OVER... please try again.");
                    gameOn = false;
                    break;
                }

            }
            if (input.equals("S") || input.equals("s")) {
                int down = treasureHunter.getX() + 1;
                if (maze.getMaze()[down][treasureHunter.getY()].getStatus().equals((maze.wallStatus))) {
                    System.out.println("Invalid move: you cannot move through walls!");
                    System.out.println("Enter your move [WASD?]:");
                }
                if ((maze.getMaze()[down][treasureHunter.getY()].getStatus().equals(maze.passageStatus))) {
                    maze.getMaze()[treasureHunter.getX()][treasureHunter.getY()].setStatus(" ");
                    treasureHunter.setX(down);
                    maze.getMaze()[down][treasureHunter.getY()].setStatus("@");
                    moveGuardians(firstGuardian);
                    moveGuardians(secondGuardian);
                    moveGuardians(thirdGuardian);
                    maze.print();
                    System.out.println("Total number of relics to be collected: " + relicsLeft);
                    System.out.println("Number of relics in possession: " + relicsCollected);
                    System.out.println("Enter your move: [WASD]");
                }
                //collecting relic
                if (maze.getMaze()[down][treasureHunter.getY()].getStatus().equals("^")) {
                    relicsCollected++;
                    relicsLeft--;
                    maze.getMaze()[treasureHunter.getX()][treasureHunter.getY()].setStatus(" ");
                    treasureHunter.setX(down);
                    maze.getMaze()[down][treasureHunter.getY()].setStatus("@");
                    //win
                    if (relicsLeft == 0) {
                        System.out.println("Congratulations! You Won!");
                        maze.print();
                        System.out.println("Total number of relics to be collected: " + relicsLeft);
                        System.out.println("Number of relics in possession: " + relicsCollected);
                        gameOn = false;
                        break;
                    }
                    moveGuardians(firstGuardian);
                    moveGuardians(secondGuardian);
                    moveGuardians(thirdGuardian);
                    generateRandomRelics();//generates a new relic
                    maze.print();
                    System.out.println("Total number of relics to be collected: " + relicsLeft);
                    System.out.println("Number of relics in possession: " + relicsCollected);
                    System.out.println("Enter your move: [WASD]");
                }
                //kill case 1) Hunter walks into a Guardian
                if (maze.getMaze()[down][treasureHunter.getY()].getStatus().equals("!")) {
                    maze.getMaze()[treasureHunter.getX()][treasureHunter.getY()].setStatus(" ");
                    treasureHunter.setX(down);
                    maze.getMaze()[down][treasureHunter.getY()].setStatus("X");
                    System.out.println("Oh no! The hunter has been killed!");
                    maze.print();
                    System.out.println("Total number of relics to be collected: " + relicsLeft);
                    System.out.println("Number of relics in possession: " + relicsCollected);
                    System.out.println("GAME OVER... please try again.");
                    gameOn = false;
                    break;
                }
            }
            if (input.equals("D") || input.equals("d")) {
                int right = treasureHunter.getY() + 1;
                if (maze.getMaze()[treasureHunter.getX()][right].getStatus().equals(maze.wallStatus)) {
                    System.out.println("Invalid move: you cannot move through walls!");
                    System.out.println("Enter your move [WASD?]:");
                }
                if (maze.getMaze()[treasureHunter.getX()][right].getStatus().equals(maze.passageStatus)) {
                    maze.getMaze()[treasureHunter.getX()][treasureHunter.getY()].setStatus(" ");//update previous path
                    treasureHunter.setY(right);
                    maze.getMaze()[treasureHunter.getX()][right].setStatus("@");
                    moveGuardians(firstGuardian);
                    moveGuardians(secondGuardian);
                    moveGuardians(thirdGuardian);
                    maze.print();
                    System.out.println("Total number of relics to be collected: " + relicsLeft);
                    System.out.println("Number of relics in possession: " + relicsCollected);
                    System.out.println("Enter your move: [WASD]");
                }
                //collecting relic
                if (maze.getMaze()[treasureHunter.getX()][right].getStatus().equals("^")) {
                    relicsCollected++;
                    relicsLeft--;
                    maze.getMaze()[treasureHunter.getX()][treasureHunter.getY()].setStatus(" ");
                    treasureHunter.setY(right);
                    maze.getMaze()[treasureHunter.getX()][right].setStatus("@");
                    if (relicsLeft == 0) {
                        System.out.println("Congratulations! You Won!");
                        maze.print();
                        System.out.println("Total number of relics to be collected: " + relicsLeft);
                        System.out.println("Number of relics in possession: " + relicsCollected);
                        gameOn = false;
                        break;
                    }
                    moveGuardians(firstGuardian);
                    moveGuardians(secondGuardian);
                    moveGuardians(thirdGuardian);
                    generateRandomRelics();//generates a new relic
                    maze.print();
                    System.out.println("Total number of relics to be collected: " + relicsLeft);
                    System.out.println("Number of relics in possession: " + relicsCollected);
                    System.out.println("Enter your move: [WASD]");
                }
                //kill case 1) hunter walks into a guardian
                if (maze.getMaze()[treasureHunter.getX()][right].getStatus().equals("!")) {
                    maze.getMaze()[treasureHunter.getX()][treasureHunter.getY()].setStatus(" ");
                    treasureHunter.setY(right);
                    maze.getMaze()[treasureHunter.getX()][right].setStatus("X");
                    System.out.println("Oh no! The hunter has been killed!");
                    maze.print();
                    System.out.println("Total number of relics to be collected: " + relicsLeft);
                    System.out.println("Number of relics in possession: " + relicsCollected);
                    System.out.println("GAME OVER... please try again.");
                    gameOn = false;
                    break;//game is over so get out of the while loop once the code is done
                }

            }

        }

    }

    public void generateRandomRelics() {
        //https://stackoverflow.com/questions/363681/how-do-i-generate-random-integers-within-a-specific-range-in-java
        //used to update random number generation to start at 1 instead of 0 (so that the relic is not on a wall)
        int generateX = rand.nextInt(maze.length - 2) + 1;//to ensure the relic isn't on a wall (generates from 1-18)
        int generateY = rand.nextInt(maze.width - 2) + 1;//to ensure the relic is not on a wall (generates from 1-14)
        for (int i = 0; i < maze.length - 1; i++) {
            for (int j = 0; j < maze.width - 1; j++) {
                //A relic cant be on a hunter or wall and cant start where the hunter starts
                if (!maze.getMaze()[generateX][generateY].getStatus().equals(treasureHunter.getStatus())
                        || (!maze.getMaze()[generateX][generateY].getStatus().equals("#"))
                        || !(generateX == 1 && generateY == 1)) {
                    Relic relic = new Relic(generateX, generateY, "^");
                    maze.getMaze()[generateX][generateY].setStatus(relic.getStatus());
                }
            }
        }
    }

    public void moveGuardians(Guardian guard) {
        //randomly generate a direction to check, in this case up or down
        boolean isFinished = false;
        while (!isFinished)//Cycles till it finds a path
        {
            int generateUpDownLeftRight = rand.nextInt(4) + 1;//randomly generates 1-4
            int up = guard.getX() - 1;
            int down = guard.getX() + 1;
            int left = guard.getY() - 1;
            int right = guard.getY() + 1;
            if (generateUpDownLeftRight == 1) {
                if (maze.getMaze()[up][guard.getY()].getStatus().equals("#"))//hits a wall
                {
                    generateUpDownLeftRight = rand.nextInt(4)+1;//regenerate
                }
                if (maze.getMaze()[up][guard.getY()].getStatus().equals(" ")) {
                    maze.getMaze()[guard.getX()][guard.getY()].setStatus(" ");//update previous path
                    guard.setX(up);
                    maze.getMaze()[up][guard.getY()].setStatus("!");
                    isFinished = true;
                }
                //kills a hunter
                if (maze.getMaze()[up][guard.getY()].getStatus().equals("@")) {
                    maze.getMaze()[guard.getX()][guard.getY()].setStatus(" ");
                    guard.setX(up);
                    maze.getMaze()[up][guard.getY()].setStatus("X");
                    System.out.println("Oh no! The hunter has been killed!");
                    maze.print();
                    System.out.println("Total number of relics to be collected: " + relicsLeft);
                    System.out.println("Number of relics in possession: " + relicsCollected);
                    System.out.println("GAME OVER... please try again.");
                    System.exit(0);//Game Over!
                }
                //conflicts with a relic
                if (maze.getMaze()[up][guard.getY()].getStatus().equals("^")) {
                    generateUpDownLeftRight = rand.nextInt(4)+1;
                }
            }
            if (generateUpDownLeftRight == 2) {
                if (maze.getMaze()[down][guard.getY()].getStatus().equals("#")) {
                    generateUpDownLeftRight = rand.nextInt(4)+1;//regenerate
                }
                if (maze.getMaze()[down][guard.getY()].getStatus().equals(" ")) {
                    maze.getMaze()[guard.getX()][guard.getY()].setStatus(" ");
                    guard.setX(down);
                    maze.getMaze()[down][guard.getY()].setStatus("!");
                    isFinished = true;
                }
                //if the guardian reaches the relic
                if (maze.getMaze()[down][guard.getY()].getStatus().equals("^"))
                {
                    generateUpDownLeftRight = rand.nextInt(4)+1;//regenerate
                }
                //kill case 2) guardian walks into a hunter
                if (maze.getMaze()[down][guard.getY()].getStatus().equals("@")) {
                    maze.getMaze()[guard.getX()][guard.getY()].setStatus(" ");
                    guard.setX(down);
                    maze.getMaze()[down][guard.getY()].setStatus("X");
                    System.out.println("Oh no! The hunter has been killed!");
                    maze.print();
                    System.out.println("Total number of relics to be collected: " + relicsLeft);
                    System.out.println("Number of relics in possession: " + relicsCollected);
                    System.out.println("GAME OVER... please try again.");
                    System.exit(0);//Game Over!
                }
            }
            if (generateUpDownLeftRight == 3) {
                if (maze.getMaze()[guard.getX()][left].getStatus().equals("#")) {
                    generateUpDownLeftRight = rand.nextInt(4)+1;//regenerate
                }
                if (maze.getMaze()[guard.getX()][left].getStatus().equals(" ")) {
                    maze.getMaze()[guard.getX()][guard.getY()].setStatus(" ");
                    guard.setY(left);
                    maze.getMaze()[guard.getX()][left].setStatus("!");
                    isFinished = true;
                }
                //kill case 2: guardian walks into a hunter
                if (maze.getMaze()[guard.getX()][left].getStatus().equals("@")) {
                    maze.getMaze()[guard.getX()][guard.getY()].setStatus(" ");
                    guard.setY(left);
                    maze.getMaze()[guard.getX()][left].setStatus("X");
                    System.out.println("Oh no! The hunter has been killed!");
                    maze.print();
                    System.out.println("Total number of relics to be collected: " + relicsLeft);
                    System.out.println("Number of relics in possession: " + relicsCollected);
                    System.out.println("GAME OVER... please try again.");
                    System.exit(0);//Game Over!
                }
                //conflicts with a relic
                if (maze.getMaze()[guard.getX()][left].getStatus().equals("^")) {
                    generateUpDownLeftRight = rand.nextInt(4)+1;//regenerate
                }

            }
            if (generateUpDownLeftRight == 4) {
                if (maze.getMaze()[guard.getX()][right].getStatus().equals("#")) {
                    generateUpDownLeftRight = rand.nextInt(4)+1;//regenerate
                }
                if (maze.getMaze()[guard.getX()][right].getStatus().equals(" ")) {
                    maze.getMaze()[guard.getX()][guard.getY()].setStatus(" ");
                    guard.setY(right);
                    maze.getMaze()[guard.getX()][right].setStatus("!");
                    isFinished = true;
                }
                if (maze.getMaze()[guard.getX()][right].getStatus().equals("@")) {
                    maze.getMaze()[guard.getX()][guard.getY()].setStatus(" ");
                    guard.setY(right);
                    maze.getMaze()[guard.getX()][right].setStatus("X");
                    System.out.println("Oh no! The hunter has been killed!");
                    maze.print();
                    System.out.println("Total number of relics to be collected: " + relicsLeft);
                    System.out.println("Number of relics in possession: " + relicsCollected);
                    System.out.println("GAME OVER... please try again.");
                    System.exit(0);//Game Over!
                }
                //conflicts with a relic
                if (maze.getMaze()[guard.getX()][right].getStatus().equals("^")) {
                    generateUpDownLeftRight = rand.nextInt(4)+1;//regenerate
                }
            }

        }


    }
}
