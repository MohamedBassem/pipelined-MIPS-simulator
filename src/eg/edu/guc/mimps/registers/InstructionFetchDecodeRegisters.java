package eg.edu.guc.mimps.registers;

import eg.edu.guc.mimps.assembler.Instruction;


public class InstructionFetchDecodeRegisters {
	private Instruction instruction;
	private int incrementedPc;
	
	
	public Instruction getInstruction() {
		return instruction;
	}
	public void setInstruction(Instruction instruction) {
		this.instruction = instruction;
	}
	public int getIncrementedPc() {
		return incrementedPc;
	}
	public void setIncrementedPc(int incrementedPc) {
		this.incrementedPc = incrementedPc;
	}
	
	public InstructionFetchDecodeRegisters clone(){
		InstructionFetchDecodeRegisters clone = new InstructionFetchDecodeRegisters();
		clone.setIncrementedPc(incrementedPc);
		clone.setInstruction(instruction);
		return clone;
	}
	
	public void replace(InstructionFetchDecodeRegisters instructionFetchDecodeRegisters){
		this.setIncrementedPc(instructionFetchDecodeRegisters.getIncrementedPc());
		this.setInstruction(instructionFetchDecodeRegisters.getInstruction());
	}
}
