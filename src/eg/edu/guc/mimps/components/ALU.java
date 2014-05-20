package eg.edu.guc.mimps.components;

import eg.edu.guc.mimps.assembler.Assembler;
import eg.edu.guc.mimps.assembler.Instruction;
import eg.edu.guc.mimps.registers.ExecuteMemoryRegisters;
import eg.edu.guc.mimps.registers.InstructionDecodeExecuteRegisters;
import eg.edu.guc.mimps.utils.BinaryManiplator;

public class ALU implements Executable {
	
	private int branchAddressALU;
	private boolean zeroALU;
	private int ALUResultALU;
	private int registerValueToMemoryALU;
	private int writeBackRegisterALU;

	// Memory Phase Signals
	private boolean memReadALU;
	private boolean memWriteALU;
	private boolean branchALU;

	// Write Back Signals
	private boolean regWriteALU;
	private boolean memToRegALU;


	InstructionDecodeExecuteRegisters prevRegister;
	ExecuteMemoryRegisters nextRegister;
	
	public ALU(
			InstructionDecodeExecuteRegisters instructionDecodeExecuteRegisters,
			ExecuteMemoryRegisters executeMemoryRegisters) {
			prevRegister = instructionDecodeExecuteRegisters;
			nextRegister = executeMemoryRegisters;
	}


	public void execute() {
		int incrementedPc = prevRegister.getIncrementedPc();
		int register1Value = prevRegister.getRegister1Value();
		int register2Value = prevRegister.getRegister2Value();
		int signExtendedOffset = prevRegister.getSignExtendedOffset();
		int rt = prevRegister.getRt();
		int rd = prevRegister.getRd();

		boolean aluSrc = prevRegister.isAluSrc();
		boolean regDest = prevRegister.isRegDest();
		int aluOpt = prevRegister.getAluOpt();

		boolean memRead = prevRegister.isMemRead();
		boolean memWrite = prevRegister.isMemWrite();
		boolean branch = prevRegister.isBranch();

		boolean regWrite = prevRegister.isRegWrite();
		boolean memToReg = prevRegister.isMemToReg();
		
		
		
		
	}

	
	public void write() {
		nextRegister.setBranchAddress(branchAddressALU);
		nextRegister.setZero(zeroALU);
		nextRegister.setALUResult(ALUResultALU);
		nextRegister.setRegisterValueToMemory(registerValueToMemoryALU);
		nextRegister.setWriteBackRegister(writeBackRegisterALU);
		nextRegister.setMemRead(memReadALU);
		nextRegister.setMemWrite(memWriteALU);
		nextRegister.setBranch(branchALU);
		nextRegister.setRegWrite(regWriteALU);
		nextRegister.setMemToReg(memToRegALU);
	}
	
	private int add(int first, int second) {
		return (first + second);
	}
	
	private int sub(int first, int second) {
		return (first - second);
	}
	
	private int or(int first, int second) {
		return (first | second);
	}
	
	private int nor(int first, int second) {
		return ~(first | second);
	}
	
	private int and(int first, int second) {
		return (first & second);
	}
	
	private int setLessThan(int first, int second) {
		if(first < second)
			return 1;
		return 0;
	}
	
	private int setLessThanUnsigned(int first, int second) {
		for(int i = 31; i >= 0; i--) {
			boolean bitOfFiirst = BinaryManiplator.getBitByIndex(first, i);
			boolean bitOfSecond = BinaryManiplator.getBitByIndex(second, i);
			if(!bitOfFiirst && bitOfSecond)
				return 1;
		}
		
		return 0;
	}
	
	private int shiftLeftLogical(int first, int second) {
		return first << second;
	}
	
	private int rightLeftLogical(int first, int second) {
		return first >>> second;
	}
	
	private int branchIfEqual(int first, int second){
		if (first == second)
			return 1;
		return 0;
	}
	
	private int branchIfNotEqual(int first, int second) {
		if (first != second)
			return 1;
		return 0;
	}

}
