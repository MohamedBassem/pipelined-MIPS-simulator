package eg.edu.guc.mimps.assembler;

public class Instruction {

	private int instuction;
	public InstructionFormat format;
	
	protected Instruction() {
		
	}
	
	public int getOpcode() {
		return 0;
	}
	
	public int getRs() {
		return 0;
	}
	
	public int getRd() {
		return 0;
	}
	
	public int getRt() {	
		return 0;
	}
	
	public int getShamt() {
		return 0;
	}
	
	public int getFunct() {
		return 0;
	}
	
	protected void setFunct(int funct) {
		
	}
	
	protected void setShamt(int shamt) {
		
	}
	
	protected void setOpcode(int opcode) {
		
	}
	
	protected void setRs(int rs) {
		
	}
	
	protected void setRd(int rd) {
		
	}
	
	protected void setRt(int rt) {
		
	}

	
	public int getConstant() {
		return 0;
	}
	
	protected void setConstant(int offset) {
		
	}
	
	public int getJumpAddress() {
		return 0;
	}
	
	protected void setJumpAddress(int jumpAddress) {
		
	}
	
}
