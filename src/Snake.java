

public class Snake
{
    private int xPos;
    private int yPos;

    public Snake(final int xPos, final int yPos) {
	this.xPos = xPos;
	this.yPos = yPos;
    }

    public void setxPos(final int xPos) {
	this.xPos = xPos;
    }

    public void setyPos(final int yPos) {
	this.yPos = yPos;
    }

    public int getxPos() {
	return xPos;
    }

    public int getyPos() {
	return yPos;
    }
}
