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
}
