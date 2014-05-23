package eg.edu.guc.mimps.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.beans.PropertyChangeListener;

import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextPane;
import javax.swing.table.DefaultTableModel;
import eg.edu.guc.mimps.registers.Memory;
import eg.edu.guc.mimps.registers.Registers;
import eg.edu.guc.mimps.simulator.Simulator;

public class GUI {
	Simulator simulator;
	Memory memory;
	Registers registers;
	JFrame mainFrame;
	JTable registersTable;
	JTable memoryTable;
	JTextPane editor;

	public GUI(Simulator simulator, Memory memory, Registers registers) {
		this.simulator = simulator;
		this.memory = memory;
		this.registers = registers;		
	}

	public void startFrame() {
		mainFrame = new JFrame();
		JMenuBar menu = getMenuBar();
		mainFrame.setMinimumSize(new Dimension((int) Toolkit
				.getDefaultToolkit().getScreenSize().getWidth()-70,
				(int) Toolkit.getDefaultToolkit().getScreenSize().height));
		mainFrame.setMaximumSize(Toolkit.getDefaultToolkit().getScreenSize());
		mainFrame.setTitle("MIMPS Simulator");
		mainFrame.setJMenuBar(menu);
		JSplitPane leftPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
				getEditor(), getMemoryTable());
		leftPane.setDividerLocation(490);
		leftPane.setPreferredSize(new Dimension(1000, (int) Toolkit
				.getDefaultToolkit().getScreenSize().getHeight()));
		JSplitPane contentPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
				leftPane, getRegistersTable());
		contentPane.setDividerLocation(950);
		mainFrame.setContentPane(contentPane);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setVisible(true);
	}

	private JScrollPane getEditor() {
		editor = new JTextPane();
		editor.addKeyListener
		(
		new KeyListener()
		{
		@Override
		public void keyPressed(KeyEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		@Override
		public void keyReleased(KeyEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		@Override
		public void keyTyped(KeyEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		}
		);
		TextLineNumber tln = new TextLineNumber(editor);
		JScrollPane scroll = new JScrollPane(editor);
		scroll.setRowHeaderView(tln);
		editor.setBackground(Color.white);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scroll.setMinimumSize(new Dimension(700, 400));
		return scroll;
	}
	
	private JMenuBar getMenuBar() {
		JMenuBar mb = new JMenuBar();
		final JMenuItem run = new JMenuItem(new ImageIcon("run.png"));
		run.setMaximumSize(new Dimension(70, 100));
		run.setDisabledIcon(new ImageIcon("run_disabled.png"));
		final JMenuItem runStep = new JMenuItem(new ImageIcon("run_step.png"));
		runStep.setDisabledIcon(new ImageIcon("run_step_disabled.png"));
		runStep.setMaximumSize(new Dimension(70, 100));
		JMenuItem save = new JMenuItem(new ImageIcon("save.png"));
		save.setMaximumSize(new Dimension(70, 100));
		JMenuItem open = new JMenuItem(new ImageIcon("open.png"));
		open.setMaximumSize(new Dimension(70, 100));
		JMenuItem assemble = new JMenuItem(new ImageIcon("assemble.png"));
		assemble.setDisabledIcon(new ImageIcon("assemble_disabled.png"));
		assemble.setMaximumSize(new Dimension(70, 100));
		run.setEnabled(false);
		runStep.setEnabled(false);
		
		assemble.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(simulator.assemble(0,editor.getText())) {
					run.setEnabled(true);
					runStep.setEnabled(true);
				} else {
					
				}
				
			}
		});
		
		run.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				simulator.run(0, editor.getText());
				run.setEnabled(false);
				runStep.setEnabled(false);
			}
		});
		
		runStep.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!simulator.step()) {
					runStep.setEnabled(false);
					run.setEnabled(true);
				}
			}
		});
		mb.add(open);
		mb.add(save);
		mb.add(assemble);
		mb.add(run);
		mb.add(runStep);
		return mb;
	}
	
	private JScrollPane getRegistersTable() {
		String[] names = { "Register", "Number", "Value" };
		String[] registers = { "$zero", "$at", "$v0", "$v1", "$a0", "$a1",
				"$a2", "$a3", "$t0", "$t1", "$t2", "$t3", "$t4", "$t5", "$t6",
				"$t7", "$s0", "$s1", "$s2", "$s3", "$s4", "$s5", "$s6", "$s7",
				"$t8", "$t9", "$k0", "$k1", "$gp", "$sp", "$fp", "$ra" };

		DefaultTableModel model = new DefaultTableModel(names, 35);
		registersTable = new JTable();
		registersTable.setModel(model);
		for (int i = 0; i < registers.length; i++) {
			registersTable.setValueAt(registers[i], i, 0);
			registersTable.setValueAt(i, i, 1);
		}
		registersTable.setValueAt("pc", 32, 0);
		registersTable.setValueAt("hi", 33, 0);
		registersTable.setValueAt("lo", 34, 0);	
		registersTable.setEnabled(false);
		JScrollPane scroll = new JScrollPane(registersTable);
		scroll.setMinimumSize(new Dimension(400, Toolkit.getDefaultToolkit()
				.getScreenSize().height));
		return scroll;
	}

	private JScrollPane getMemoryTable() {
		DefaultTableModel model = new DefaultTableModel(10, 10);
		memoryTable = new JTable(model);
		memoryTable.getColumn(memoryTable.getColumnName(0)).setHeaderValue(
				"Address");
		for (int i = 1; i < 10; i++) {
			memoryTable.getColumn(memoryTable.getColumnName(i)).setHeaderValue(
					"Value" + i);
		}
		memoryTable.setEnabled(false);
		JScrollPane scroll = new JScrollPane(memoryTable);
		scroll.setMinimumSize(new Dimension(700, 200));
		return scroll;
	}

	public void update() {
		for (int i = 0; i < 32; i++) {
			registersTable.setValueAt(registers.getReg(i), i, 2);
		}
		registersTable.setValueAt(simulator.getPc(), 32, 2);
	}
	
	public static void main(String[]args) {
		Registers registers = new Registers();
		Memory memory = new Memory();
		Simulator simulator = new Simulator();
		
		GUI gui = new GUI(simulator, memory, registers);
		gui.startFrame();
		gui.update();

	}

}
