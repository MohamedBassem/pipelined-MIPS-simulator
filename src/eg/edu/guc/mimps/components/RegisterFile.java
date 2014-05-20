package eg.edu.guc.mimps.components;

import eg.edu.guc.mimps.registers.InstructionDecodeExecuteRegisters;
import eg.edu.guc.mimps.registers.InstructionFetchDecodeRegisters;
import eg.edu.guc.mimps.registers.MemoryWritebackRegisters;

public class RegisterFile implements Executable {

	InstructionFetchDecodeRegisters instructionFetchDecodeRegisters;
	InstructionDecodeExecuteRegisters instructionDecodeExecuteRegisters;
	MemoryWritebackRegisters memoryWritebackRegisters;
	
	public RegisterFile(
			InstructionFetchDecodeRegisters instructionFetchDecodeRegisters,
			InstructionDecodeExecuteRegisters instructionDecodeExecuteRegisters,
			MemoryWritebackRegisters memoryWritebackRegisters) {
		this.instructionFetchDecodeRegisters = instructionFetchDecodeRegisters.clone();
		this.instructionDecodeExecuteRegisters = instructionDecodeExecuteRegisters.clone();
		this.memoryWritebackRegisters = memoryWritebackRegisters.clone();
		// TODO Auto-generated constructor stub
	}
	
	public static void main(String[]ar) {
		
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void write() {
		// TODO Auto-generated method stub
		
	}

}