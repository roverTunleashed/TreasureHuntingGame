package ca.sfu.cmpt213.a2.model;

/**
 * Cell class that has the coordinates of each cell, the status and if is has been visited or not
 */

public class Cell {
    public boolean isVisited;
    private int x;
    private int y;
    private String status; //the symbol associated with each cell(#, " ")

    public void setVisited(boolean visited) {
        isVisited = visited;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public Cell(int xCoordinate, int yCoordinate, String status, boolean isVisited) {
        this.isVisited = isVisited;
        this.x = xCoordinate;
        this.y = yCoordinate;
        this.status = status;
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }
}
