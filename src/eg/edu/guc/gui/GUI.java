package eg.edu.guc.gui;

import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;

public class GUI {

	public static void main(String[]args) {
		JFrame mainFrame = new JFrame();
		mainFrame.setMinimumSize(Toolkit.getDefaultToolkit().getScreenSize());
		mainFrame.setMaximumSize(Toolkit.getDefaultToolkit().getScreenSize());
		mainFrame.setTitle("MIMPS Simulator");
		mainFrame.repaint();
		JMenuBar mb = new JMenuBar();
		JMenu run = new JMenu("Run");
		mb.add(run);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setVisible(true);
	}
	
}
