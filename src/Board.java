import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Board
{

    private SquareType[][] squares;
    private int width;
    private int height;

    private Food food = null;
    private Snake snake = null;

    private List<BoardListener> listeners = new ArrayList<>();

    private static final int SQUAREWIDTH = 20;
    private static final int SQUAREHEIGHT = 20;

    private int squareWidth;
    private int squareHeight;


    public Board(final int width, final int height) {
        this.width = width;
        this.height = height;
        this.squareWidth = SQUAREWIDTH;
        this.squareHeight = SQUAREHEIGHT;
        squares = new SquareType[width+4][height+4];

        for (int i = 0; i < width; i++) {
            for (int j =0; j<height; j++) {
                squares[i][j] = SquareType.EMPTY;
            }
        }
        spawnFood();
        spawnSnake();

    }

    public void spawnFood() {
        int random1 = ThreadLocalRandom.current().nextInt(0, this.width);
        int random2 = ThreadLocalRandom.current().nextInt(0, this.height);

        food = new Food(random1, random2);
        squares[random1][random2] = SquareType.FOOD;
    }

    public void spawnSnake() {
        int random1 = ThreadLocalRandom.current().nextInt(0, this.width);
        int random2 = ThreadLocalRandom.current().nextInt(0, this.height);

        if (squares[random1][random2] == SquareType.EMPTY) {
            snake = new Snake(random1, random2);
            squares[random1][random2] = SquareType.SNAKE;
        }
        else {
            spawnSnake();
        }
    }

    public void moveUp() {
       squares[snake.getxPos()][snake.getyPos()-1] = SquareType.SNAKE;
       squares[snake.getxPos()][snake.getyPos()] = SquareType.EMPTY;
       snake.setyPos(snake.getyPos()-1);
       notifyListeners();
    }

    public void moveLeft() {
        squares[snake.getxPos()-1][snake.getyPos()] = SquareType.SNAKE;
        squares[snake.getxPos()][snake.getyPos()] = SquareType.EMPTY;
        snake.setxPos(snake.getxPos()-1);
        notifyListeners();
    }

    public void moveRight() {
        squares[snake.getxPos()+1][snake.getyPos()] = SquareType.SNAKE;
        squares[snake.getxPos()][snake.getyPos()] = SquareType.EMPTY;
        snake.setxPos(snake.getxPos()+1);
        notifyListeners();
    }

    public void moveDown() {
        squares[snake.getxPos()][snake.getyPos()+1] = SquareType.SNAKE;
        squares[snake.getxPos()][snake.getyPos()] = SquareType.EMPTY;
        snake.setyPos(snake.getyPos()+1);
        notifyListeners();
    }

    public void addBoardListener(BoardListener bl) {
            listeners.add(bl);
        }

    private void notifyListeners() {
    	for (BoardListener listener : listeners) {
    	    listener.boardChanged();
    	}
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

    public Snake getSnake() {
        return snake;
    }

    public int getSquareWidth() {
        return squareWidth;
    }

    public int getSquareHeight() {
        return squareHeight;
    }
}
