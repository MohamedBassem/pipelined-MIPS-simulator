package eg.edu.guc.mimps.components;

import java.util.HashMap;

import eg.edu.guc.mimps.registers.ExecuteMemoryRegisters;
import eg.edu.guc.mimps.registers.MemoryWritebackRegisters;

public class DataMemory implements Executable {

	ExecuteMemoryRegisters executeMemoryRegisters;
	MemoryWritebackRegisters memoryWritebackRegisters;
	
	HashMap<Integer,Integer> memory;
	
	// MemoryWritebackRegisters
	private int ALUResult;
	private int memoryWord;
	private int writeBackRegister;
	
	// Write Back Signals
	private boolean regWrite;
	private boolean memToReg;

	public DataMemory(ExecuteMemoryRegisters executeMemoryRegister,
			MemoryWritebackRegisters memoryWritebackRegisters) {
		this.executeMemoryRegisters = executeMemoryRegisters;
		this.memoryWritebackRegisters = memoryWritebackRegisters;
		
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
