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
