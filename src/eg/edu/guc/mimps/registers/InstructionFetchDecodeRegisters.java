package eg.edu.guc.mimps.registers;

public class InstructionFetchDecodeRegisters {
	private int instruction;
	private int incrementedPc;
	
	
	public int getInstruction() {
		return instruction;
	}
	public void setInstruction(int instruction) {
		this.instruction = instruction;
	}
	public int getIncrementedPc() {
		return incrementedPc;
	}
	public void setIncrementedPc(int incrementedPc) {
		this.incrementedPc = incrementedPc;
	}
}
