package eg.edu.guc.mips.exceptions;

public class SyntaxErrorException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int line;
	
	public SyntaxErrorException(int line) {
		super("Syntax error " + line);
		this.line = line;
	}
	
	public SyntaxErrorException(String msg, int line) {
		super(msg);
		this.line = line;
	}
	
	public int getLine() {
		return this.line;
	}
	

}
