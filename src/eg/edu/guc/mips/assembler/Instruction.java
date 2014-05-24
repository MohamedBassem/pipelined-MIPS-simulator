package eg.edu.guc.mips.assembler;

import eg.edu.guc.mips.utils.BinaryManiplator;

public class Instruction {

	private int instruction = 0;
	
	
	protected Instruction() {
		
	}
	
	public Instruction(int instruction) {
		this.instruction = instruction;
	}
	
	public int getOpcode() {
		return BinaryManiplator.getUnsignedPartialValue(instruction, 26, 31);
	}
	
	public int getRs() {
		return BinaryManiplator.getUnsignedPartialValue(instruction, 21, 25);
	}
	
	public int getRd() {
		return BinaryManiplator.getUnsignedPartialValue(instruction, 11, 15);
	}
	
	public int getRt() {	
		return BinaryManiplator.getUnsignedPartialValue(instruction, 16, 20);
	}
	
	public int getShamt() {
		return BinaryManiplator.getUnsignedPartialValue(instruction, 6, 10);
	}
	
	public int getFunct() {
		return BinaryManiplator.getUnsignedPartialValue(instruction, 0, 5);
	}
	
	protected void setFunct(int funct) {
		instruction = BinaryManiplator.setUnsignedPartialValue(instruction, 0, 5, funct);
	}
	
	protected void setShamt(int shamt) {
		instruction = BinaryManiplator.setUnsignedPartialValue(instruction, 6, 10, shamt);
	}
	
	protected void setOpcode(int opcode) {
		instruction = BinaryManiplator.setUnsignedPartialValue(instruction, 26, 31, opcode);
	}
	
	protected void setRs(int rs) {
		instruction = BinaryManiplator.setUnsignedPartialValue(instruction, 21, 25,rs);
	}
	
	protected void setRd(int rd) {
		instruction = BinaryManiplator.setUnsignedPartialValue(instruction, 11, 15, rd);
	}
	
	protected void setRt(int rt) {
		instruction = BinaryManiplator.setUnsignedPartialValue(instruction, 16, 20, rt);
	}

	
	public int getConstant() {
		return BinaryManiplator.getPartialValue(instruction, 0, 15);
	}
	
	protected void setConstant(int value) {
		instruction = BinaryManiplator.setPartialValue(instruction, 0, 15, value);
	}
	
	public int getJumpAddress() {
		return BinaryManiplator.getUnsignedPartialValue(instruction, 0, 25);
	}
	
	protected void setJumpAddress(int jumpAddress) {
		instruction = BinaryManiplator.setUnsignedPartialValue(instruction, 0, 25, jumpAddress);
		
	}
	
	public int toInt() {
		return instruction;
	}
	
	public String toString(){
		String ret = "";
		for(int i=31;i>=0;i--){
			ret += (instruction & (1<<i)) != 0 ? '1':'0';
		}
		return ret;
	}
}
