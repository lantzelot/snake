import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Board
{

    private SquareType[][] squares;
    private int width;
    private int height;

   // private Food food = null;
    private List<Food> foodList = new ArrayList<>();
    private Snake snakeHead = null;

    private List<BoardListener> listeners = new ArrayList<>();


    private static final int SQUAREWIDTH = 20;
    private static final int SQUAREHEIGHT = 20;

    private int squareWidth;
    private int squareHeight;

    private CollisionHandler collisionHandler = new DefaultCollisionHandler();

    private String currentDirection = "still";

    private List<Snake> snakeJoints = new ArrayList<>();

    private int currentScore;

    private boolean gameOver = false;

    public Board(final int width, final int height) {
        this.width = width;
        this.height = height;
        this.squareWidth = SQUAREWIDTH;
        this.squareHeight = SQUAREHEIGHT;
        squares = new SquareType[width][height];

        // Creates a border outside the board
       /* for (int i = 0; i < width; i++) {
            for (int j =0; j < height; j++) {
                squares[i][j] = SquareType.OBSTACLE;
            }
        }*/

        for (int i = 0; i < width; i++) {
            for (int j = 0; j<height; j++) {
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
            Food food = new Food(random1, random2);
            foodList.add(food);
            squares[random1][random2] = SquareType.FOOD;
        }
        else {
            spawnFood();
        }
    }

    public void removeFood(int i) {
        Food food = foodList.get(i);
        squares[food.getxPos()][food.getyPos()] = SquareType.EMPTY;
        foodList.remove(food);
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

    public void increaseScore(int score) {
        currentScore += score;
    }

    public void tick() {
        if (hitsEdge()) {
            checkCollisions();
        }
        else {
            switch (currentDirection) {
                case "up":
                    moveJoints();
                    snakeHead.setyPos(snakeHead.getyPos() - 1);
                    checkCollisions();
                    break;
                case "left":
                    moveJoints();
                    snakeHead.setxPos(snakeHead.getxPos() - 1);
                    checkCollisions();
                    break;
                case "right":
                    moveJoints();
                    snakeHead.setxPos(snakeHead.getxPos() + 1);
                    checkCollisions();
                    break;
                case "down":
                    moveJoints();
                    snakeHead.setyPos(snakeHead.getyPos() + 1);
                    checkCollisions();
                    break;
                default:
                    break;
            }
        }
        notifyListeners();
    }

    public boolean hitsEdge() {
        if (snakeHead.getyPos() == 0 && currentDirection.equals("up")) {
            moveJoints();
            snakeHead.setyPos(height-1);
            return true;
        }
        else if (snakeHead.getxPos() == 0 && currentDirection.equals("left")) {
            moveJoints();
            snakeHead.setxPos(width-1);
            return true;
        }
        else if (snakeHead.getxPos() == (width - 1) && currentDirection.equals("right")) {
            moveJoints();
            snakeHead.setxPos(0);
            return true;
        }
        else if (snakeHead.getyPos() == (height - 1) && currentDirection.equals("down")) {
            moveJoints();
            snakeHead.setyPos(0);
            return true;
        }
        else {
            return false;
        }
    }

    public void checkCollisions() {
        collisionHandler.handleCollision(this);
        if (!gameOver) {
            squares[snakeHead.getxPos()][snakeHead.getyPos()] = SquareType.SNAKE;
        }
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

    public List<Food> getFoodList() {
        return foodList;
    }

    public Food getFood(int i) {
        return foodList.get(i);
    }

    public int getCurrentScore() {
        return currentScore;
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
