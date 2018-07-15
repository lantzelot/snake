public class Game
{
    // The width and height of the board in number of squares
    private static final int WIDTH = 30;
    private static final int HEIGHT = 30;

    public static void main(String[] args) {
	Board board = new Board(WIDTH, HEIGHT);
	GameFrame frame = new GameFrame(board);
	board.addBoardListener(frame.getComponent());
    }

}
