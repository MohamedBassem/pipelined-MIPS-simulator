package eg.edu.guc.mimps.components;

import com.sun.media.sound.AlawCodec;

import eg.edu.guc.mimps.assembler.Assembler;
import eg.edu.guc.mimps.assembler.Instruction;
import eg.edu.guc.mimps.registers.ExecuteMemoryRegisters;
import eg.edu.guc.mimps.registers.InstructionDecodeExecuteRegisters;
import eg.edu.guc.mimps.utils.BinaryManiplator;
import eg.edu.guc.mimps.utils.Constants;

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


	InstructionDecodeExecuteRegisters decodeExecuteRegister;
	ExecuteMemoryRegisters executeMemoryRegister;
	ExecuteMemoryRegisters newExecuteMemoryRegister;
	Instruction instruction;
	
	public ALU(
			InstructionDecodeExecuteRegisters instructionDecodeExecuteRegisters,
			ExecuteMemoryRegisters executeMemoryRegisters, Instruction instruction) {
			decodeExecuteRegister = instructionDecodeExecuteRegisters;
			executeMemoryRegister = executeMemoryRegisters;
			this.instruction = instruction;
	}


	public void execute() {
		newExecuteMemoryRegister = executeMemoryRegister.clone();
		int incrementedPc = decodeExecuteRegister.getIncrementedPc();
		int register1Value = decodeExecuteRegister.getRegister1Value();
		int register2Value = decodeExecuteRegister.getRegister2Value();
		int signExtendedOffset = decodeExecuteRegister.getSignExtendedOffset();
		int rt = decodeExecuteRegister.getRt();
		int rd = decodeExecuteRegister.getRd();

		boolean aluSrc = decodeExecuteRegister.isAluSrc();
		boolean regDest = decodeExecuteRegister.isRegDest();
		int aluOpt = decodeExecuteRegister.getAluOpt();

		boolean memRead = decodeExecuteRegister.isMemRead();
		boolean memWrite = decodeExecuteRegister.isMemWrite();
		boolean branch = decodeExecuteRegister.isBranch();

		boolean regWrite = decodeExecuteRegister.isRegWrite();
		boolean memToReg = decodeExecuteRegister.isMemToReg();
		
		int opCode = instruction.getOpcode();
		int funct = instruction.getFunct();
		
		if(opCode == 0) {
			switch (funct){
			case Constants.ADD_FUNC: ALUResultALU = add(register1Value, register2Value);break;
			case Constants.SUB_FUNC: ALUResultALU = sub(register1Value, register2Value);break;
			case Constants.SLL_FUNC: ALUResultALU = shiftLeftLogical(register1Value, register2Value);break;
			case Constants.SRL_FUNC: ALUResultALU = shiftRightLogical(register1Value, register2Value);break;
			case Constants.AND_FUNC: ALUResultALU = and(register1Value, register2Value);break;
			case Constants.OR_FUNC: ALUResultALU = or(register1Value, register2Value);break;
			case Constants.NOR_FUNC: ALUResultALU = nor(register1Value, register2Value);break;
			case Constants.SLT_FUNC: ALUResultALU = setLessThan(register1Value, register2Value);break;
			case Constants.SLTU_FUNC: ALUResultALU = setLessThanUnsigned(register1Value, register2Value);break;
			default:break;
			}
		} else {
			switch (opCode) {
			case Constants.ADDI_OPCODE:
			case Constants.SW_OPCODE:
			case Constants.LW_OPCODE: ALUResultALU = add(register1Value, signExtendedOffset);break;
			case Constants.ANDI_OPCODE: ALUResultALU = and(register1Value, signExtendedOffset);break;
			case Constants.ORI_OPCODE: ALUResultALU = or(register1Value, signExtendedOffset);break;
			case Constants.BEQ_OPCODE: zeroALU = branchIfEqual(register1Value, register2Value);break;
			case Constants.BNE_OPCODE: zeroALU = branchIfNotEqual(register1Value, register2Value);break;
			default:break;
			}
		}
		newExecuteMemoryRegister.setBranchAddress(branchAddressALU);
		newExecuteMemoryRegister.setZero(zeroALU);
		newExecuteMemoryRegister.setALUResult(ALUResultALU);
		newExecuteMemoryRegister.setRegisterValueToMemory(registerValueToMemoryALU);
		newExecuteMemoryRegister.setWriteBackRegister(writeBackRegisterALU);
		newExecuteMemoryRegister.setMemRead(memReadALU);
		newExecuteMemoryRegister.setMemWrite(memWriteALU);
		newExecuteMemoryRegister.setBranch(branchALU);
		newExecuteMemoryRegister.setRegWrite(regWriteALU);
		newExecuteMemoryRegister.setMemToReg(memToRegALU);
		
	}

	
	public void write() {
		executeMemoryRegister.replace(newExecuteMemoryRegister);
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
			if(bitOfFiirst && !bitOfSecond)
				return 0;
		}
		
		return 0;
	}
	
	private int shiftLeftLogical(int first, int second) {
		return first << second;
	}
	
	private int shiftRightLogical(int first, int second) {
		return first >>> second;
	}
	
	private boolean branchIfEqual(int first, int second){
		if (first == second)
			return true;
		return false;
	}
	
	private boolean branchIfNotEqual(int first, int second) {
		if (first != second)
			return true;
		return false;
	}

}
