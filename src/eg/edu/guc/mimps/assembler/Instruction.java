package eg.edu.guc.mimps.assembler;

import eg.edu.guc.mimps.utils.BinaryManiplator;

public class Instruction {

	private int instruction = 0;
	
	
	protected Instruction() {
		
	}
	
	public int getOpcode() {
		return BinaryManiplator.getPartialValue(instruction, 26, 31);
	}
	
	public int getRs() {
		return BinaryManiplator.getPartialValue(instruction, 21, 25);
	}
	
	public int getRd() {
		return BinaryManiplator.getPartialValue(instruction, 11, 15);
	}
	
	public int getRt() {	
		return BinaryManiplator.getPartialValue(instruction, 16, 20);
	}
	
	public int getShamt() {
		return BinaryManiplator.getPartialValue(instruction, 6, 10);
	}
	
	public int getFunct() {
		return BinaryManiplator.getPartialValue(instruction, 0, 5);
	}
	
	protected void setFunct(int funct) {
		instruction = BinaryManiplator.setPartialValue(instruction, 0, 5, funct);
	}
	
	protected void setShamt(int shamt) {
		instruction = BinaryManiplator.setPartialValue(instruction, 6, 10, shamt);
	}
	
	protected void setOpcode(int opcode) {
		instruction = BinaryManiplator.setPartialValue(instruction, 26, 31, opcode);
	}
	
	protected void setRs(int rs) {
		instruction = BinaryManiplator.getPartialValue(instruction, 21, 25);
	}
	
	protected void setRd(int rd) {
		instruction = BinaryManiplator.setPartialValue(instruction, 11, 15, rd);
	}
	
	protected void setRt(int rt) {
		instruction = BinaryManiplator.setPartialValue(instruction, 16, 20, rt);
	}

	
	public int getConstant() {
		return 0;
	}
	
	protected void setConstant(int offset) {
		
	}
	
	public int getJumpAddress() {
		return BinaryManiplator.getPartialValue(instruction, 0, 25);
	}
	
	protected void setJumpAddress(int jumpAddress) {
		BinaryManiplator.setPartialValue(instruction, 0, 25, jumpAddress);
		
	}
	
	public int toInt() {
		return instruction;
	}
	
}
