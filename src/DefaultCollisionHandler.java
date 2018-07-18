public class DefaultCollisionHandler implements CollisionHandler
{
    public void handleCollision(Board board) {
        Snake head = board.getSnakeHead();

        for (int i = 0; i < board.getFoodList().size(); i++) {
	    Food food = board.getFood(i);
	    if ((head.getxPos() == food.getxPos()) && (head.getyPos() == food.getyPos())) {
		board.removeFood(i);
		board.increaseScore(10);
		if ((board.getCurrentScore() % 100 == 0) && board.getCurrentScore() < 500) {
		    board.spawnFood();
		}
		board.spawnFood();
		board.spawnSnakeJoint();
	    }
	}
	if (board.getSquareType(head.getxPos() ,head.getyPos()) == SquareType.OBSTACLE ||
		 (board.getSquareType(head.getxPos(), head.getyPos())) == SquareType.SNAKE) {
	    board.setGameOver(true);
	}
    }
}
