package ca.sfu.cmpt213.a2.model;

/**
 * TreasureHunter class that has the coordinates of the relics, setters/getters and its status
 */
public class TreasureHunter {
    private int x;
    private int y;
    private String status; // @

    public TreasureHunter(int x, int y, String status) {
        this.x = x;
        this.y = y;
        this.status = status;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
