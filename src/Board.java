import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Board
{

    private SquareType[][] squares;
    private int width;
    private int height;

    private Food food = null;
    private Snake snakeHead = null;

    private List<BoardListener> listeners = new ArrayList<>();


    private static final int SQUAREWIDTH = 20;
    private static final int SQUAREHEIGHT = 20;

    private int squareWidth;
    private int squareHeight;

    private CollisionHandler collisionHandler = new DefaultCollisionHandler();

    private String currentDirection = "still";

    private List<Snake> snakeJoints = new ArrayList<>();

    private boolean gameOver = false;

    public Board(final int width, final int height) {
        this.width = width;
        this.height = height;
        this.squareWidth = SQUAREWIDTH;
        this.squareHeight = SQUAREHEIGHT;
        squares = new SquareType[width+4][height+4];

        // Creates a border outside the board
        for (int i = 0; i < width; i++) {
            for (int j =0; j < height; j++) {
                squares[i][j] = SquareType.OBSTACLE;
            }
        }

        for (int i = 1; i < width - 1; i++) {
            for (int j = 1; j<height - 1; j++) {
                squares[i][j] = SquareType.EMPTY;
            }
        }
        spawnFood();
        spawnSnakeHead();
    }

    public void spawnFood() {
        int random1 = ThreadLocalRandom.current().nextInt(0, this.width);
        int random2 = ThreadLocalRandom.current().nextInt(0, this.height);

        if (squares[random1][random2] == SquareType.EMPTY) {
            food = new Food(random1, random2);
            squares[random1][random2] = SquareType.FOOD;
        }
        else {
            spawnFood();
        }
    }

    public void removeFood() {
        squares[food.getxPos()][food.getyPos()] = SquareType.EMPTY;
        food = null;
    }

    public void spawnSnakeHead() {
        int random1 = ThreadLocalRandom.current().nextInt(0, this.width);
        int random2 = ThreadLocalRandom.current().nextInt(0, this.height);

        if (squares[random1][random2] == SquareType.EMPTY) {
            snakeHead = new Snake(random1, random2);
            snakeJoints.add(snakeHead);
            squares[random1][random2] = SquareType.SNAKE;
        }
        else {
            spawnSnakeHead();
        }
    }

    public void spawnSnakeJoint() {
        Snake joint = new Snake(snakeHead.getxPos(), snakeHead.getyPos());
        snakeJoints.add(0, joint);
    }

    public void tick() {
        squares[snakeHead.getxPos()][snakeHead.getyPos()] = SquareType.SNAKE;
        switch (currentDirection) {
            case "up":
                moveJoints();
                snakeHead.setyPos(snakeHead.getyPos() - 1);
                collisionHandler.handleCollision(this);
                squares[snakeHead.getxPos()][snakeHead.getyPos()] = SquareType.SNAKE;
                break;
            case "left":
                moveJoints();
                snakeHead.setxPos(snakeHead.getxPos() - 1);
                collisionHandler.handleCollision(this);
                squares[snakeHead.getxPos()][snakeHead.getyPos()] = SquareType.SNAKE;
            break;
            case "right":
                moveJoints();
                snakeHead.setxPos(snakeHead.getxPos() + 1);
                collisionHandler.handleCollision(this);
                squares[snakeHead.getxPos()][snakeHead.getyPos()] = SquareType.SNAKE;
                break;
            case "down":
                moveJoints();
                snakeHead.setyPos(snakeHead.getyPos() + 1);
                collisionHandler.handleCollision(this);
                squares[snakeHead.getxPos()][snakeHead.getyPos()] = SquareType.SNAKE;
                break;
            default:
                break;
        }

        notifyListeners();
    }

    public void moveJoints() {
        Snake tail = snakeJoints.get(0);
        squares[tail.getxPos()][tail.getyPos()] = SquareType.EMPTY;
        for (int i = 0; i < snakeJoints.size()-1; i++) {
            Snake joint = snakeJoints.get(i);
            Snake nextJoint = snakeJoints.get(i+1);
            joint.setyPos(nextJoint.getyPos());
            joint.setxPos(nextJoint.getxPos());
            squares[joint.getxPos()][joint.getyPos()] = SquareType.SNAKE;
        }

    }

    public void moveUp() {
        if (!currentDirection.equals("down")) {
            currentDirection = "up";
        }
    }

    public void moveLeft() {
        if (!currentDirection.equals("right")) {
            currentDirection = "left";
        }
    }

    public void moveRight() {
        if (!currentDirection.equals("left")) {
            currentDirection = "right";
        }
    }

    public void moveDown() {
        if (!currentDirection.equals("up")) {
            currentDirection = "down";
        }
    }

    public void addBoardListener(BoardListener bl) {
            listeners.add(bl);
        }

    private void notifyListeners() {
    	for (BoardListener listener : listeners) {
    	    listener.boardChanged();
    	}
    }

    public void setGameOver(final boolean gameOver) {
        this.gameOver = gameOver;
    }

    public SquareType getSquareType(int x, int y) {
        return squares[x][y];
    }

    public int getHeight() {
           return height;
       }

    public int getWidth() {
        return width;
    }

    public Snake getSnakeHead() {
        return snakeHead;
    }

    public Food getFood() {
        return food;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public int getSquareWidth() {
        return squareWidth;
    }

    public int getSquareHeight() {
        return squareHeight;
    }
}
