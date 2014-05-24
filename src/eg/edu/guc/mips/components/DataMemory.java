package eg.edu.guc.mips.components;

import java.util.HashMap;

import eg.edu.guc.mips.registers.ExecuteMemoryRegisters;
import eg.edu.guc.mips.registers.Memory;
import eg.edu.guc.mips.registers.MemoryWritebackRegisters;

public class DataMemory implements Executable {

	ExecuteMemoryRegisters executeMemoryRegisters;
	MemoryWritebackRegisters memoryWritebackRegisters;
	
	Memory memory;
	
	MemoryWritebackRegisters newMemoryWritebackRegisters;

	public DataMemory(ExecuteMemoryRegisters executeMemoryRegisters,
			MemoryWritebackRegisters memoryWritebackRegisters,
			Memory memory) {
		this.executeMemoryRegisters = executeMemoryRegisters;
		this.memoryWritebackRegisters = memoryWritebackRegisters;
		this.memory = memory;
	}

	@Override
	public void execute() {
		newMemoryWritebackRegisters = memoryWritebackRegisters.clone(); 
		newMemoryWritebackRegisters.setALUResult(executeMemoryRegisters.getALUResult());
		newMemoryWritebackRegisters.setWriteBackRegister(executeMemoryRegisters.getWriteBackRegister());
		newMemoryWritebackRegisters.setRegWrite(executeMemoryRegisters.isRegWrite());
		newMemoryWritebackRegisters.setMemToReg(executeMemoryRegisters.isMemToReg());
		
		if(executeMemoryRegisters.isMemRead()){
			newMemoryWritebackRegisters.setMemoryWord(memory.get(executeMemoryRegisters.getALUResult()));
		}
		
		if(executeMemoryRegisters.isMemWrite()){
			memory.put(executeMemoryRegisters.getALUResult(),executeMemoryRegisters.getRegisterValueToMemory());
		}
		
	}

	@Override
	public void write() {
		memoryWritebackRegisters.replace(newMemoryWritebackRegisters);
	}

}
