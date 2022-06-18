package ca.sfu.cmpt213.a2.model;


/**
 * Maze class that uses the Cell class to make a maze using Prim's algorithm
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Maze {
    //maze parameters
    int length;
    int width;
    String wallStatus = "#";
    String passageStatus = " ";
    final Random rand = new Random(); //random generator
    private final Cell[][] maze;
    List<Cell> walls = new ArrayList<>();

    public Maze(int length, int width) {
        this.length = length;
        this.width = width;
        maze = new Cell[length][width];
    }

    public Cell[][] getMaze() //returns a certain cell in the maze
    {
        return maze;
    }

    public void primsAlgorithm() {
        //pick a random cell
        //https://stackoverflow.com/questions/363681/how-do-i-generate-random-integers-within-a-specific-range-in-java
        //ensure that the random cell picked is not 0 by changing the range of rand.nextInt to 1 instead of 0
        int generateX = rand.nextInt(length - 2) + 1;//length - 2 to get the numbers with an upper bound of 18
        int generateY = rand.nextInt(width - 2) + 1;//width - 2 to the numbers with a lower bound of 14
        //mark it as part of maze, set it as path
        maze[generateX][generateY].setVisited(true);
        maze[generateX][generateY].setStatus(passageStatus);
        neighbours(generateX, generateY); //Add the walls (neighbours) to the wall list
        while (!walls.isEmpty()) {
            int randomGenerator = (rand.nextInt(walls.size()));
            int currentX = walls.get(randomGenerator).getX();
            int currentY = walls.get(randomGenerator).getY();
            pathBuilder(currentX, currentY);
            walls.remove(maze[currentX][currentY]);
        }
    }

    public void pathBuilder(int currentX, int currentY) {
        //check if the randomWall is separated by an explored cell(which it always does)
        //and use that explored cell to determine the direction of the new path
        int checkBelowWall = currentX + 1;
        String statusBelow = maze[checkBelowWall][currentY].getStatus();
        int checkAboveWall = currentX - 1;
        String statusAbove = maze[checkAboveWall][currentY].getStatus();
        int checkLeftWall = currentY - 1;
        String statusLeft = maze[currentX][checkLeftWall].getStatus();
        int checkRightWall = currentY + 1;
        String statusRight = maze[currentX][checkRightWall].getStatus();
        //the explored cell is below
        if (maze[checkBelowWall][currentY].isVisited && statusBelow.equals(passageStatus)) {
            if (checkAboveWall == 0) //border case, add just the current cell
            {
                maze[currentX][currentY].setStatus(passageStatus);
                maze[currentX][currentY].setVisited(true);
            }
            //because the explored cell is below, check the opposite direction, up
            //check if the opposite wall is visited & not a border
            if (!maze[checkAboveWall][currentY].isVisited && checkAboveWall != 0) {
                maze[currentX][currentY].setStatus(passageStatus);//set current cell to be a path
                maze[currentX][currentY].setVisited(true);
                maze[checkAboveWall][currentY].setVisited(true);//set the wall above to be a path
                maze[checkAboveWall][currentY].setStatus(passageStatus);
                neighbours(checkAboveWall, currentY);//add neighbouring walls
            }

        }
        //the explored cell is above
        if (maze[checkAboveWall][currentY].isVisited && statusAbove.equals(passageStatus)) {
            if (checkBelowWall == length - 1)//reached a border, add current cell to path
            {
                maze[currentX][currentY].setStatus(passageStatus);
                maze[currentX][currentY].setVisited(true);
            }

            if (!maze[checkBelowWall][currentY].isVisited && checkBelowWall != length - 1) {
                maze[currentX][currentY].setStatus(passageStatus);
                maze[currentX][currentY].setVisited(true);
                maze[checkBelowWall][currentY].setVisited(true);
                maze[checkBelowWall][currentY].setStatus(passageStatus);
                neighbours(checkBelowWall, currentY);
            }
        }
        //the explored cell is to the right, check the left path
        if (maze[currentX][checkRightWall].isVisited && statusRight.equals(passageStatus)) {
            if (checkLeftWall == 0) {
                maze[currentX][currentY].setStatus(passageStatus);
                maze[currentX][currentY].setVisited(true);
            }
            //check if the opposite wall is visited & not a border
            if (!maze[currentX][checkLeftWall].isVisited && checkLeftWall != 0) {
                maze[currentX][currentY].setStatus(passageStatus);
                maze[currentX][currentY].setVisited(true);
                maze[currentX][checkLeftWall].setVisited(true);
                maze[currentX][checkLeftWall].setStatus(passageStatus);
                neighbours(currentX, checkLeftWall);
            }
        }
        //the explored cell is to the left, check the right path
        if (maze[currentX][checkLeftWall].isVisited && statusLeft.equals(passageStatus)) {
            if (checkRightWall == width - 1) {
                maze[currentX][currentY].setStatus(passageStatus);
                maze[currentX][currentY].setVisited(true);
            }
            //check if the opposite wall is visited & not a border
            if (!maze[currentX][checkRightWall].isVisited && checkRightWall != width - 1) {
                maze[currentX][currentY].setStatus(passageStatus);
                maze[currentX][currentY].setVisited(true);
                maze[currentX][checkRightWall].setVisited(true);
                maze[currentX][checkRightWall].setStatus(passageStatus);
                neighbours(currentX, checkRightWall);
            }
        }
    }

    public void neighbours(int x, int y)//neighbours given the x and y coordinate
    {
        int top = x - 1;
        int bot = x + 1;
        int left = y - 1;
        int right = y + 1;

        int topBorder = 0;
        int leftBorder = 0;
        int botBorder = length - 1;
        int rightBorder = width - 1;
        if ((bot != botBorder) && maze[bot][y].getStatus().equals(wallStatus)) {
            if (!walls.contains(maze[bot][y])) {
                walls.add(maze[bot][y]);
            }
        }

        if ((top != topBorder) && maze[top][y].getStatus().equals(wallStatus))//checks to see if the top neighbour is at the border
        {
            //used contains to check if the current neighbour is already in the list
            if (!walls.contains(maze[top][y])) {
                walls.add(maze[top][y]);
            }
        }

        if ((left != leftBorder) && (maze[x][left].getStatus().equals(wallStatus))) {
            if (!walls.contains(maze[x][left])) {
                walls.add(maze[x][left]);
            }
        }
        if ((right != rightBorder) && maze[x][right].getStatus().equals(wallStatus)) {
            if (!walls.contains(maze[x][right])) {
                walls.add(maze[x][right]);
            }
        }
    }
    //fill the maze with the walls
    public void initialGeneration() {
        for (int i = 0; i < length; ++i) {
            for (int j = 0; j < width; ++j) {
                maze[i][j] = new Cell(i, j, wallStatus, false);
            }
        }
    }
    //prints out the maze
    public void print() {
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < width; j++) {
                System.out.print(maze[i][j].getStatus());
            }
            System.out.println();
        }
    }

}