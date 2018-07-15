import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

import static javax.swing.JOptionPane.YES_OPTION;

public class GameFrame extends JFrame
{
    private GameComponent component;

    private class QuitAction extends AbstractAction
        {
    	public void actionPerformed(final ActionEvent e) {
    	    if (JOptionPane.showConfirmDialog(null,
    					      "Are you sure you want to quit?",
    					  "Leaving alredy?", JOptionPane.YES_NO_OPTION) == YES_OPTION) {
    	        System.exit(0);
    	    }
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

	component = new GameComponent(board);
	this.setLayout(new BorderLayout());
	this.add(component, BorderLayout.CENTER);

	//this.setSize(800, 800);

	this.pack();
	this.setVisible(true);

    }

    public GameComponent getComponent() {
	return component;
    }
}
