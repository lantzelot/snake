import javafx.scene.paint.*;

import javax.swing.*;
import java.awt.*;
import java.awt.Color;

public class GameComponent extends JComponent implements BoardListener
{

    private Board board;

    public GameComponent(final Board board) {
	this.board = board;

	final InputMap in = getInputMap();
	in.put(KeyStroke.getKeyStroke("UP"), "move_up");
	in.put(KeyStroke.getKeyStroke("LEFT"), "move_left");
	in.put(KeyStroke.getKeyStroke("RIGHT"), "move_right");
	in.put(KeyStroke.getKeyStroke("DOWN"), "move_down");

	final ActionMap act = getActionMap();
	act.put("move_up", new MoveUpAction());
	act.put("move_left", new MoveLeftAction());
	act.put("move_right", new MoveRightAction());
	act.put("move_down", new MoveDownAction());
    }

    private class MoveUpAction extends AbstractAction {
    	@Override public void actionPerformed(final java.awt.event.ActionEvent e) {
    	    board.moveUp();
    	}
    }

    private class MoveLeftAction extends AbstractAction {
        @Override public void actionPerformed(final java.awt.event.ActionEvent e) {
	    board.moveLeft();
	}
    }

    private class MoveRightAction extends AbstractAction {
    	@Override public void actionPerformed(final java.awt.event.ActionEvent e) {
	    board.moveRight();
    	}
    }

    private class MoveDownAction extends AbstractAction {
	@Override public void actionPerformed(final java.awt.event.ActionEvent e) {
	    board.moveDown();
	}
    }

    @Override public Dimension getPreferredSize() {
    	return new Dimension(board.getWidth()*board.getSquareWidth(),
    			     board.getHeight()*board.getSquareHeight());
        }

    @Override protected void paintComponent(final Graphics g) {
	super.paintComponent(g);
	final Graphics2D g2d = (Graphics2D) g;

	for (int w = 0; w < board.getWidth(); w++) {
	    for (int h = 0; h < board.getHeight(); h++) {
	        SquareType type = board.getSquareType(w, h);

	        switch (type) {
		    case EMPTY:
		        g2d.setColor(Color.BLACK);
		        g2d.fillRect(w*board.getSquareWidth(), h*board.getSquareHeight(),
				     board.getSquareWidth(), board.getSquareHeight());
		    //    g2d.setColor(Color.WHITE);
		//	g2d.drawRect(w*board.getSquareWidth(), h*board.getSquareHeight(),
		//		     board.getSquareWidth(), board.getSquareHeight());
			break;
		    case FOOD:
			g2d.setColor(Color.YELLOW);
			g2d.fillRect(w*board.getSquareWidth(), h*board.getSquareHeight(),
				     board.getSquareWidth(), board.getSquareHeight());
		//	g2d.setColor(Color.WHITE);
		//	g2d.drawRect(w*board.getSquareWidth(), h*board.getSquareHeight(),
		//		     board.getSquareWidth(), board.getSquareHeight());
			break;
		    case SNAKE:
			g2d.setColor(Color.RED);
			g2d.fillRect(w*board.getSquareWidth(), h*board.getSquareHeight(),
				     board.getSquareWidth(), board.getSquareHeight());
		//	g2d.setColor(Color.WHITE);
		//	g2d.drawRect(w*board.getSquareWidth(), h*board.getSquareHeight(),
		//		     board.getSquareWidth(), board.getSquareHeight());
			break;
		    case OBSTACLE:
			g2d.setColor(Color.GRAY);
			g2d.fillRect(w*board.getSquareWidth(), h*board.getSquareHeight(),
				     board.getSquareWidth(), board.getSquareHeight());
		//	g2d.setColor(Color.WHITE);
		//	g2d.drawRect(w*board.getSquareWidth(), h*board.getSquareHeight(),
		//		     board.getSquareWidth(), board.getSquareHeight());
		        break;
		    default:
			throw new IllegalArgumentException("Wrong");
		}
	    }
	}

    }

    @Override public void boardChanged() {
	repaint();
    }


}
