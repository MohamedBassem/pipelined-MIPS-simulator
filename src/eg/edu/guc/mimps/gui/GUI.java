package eg.edu.guc.mimps.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextPane;
import javax.swing.filechooser.FileFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;

import eg.edu.guc.mimps.exceptions.SyntaxErrorException;
import eg.edu.guc.mimps.registers.Memory;
import eg.edu.guc.mimps.registers.Registers;
import eg.edu.guc.mimps.simulator.Simulator;

public class GUI {
	Simulator simulator;
	JFrame mainFrame;
	JTable registersTable;
	JTable memoryTable;
	JTextPane editor = new JTextPane();
	JMenuBar mb;
	String code = "";

	public GUI(Simulator simulator, Memory memory, Registers registers) {
		this.simulator = simulator;
		startFrame();
		update();
	}

	public void startFrame() {
		mainFrame = new JFrame();
		JMenuBar menu = getMenuBar();
		mainFrame.setMinimumSize(new Dimension((int) Toolkit
				.getDefaultToolkit().getScreenSize().getWidth()/2,
				(int) Toolkit.getDefaultToolkit().getScreenSize().height/2));
		mainFrame.setMaximumSize(Toolkit.getDefaultToolkit().getScreenSize());
		mainFrame.setTitle("MIPS Simulator");
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
		editor.setDocument(new AssemblyDocument());
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
		mb = new JMenuBar();
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
		final JMenuItem assemble = new JMenuItem(new ImageIcon("assemble.png"));
		assemble.setDisabledIcon(new ImageIcon("assemble_disabled.png"));
		assemble.setMaximumSize(new Dimension(70, 100));
		run.setEnabled(false);
		runStep.setEnabled(false);
		
		if(code == "")
			assemble.setEnabled(false);
		
		editor.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent arg0) {
				run.setEnabled(false);
				runStep.setEnabled(false);
				assemble.setEnabled(true);
				editor.getHighlighter().removeAllHighlights();
				simulator.reset();
				update();
			}
			
			@Override
			public void keyReleased(KeyEvent arg0) {
			}
			
			@Override
			public void keyPressed(KeyEvent arg0) {
			}
		});
		
		assemble.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					code = editor.getText();
					System.out.println(code);
					simulator.assemble(0, code);
					assemble.setEnabled(false);
					
					run.setEnabled(true);
					runStep.setEnabled(true);

				} catch (SyntaxErrorException e1) {
					try {
						editor.getHighlighter().addHighlight(
								editor.getDocument().getDefaultRootElement()
										.getElement(e1.getLine() - 1)
										.getStartOffset(),
								editor.getDocument().getDefaultRootElement().getElement(e1.getLine() - 1).getEndOffset(),
								new DefaultHighlighter.DefaultHighlightPainter(
										Color.red));

					} catch (BadLocationException e2) {
						e2.printStackTrace();
					}

				} 
			}
		}); 

		run.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				simulator.run();
				run.setEnabled(false);
				runStep.setEnabled(false);
			}
		});

		runStep.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (!simulator.step()) {
					runStep.setEnabled(false);
					run.setEnabled(true);
				}
			}
		});

		open.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				final JFileChooser fc = new JFileChooser();
				fc.setFileFilter(new FileFilter() {
					
					@Override
					public String getDescription() {
						return null;
					}
					
					@Override
					public boolean accept(File f) {
						return f.isDirectory() || f.getName().toLowerCase().endsWith(".txt");
					}
				});

				// in response to a button click:
				int returnVal = fc.showOpenDialog(mainFrame);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fc.getSelectedFile();
					BufferedReader reader;
					try {
						reader = new BufferedReader(new FileReader(file));
						StringBuffer buffer = new StringBuffer();
						String line = reader.readLine();
						while (line != null) {
							buffer.append(line + '\n');
							line = reader.readLine();
						}
						editor.setText(buffer.toString());
					} catch (FileNotFoundException e1) {
						e1.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					} 
					
				}
			}
		});
		mb.add(open);
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
		DefaultTableModel model = new DefaultTableModel(10, 9);
		memoryTable = new JTable(model);
		memoryTable.getColumn(memoryTable.getColumnName(0)).setHeaderValue(
				"Address");
		for (int i = 1; i < 9; i++) {
			memoryTable.getColumn(memoryTable.getColumnName(i)).setHeaderValue(
					"Value (+" + (i-1)*4 + ")");
		}
		memoryTable.setEnabled(false);
		JScrollPane scroll = new JScrollPane(memoryTable);
		scroll.setMinimumSize(new Dimension(700, 200));
		return scroll;
	}

	public void update() {
		for (int i = 0; i < 32; i++) {
			registersTable.setValueAt(simulator.getRegisters().getReg(i), i, 2);
		}
		registersTable.setValueAt(simulator.getPc(), 32, 2);
	}

}
