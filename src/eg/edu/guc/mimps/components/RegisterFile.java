package eg.edu.guc.mimps.components;

import eg.edu.guc.mimps.assembler.Instruction;
import eg.edu.guc.mimps.registers.InstructionDecodeExecuteRegisters;
import eg.edu.guc.mimps.registers.InstructionFetchDecodeRegisters;
import eg.edu.guc.mimps.registers.MemoryWritebackRegisters;
import eg.edu.guc.mimps.registers.Registers;

public class RegisterFile implements Executable {

	InstructionFetchDecodeRegisters instructionFetchDecodeRegisters;
	InstructionDecodeExecuteRegisters instructionDecodeExecuteRegisters;
	InstructionDecodeExecuteRegisters newIDER;
	MemoryWritebackRegisters memoryWritebackRegisters;
	Registers registers;

	public RegisterFile(
			InstructionFetchDecodeRegisters instructionFetchDecodeRegisters,
			InstructionDecodeExecuteRegisters instructionDecodeExecuteRegisters,
			MemoryWritebackRegisters memoryWritebackRegisters,
			Registers registers) {
		this.instructionFetchDecodeRegisters = instructionFetchDecodeRegisters;
		this.memoryWritebackRegisters = memoryWritebackRegisters;
		this.instructionDecodeExecuteRegisters = instructionDecodeExecuteRegisters;
		this.registers = registers; 
	}
	
	private static int signExtend(int val) {
		int mostSigBit = val & (1 << 15);
		if(mostSigBit != 0)
			for(int i=16;i<=31;i++)
				val |= (1 << i);
		return val;
	}
	
	@Override
	public void execute() {
		int instructionNumber = instructionFetchDecodeRegisters.getInstruction();
		Instruction instruction = new Instruction(instructionNumber);
		newIDER = instructionDecodeExecuteRegisters.clone();
		
		int rs = instruction.getRs();
		int rt = instruction.getRt();

		if(instructionDecodeExecuteRegisters.isRegWrite()) {
			int data = memoryWritebackRegisters.getALUResult();
			int writeTo = memoryWritebackRegisters.getWriteBackRegister();
			if(instructionDecodeExecuteRegisters.isMemToReg()) {
				data = memoryWritebackRegisters.getMemoryWord();
				registers.setReg(writeTo, data);
			}else {
				registers.setReg(writeTo, data);
			}
		}else {
			newIDER.setRegister1Value(registers.getReg(rs));
			newIDER.setRegister2Value(registers.getReg(rt));
		}
		
		int constant = instruction.getConstant();
		newIDER.setSignExtendedOffset(signExtend(constant));
	}

	@Override
	public void write() {
		instructionDecodeExecuteRegisters.replaceRegister(newIDER);
	}

}