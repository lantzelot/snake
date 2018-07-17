public class DefaultCollisionHandler implements CollisionHandler
{
    public void handleCollision(Board board) {
        Snake head = board.getSnakeHead();
        Food food = board.getFood();
        if ((head.getxPos() == food.getxPos()) && (head.getyPos() == food.getyPos())) {
            board.removeFood();
            board.spawnFood();
            board.spawnSnakeJoint();
	}
	else if (board.getSquareType(head.getxPos() ,head.getyPos()) == SquareType.OBSTACLE ||
		 (board.getSquareType(head.getxPos(), head.getyPos())) == SquareType.SNAKE) {
	    board.setGameOver(true);
	}
    }
}
