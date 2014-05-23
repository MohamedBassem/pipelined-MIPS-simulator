package eg.edu.guc.mimps.gui;

import java.awt.Color;
import java.util.Hashtable;

import javax.swing.event.DocumentEvent;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultEditorKit;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Element;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

/**
 * This class is an edited version of the code at 
 * http://www.linuxquestions.org/questions/programming-9/java-jtextarea-and-syntax-highlighting-417575/
 * @author Almgohar
 */
public class AssemblyDocument extends DefaultStyledDocument {

	private MutableAttributeSet normal;
	private MutableAttributeSet registerKeyword;
	private MutableAttributeSet instructionKeyword;
	private MutableAttributeSet comment;
	private Hashtable registersKeywords;
	private Hashtable instructionsKeywords;
	private DefaultStyledDocument doc;
	private Element rootElement;

	public AssemblyDocument() {
		doc = this;
		rootElement = doc.getDefaultRootElement();
		putProperty(DefaultEditorKit.EndOfLineStringProperty, "\n");

		normal = new SimpleAttributeSet();
		StyleConstants.setForeground(normal, Color.black);

		comment = new SimpleAttributeSet();
		Color green = new Color(0, 120, 0);
		StyleConstants.setForeground(comment, green);
		StyleConstants.setItalic(comment, true);

		registerKeyword = new SimpleAttributeSet();
		Color blue = new Color(0, 0, 255);
		StyleConstants.setForeground(registerKeyword, blue);
		StyleConstants.setBold(registerKeyword, true);

		instructionKeyword = new SimpleAttributeSet();
		Color red = new Color(255, 0, 0);
		StyleConstants.setForeground(instructionKeyword, red);
		StyleConstants.setBold(instructionKeyword, true);

		String[] registers = { "$0", "$zero", "$at", "$v0", "$v1", "$a0",
				"$a1", "$a2", "$a3", "$t0", "$t1", "$t2", "$t3", "$t4", "$t5",
				"$t6", "$t7", "$s0", "$s1", "$s2", "$s3", "$s4", "$s5", "$s6",
				"$s7", "$t8", "$t9", "$k0", "$k1", "$gp", "$sp", "$fp", "$ra" };
		registersKeywords = new Hashtable();
		for (int i = 0; i < registers.length; i++) {
			registersKeywords.put(registers[i], new Object());
		}

		String[] instructions = { "add", "addi", "sub", "lw", "sw", "sll",
				"srl", "and", "andi", "or", "ori", "nor", "beq", "bne", "j",
				"jal", "jr", "slt", "sltu" };

		instructionsKeywords = new Hashtable();
		for (int i = 0; i < instructions.length; i++) {
			instructionsKeywords.put(instructions[i], new Object());
		}
	}

	public void insertString(int offset, String str, AttributeSet a)
			throws BadLocationException {
		super.insertString(offset, str, a);
		processChangedLines(offset, str.length());
	}

	public void remove(int offset, int length) throws BadLocationException {
		super.remove(offset, length);
		processChangedLines(offset, 0);
	}

	private void processChangedLines(int offset, int length)
			throws BadLocationException {
		String content = doc.getText(0, doc.getLength());
		int startLine = rootElement.getElementIndex(offset);
		int endLine = rootElement.getElementIndex(offset + length);

		for (int i = startLine; i <= endLine; i++)
			applyHighlighting(content, i);
	}

	private void applyHighlighting(String content, int line)
			throws BadLocationException {
		int startOffset = rootElement.getElement(line).getStartOffset();
		int endOffset = rootElement.getElement(line).getEndOffset() - 1;
		int lineLength = endOffset - startOffset;
		int contentLength = content.length();
		if (endOffset >= contentLength)
			endOffset = contentLength - 1;
		doc.setCharacterAttributes(startOffset, lineLength, normal, true);
		int index = content.indexOf(getSingleLineDelimiter(), startOffset);
		if ((index > -1) && (index < endOffset)) {
			doc.setCharacterAttributes(index, endOffset - index + 1, comment,
					false);
			endOffset = index - 1;
		}
		checkForTokens(content, startOffset, endOffset);
	}

	private void checkForTokens(String content, int startOffset, int endOffset) {
		while (startOffset <= endOffset) {
			while (Character.isWhitespace(content.substring(startOffset,
					startOffset + 1).charAt(0))) {
				if (startOffset < endOffset)
					startOffset++;
				else
					return;
			}
			startOffset = getOtherToken(content, startOffset, endOffset);
		}
	}

	private int getOtherToken(String content, int startOffset, int endOffset) {
		int endOfToken = startOffset + 1;
		while (endOfToken <= endOffset) {
			if (Character.isWhitespace(content.substring(endOfToken,
					endOfToken + 1).charAt(0)))
				break;
			endOfToken++;
		}
		String token = content.substring(startOffset, endOfToken);

		if (isRegisterKeyword(token))
			doc.setCharacterAttributes(startOffset, endOfToken - startOffset,
					registerKeyword, false);
		if (isInstructionKeyword(token))
			doc.setCharacterAttributes(startOffset, endOfToken - startOffset,
					instructionKeyword, false);

		return endOfToken + 1;
	}

	protected void fireInsertUpdate(DocumentEvent evt) {

		super.fireInsertUpdate(evt);

		try {
			processChangedLines(evt.getOffset(), evt.getLength());
		} catch (BadLocationException ex) {
			System.out.println("" + ex);
		}
	}

	protected void fireRemoveUpdate(DocumentEvent evt) {

		super.fireRemoveUpdate(evt);

		try {
			processChangedLines(evt.getOffset(), evt.getLength());
		} catch (BadLocationException ex) {
			System.out.println("" + ex);
		}
	}

	protected boolean isRegisterKeyword(String token) {
		if (token.endsWith(",")) {
			Object o = registersKeywords.get(token.substring(0,
					token.length()).toLowerCase());
			return o == null ? false : true;
		} else {
			Object o = registersKeywords.get(token.toLowerCase());
			return o == null ? false : true;
		}
	}

	protected boolean isInstructionKeyword(String token) {
		Object o = instructionsKeywords.get(token.toLowerCase());
		return o == null ? false : true;
	}

	protected String getSingleLineDelimiter() {
		return "#";
	}
}