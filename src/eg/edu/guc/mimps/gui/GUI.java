package eg.edu.guc.mimps.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.Scrollbar;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;

import com.sun.org.apache.bcel.internal.generic.F2D;

import sun.font.TextLineComponent;
import sun.org.mozilla.javascript.tools.shell.JavaPolicySecurity;

public class GUI {

	public GUI() {
		startFrame();
	}
	public void startFrame(){
		JFrame mainFrame = new JFrame();
		JMenuBar menu = getMenuBar();
		mainFrame.setMinimumSize(Toolkit.getDefaultToolkit().getScreenSize());
		mainFrame.setMaximumSize(Toolkit.getDefaultToolkit().getScreenSize());
		mainFrame.setTitle("ALAA Simulator");
		mainFrame.setJMenuBar(menu);
		JScrollPane scroll = getEditor(mainFrame);
		mainFrame.setLayout(null);
		Insets insets = mainFrame.getInsets();
		Dimension size = scroll.getPreferredSize();
		scroll.setBounds(0 + insets.left, 0 + insets.top,
		             size.width, size.height);
		mainFrame.getContentPane().add(scroll);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setVisible(true);
	}
	
	private JMenuBar getMenuBar() {
		JMenuBar mb = new JMenuBar();
		JMenuItem run = new JMenuItem(new ImageIcon("play.png"));
		run.setMaximumSize(new Dimension(40,100));
		JMenuItem stop = new JMenuItem(new ImageIcon("stop.png"));
		stop.setHorizontalAlignment(SwingConstants.CENTER);
		stop.setMaximumSize(new Dimension(40,100));
		JMenuItem playStep = new JMenuItem(new ImageIcon("play_step.png"));
		playStep.setMaximumSize(new Dimension(40,100));
		mb.add(run);
		mb.add(playStep);
		mb.add(stop);
		return mb;
	}
	
	private JScrollPane getEditor (JFrame frame) {
		JTextPane editor = new JTextPane();
		TextLineNumber tln = new TextLineNumber(editor);
		JScrollPane scroll = new JScrollPane(editor);
		scroll.setRowHeaderView(tln);
		editor.setBackground(Color.white);
		scroll.setPreferredSize(new Dimension(1000,400));
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		return scroll;
	}
	public static void main(String[]args) {
		GUI gui = new GUI();
		gui.startFrame();
	}
	
}
