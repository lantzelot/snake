import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

import static javax.swing.JOptionPane.YES_OPTION;

public class GameFrame extends JFrame
{
    private GameComponent component;

    private boolean running = true;

    private class QuitAction extends AbstractAction
    {
	public void actionPerformed(final ActionEvent e) {
	    if (JOptionPane.showConfirmDialog(null, "Are you sure you want to quit?", "Leaving alredy?", JOptionPane.YES_NO_OPTION) == YES_OPTION) {
		System.exit(0);
	    }
	}
    }

    public void gameOverScreen() {
        JOptionPane.showMessageDialog(null, "You lost!");
	if ((JOptionPane.showConfirmDialog(null, "Do you want to play again?",
					   "Again?", JOptionPane.YES_NO_OPTION)) == YES_OPTION) {
	    Game.newGame();
	}
	else {
	    System.exit(0);
	}
    }

    public GameFrame(Board board) throws HeadlessException {
	super("SNAKE");

	final JMenuBar menuBar = new JMenuBar();
	final JMenu options = new JMenu("Options");
	JMenuItem quit = new JMenuItem("Quit");
	quit.addActionListener(new QuitAction());
	options.add(quit);
	menuBar.add(options);
	setJMenuBar(menuBar);

	final Action tick = new AbstractAction() {
	    @Override public void actionPerformed(final ActionEvent e) {
		if (running) {
		    if (board.isGameOver()) {
			dispose();
			running = false;
			gameOverScreen();
		    } else {
			board.tick();
		    }
		}
	    }
	};

	final Timer clockTimer = new Timer(80, tick);
	clockTimer.setCoalesce(true);
	clockTimer.start();

	component = new GameComponent(board);
	this.setLayout(new BorderLayout());
	this.add(component, BorderLayout.CENTER);

	this.pack();
	this.setVisible(true);

    }

    public GameComponent getComponent() {
	return component;
    }
}
