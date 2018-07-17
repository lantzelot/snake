public class Game
{
    // The width and height of the board in number of squares
    private static final int WIDTH = 25;
    private static final int HEIGHT = 25;

    public static void newGame() {
	Board board = new Board(WIDTH, HEIGHT);
	GameFrame frame = new GameFrame(board);
	board.addBoardListener(frame.getComponent());
    }

    public static void main(String[] args) {
	newGame();
    }

}
