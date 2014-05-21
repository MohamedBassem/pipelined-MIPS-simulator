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

	public DataMemory(ExecuteMemoryRegisters executeMemoryRegisters,
			MemoryWritebackRegisters memoryWritebackRegisters) {
		this.executeMemoryRegisters = executeMemoryRegisters;
		this.memoryWritebackRegisters = memoryWritebackRegisters;
	}

	@Override
	public void execute() {
		ALUResult = executeMemoryRegisters.getALUResult();
		writeBackRegister = executeMemoryRegisters.getWriteBackRegister();
		regWrite = executeMemoryRegisters.isRegWrite();
		memToReg = executeMemoryRegisters.isMemToReg();
		
		if(executeMemoryRegisters.isMemRead()){
			memoryWord = memory.get(executeMemoryRegisters.getALUResult());
		}
		
		if(executeMemoryRegisters.isMemWrite()){
			memory.put(executeMemoryRegisters.getALUResult(),executeMemoryRegisters.getRegisterValueToMemory());
		}
		
	}

	@Override
	public void write() {
		memoryWritebackRegisters.setALUResult(ALUResult);
		memoryWritebackRegisters.setMemoryWord(memoryWord);
		memoryWritebackRegisters.setMemToReg(memToReg);
		memoryWritebackRegisters.setRegWrite(regWrite);
		memoryWritebackRegisters.setWriteBackRegister(writeBackRegister);
	}

}
